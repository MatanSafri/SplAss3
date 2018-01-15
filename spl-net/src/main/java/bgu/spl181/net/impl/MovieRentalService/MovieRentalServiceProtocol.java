package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.impl.MovieRentalService.DB.MovieRentalDbExecutor;
import bgu.spl181.net.impl.MovieRentalService.DataObjects.Movie;
import bgu.spl181.net.impl.MovieRentalService.DataObjects.MovieUser;

import bgu.spl181.net.impl.UserServiceTextBased.USTBProtocol;
import javafx.util.Pair;

import java.util.*;

public class MovieRentalServiceProtocol extends USTBProtocol<MovieUser, MovieRentalSharedData>
{
    public MovieRentalServiceProtocol(MovieRentalSharedData sharedData)
    {
        super(sharedData);
    }

    @Override
    protected synchronized void processRegisterExtraData(String userName, String password,ArrayList<Pair<String,String>> datablock)
    {
        MovieUser movieUser = new MovieUser(userName,password,"normal",
                0,new LinkedList<Movie>(),datablock.get(0).getValue());


        _sharedData.getDataExecutor().register(movieUser);
        _connections.send(connectionId, "ACK registration succeeded");
    }

    @Override
    protected String getServiceName(ArrayList<String> param){
        if(param.get(0).toLowerCase().equals("balance")) //has 2 words
            return param.get(0)+" "+param.get(1);

        return param.get(0);
    }
    @Override
    protected void processRequest(String serviceName, ArrayList<String> params)
    {
        // Normal users commands
        // Info request
        if (serviceName.toLowerCase().equals("info"))
        {
            // send all the movies names
            if (params.size() == 0)
            {
                String moviesNames =  String.join(" ", _sharedData.getMovies().keySet());
                _connections.send(connectionId, "ACK info " + moviesNames);
            }
            else {
                String movieName = params.get(0);//.substring(1,params.get(0).length()-1);
                if (_sharedData.getMovies().containsKey(movieName))
                {
                    Movie movie = _sharedData.getMovies().get(movieName);
                    String movieInfo  = "\"" + movie.getName() + "\" " +
                            movie.getAvailableAmount() + " " +
                            movie.getPrice() + " " +
                            String.join(" ", movie.getBannedCountries());
                    _connections.send(connectionId, "ACK info " + movieInfo);
                }
                else {
                    _connections.send(connectionId, "ERROR request info failed");
                }
            }
        }
        // Rent request
        else if (serviceName.toLowerCase().equals("rent"))
        {
            synchronized (_sharedData.getUsers()) {
                synchronized (_sharedData.getMovies()) {
                    String movieName = params.get(0);
                    if (_sharedData.getMovies().containsKey(movieName))// movie exists
                    {
                        Movie movie = _sharedData.getMovies().get(movieName);
                        if (movie.getAvailableAmount() != 0 && // there is available copy
                                currUser.getBalance() >= _sharedData.getMovies().get(movieName).getPrice() && // user has enough money
                                !movie.getBannedCountries().contains(currUser.getCountry()) &&// country not banned
                                !currUser.getMovies().stream().anyMatch(m -> m.getName().equals(movieName))) // user didn't rent the movie already)
                        {
                            _sharedData.getDataExecutor().rentMovie(currUser, movie);
                            _connections.send(connectionId, "ACK rent " + movieName + " success");
                            broadcastToLoggedInUsers(getBroadcastMessage(movie));
                        } else {
                            _connections.send(connectionId, "ERROR request rent failed");
                        }
                    } else
                        _connections.send(connectionId, "ERROR request rent failed");
                }
            }
        }
        // Return request
        else if (serviceName.toLowerCase().equals("return"))
        {
            synchronized (_sharedData.getUsers()) {
                synchronized (_sharedData.getMovies()) {
                    String movieName = params.get(0);
                    if (_sharedData.getMovies().containsKey(movieName) && currUser.getMovies().stream().anyMatch(m -> m.getName().equals(movieName)))// movie exists and user rented the movie
                    {
                        Movie movie = _sharedData.getMovies().get(movieName);
                        _sharedData.getDataExecutor().returnMovie(currUser, movie);
                        _connections.send(connectionId, "ACK return " + movieName + " success");
                        broadcastToLoggedInUsers(getBroadcastMessage(_sharedData.getMovies().get(movieName)));
                    } else
                        _connections.send(connectionId, "ERROR request return failed");
                }
            }
        }
        //Balance info request
        else if(serviceName.toLowerCase().equals("balance info"))
        {
            _connections.send(connectionId, "ACK balance " + currUser.getBalance());
        }
        // Balance add request
        else if (serviceName.toLowerCase().equals("balance add"))
        {
            synchronized (_sharedData.getUsers()) {
                int amountToAdd = Integer.parseInt(params.get(0));
                _sharedData.getDataExecutor().addBalanceToUser(currUser, amountToAdd);
                _connections.send(connectionId, "ACK balance " + currUser.getBalance() + " added " + amountToAdd);
            }
        }

        // Admin Commands
        // add movie
        else if (serviceName.toLowerCase().equals("addmovie"))
        {
             if (currUser.getType().equals("admin"))
             {
                 synchronized (_sharedData.getMovies()) {
                     String movieName = params.get(0);
                     if (!_sharedData.getMovies().containsKey(movieName) && Integer.parseInt(params.get(2)) > 0)
                     //the movie does not exist and the price is bigger then zero
                     {
                         ArrayList<String> bannedCountries;
                         if (params.size() == 4)
                             bannedCountries = new ArrayList<>(Arrays.asList(params.get(3).split(",")));
                         else
                             bannedCountries = new ArrayList<>();

                         Movie movie = new Movie(movieName,
                                 _sharedData.getNextMovieID(), Integer.parseInt(params.get(1)),
                                 Integer.parseInt(params.get(2)),
                                 bannedCountries,
                                 Integer.parseInt(params.get(1)));//check maybe null problem

                         _sharedData.getDataExecutor().addMovie(movie);
                         _connections.send(connectionId, "ACK addmovie" + movieName + " success");
                         broadcastToLoggedInUsers(getBroadcastMessage(_sharedData.getMovies().get(movieName)));
                     } else
                         _connections.send(connectionId, "ERROR request addmovie failed");
                 }
             }
             else
             {
                 _connections.send(connectionId, "ERROR request addmovie failed"); // user is not admin
             }
        }
        else if (serviceName.toLowerCase().equals("remmovie"))
        {
            if (currUser.getType().equals("admin"))
            {
                synchronized (_sharedData.getUsers()) {
                    synchronized (_sharedData.getMovies()) {
                        String movieName = params.get(0);
                        if (_sharedData.getMovies().containsKey(movieName) && // movie exists
                                !_sharedData.getUsers().values().stream().anyMatch(user -> // the movie wasn't rented
                                        user.getMovies().stream().anyMatch(movie -> movie.getName().equals(movieName)))) {
                            _sharedData.getDataExecutor().removeMovie(movieName);
                            _connections.send(connectionId, "ACK remmovie " + movieName + " success");
                            broadcastToLoggedInUsers("BROADCAST movie " + movieName + " removed");
                        } else
                            _connections.send(connectionId, "ERROR request remmovie failed");
                    }
                }
            }
            else
            {
                _connections.send(connectionId, "ERROR request remmovie failed"); // user is not admin
            }
        }
        else if (serviceName.toLowerCase().equals("changeprice"))
        {

            if (currUser.getType().equals("admin"))
            {
                synchronized (_sharedData.getMovies()) {
                    String movieName = params.get(0);
                    int newPrice = Integer.parseInt(params.get(1));

                    if (_sharedData.getMovies().containsKey(movieName) && newPrice > 0) {
                        _sharedData.getDataExecutor().changeMoviePrice(movieName, newPrice);
                        _connections.send(connectionId, "ACK changeprice " + movieName + " success");
                        broadcastToLoggedInUsers(getBroadcastMessage(_sharedData.getMovies().get(movieName)));
                    } else
                        _connections.send(connectionId, "ERROR request changeprice failed");
                }
            }
            else
            {
                _connections.send(connectionId, "ERROR request changeprice failed"); // user is not admin
            }
        }
    }

    private String getBroadcastMessage(Movie movie)
    {
        return "BROADCAST movie " + movie.getName() + " " +
                movie.getAvailableAmount()  + " " + movie.getPrice();
    }

    @Override
    protected MovieUser getUser(String userName, String password) {
        return new MovieUser(userName,password,"normal",0,new LinkedList<Movie>(),"");
    }
}
