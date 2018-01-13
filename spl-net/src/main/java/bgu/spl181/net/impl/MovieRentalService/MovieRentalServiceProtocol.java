package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.impl.MovieRentalService.DB.MovieRentalDbExecutor;
import bgu.spl181.net.impl.MovieRentalService.DataObjects.Movie;
import bgu.spl181.net.impl.MovieRentalService.DataObjects.MovieUser;

import bgu.spl181.net.impl.UserServiceTextBased.USTBProtocol;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MovieRentalServiceProtocol extends USTBProtocol<MovieUser, MovieRentalSharedData>
{
    public MovieRentalServiceProtocol(MovieRentalSharedData sharedData)
    {
        super(sharedData);
    }
    @Override
    protected  void processRegisterExtraData(String userName, String password,ArrayList<Pair<String,String>> datablock)
    {
        MovieUser movieUser = new MovieUser(userName,password,"normal",
                0,new LinkedList<Movie>(),datablock.get(0).getKey());


        _connections.send(connectionId, "ACK registration succeeded");
    }
    @Override
    protected void processRequest(String serviceName, ArrayList<String> params)
    {
        // TODO: Sync issues
        // Normal users commands
        // Info request
        if (params.get(0).toLowerCase() == "info")
        {
            // send all the movies names
            if (params.size() == 1)
            {
                String moviesNames =  String.join(",", _sharedData.getMovies().keySet());
                _connections.send(connectionId, "ACK info " + moviesNames);
            }
            else {
                String movieName = params.get(1);
                if (_sharedData.getMovies().containsKey(movieName))
                {
                    Movie movie = _sharedData.getMovies().get(movieName);
                    String movieInfo  = movie.getName() + " " +
                            movie.getAvailableAmount() + " " +
                            movie.getPrice() + " " +
                            String.join(",", movie.getBannedCountries());
                    _connections.send(connectionId, "ACK info " + movieInfo);
                }
                else {
                    _connections.send(connectionId, "ERROR request " + params.get(0) + "failed");
                }
            }
        }
        // Rent request
        else if (params.get(0).toLowerCase() == "rent")
        {
            String movieName = params.get(1);
            if (_sharedData.getMovies().containsKey(movieName))// movie exists
            {
                    Movie movie = _sharedData.getMovies().get(movieName);
                    if (movie.getAvailableAmount() != 0 && // there is available copy
                            currUser.getBalance() < _sharedData.getMovies().get(movieName).getPrice() && // user doesn't have enough money)
                            !movie.getBannedCountries().contains(currUser.getCountry()) &&// country not banned
                            !currUser.getMovies().contains(movieName)) // user didn't rent the movie already)
                    {
                        _sharedData.getDataExecutor().rentMovie(currUser,movie);
                        _connections.send(connectionId, "ACK rent " + movieName + " success" );
                        broadcastToLoggedInUsers(getBroadcastMessage(movie));
                    }
                    else
                    {
                        _connections.send(connectionId, "ERROR request " + params.get(0) + "failed");
                    }
            }
            else
                _connections.send(connectionId, "ERROR request " + params.get(0) + "failed");
        }
        // Return request
        else if (params.get(0).toLowerCase() == "return")
        {
            String movieName = params.get(1);
            if (_sharedData.getMovies().containsKey(movieName) && currUser.getMovies().contains(movieName))// movie exists and user rented the movie
            {
                Movie movie = _sharedData.getMovies().get(movieName);
                _sharedData.getDataExecutor().returnMovie(currUser,movie);
                _connections.send(connectionId, "ACK return " + movieName + " success" );
                broadcastToLoggedInUsers(getBroadcastMessage(_sharedData.getMovies().get(movieName)));
            }
            else
                _connections.send(connectionId, "ERROR request " + params.get(0) + "failed");
        }
        //Balance info request
        else if(params.get(0).toLowerCase() + params.get(1).toLowerCase() == "balance info")
        {
            _connections.send(connectionId, "ACK balance " + currUser.getBalance());
        }
        // Balance add request
        else if (params.get(0).toLowerCase() + params.get(1).toLowerCase() == "balance add")
        {
            int amountToAdd = Integer.parseInt(params.get(2));
            _sharedData.getDataExecutor().addBalanceToUser(currUser,amountToAdd);
            _connections.send(connectionId, "ACK balance " + currUser.getBalance() + " added " + amountToAdd);
        }

        // Admin Commands
        // add movie
        else if (params.get(0).toLowerCase() == "addmovie")
        {
             if (currUser.getType() == "admin")
             {
                 String movieName = params.get(1);
                 if (!_sharedData.getMovies().contains(movieName))
                 {
                     Movie movie = new Movie(movieName,
                             _sharedData.getNextMovieID(),Integer.parseInt(params.get(2)),
                             Integer.parseInt(params.get(3)),
                             new ArrayList<String>(Arrays.asList(params.get(4).split(","))),
                             Integer.parseInt(params.get(2)));
                     _sharedData.getDataExecutor().addMovie(movie);
                     _connections.send(connectionId, "ACK addmovie" + movieName + " success" );
                     broadcastToLoggedInUsers(getBroadcastMessage(_sharedData.getMovies().get(movieName)));
                 }
                 else
                     _connections.send(connectionId, "ERROR request " + params.get(0) + "failed");
             }
             else
             {
                 _connections.send(connectionId, "ERROR request " + params.get(0) + "failed"); // user is not admin
             }
        }
        else if (params.get(0).toLowerCase() == "remmovie")
        {
            if (currUser.getType() == "admin")
            {
                String movieName = params.get(1);
                if (_sharedData.getMovies().contains(movieName) && // movie doesn't exists
                        !_sharedData.getUsers().values().stream().anyMatch(user-> // the movie was rented
                    user.getMovies().stream().anyMatch(movie -> movie.getName() == movieName)))
                {
                    _sharedData.getDataExecutor().removeMovie(movieName);
                    _connections.send(connectionId, "ACK remmovie " + movieName + " success" );
                    broadcastToLoggedInUsers("BROADCAST movie " + movieName + " removed" );
                }
                else
                    _connections.send(connectionId, "ERROR request " + params.get(0) + "failed");
            }
            else
            {
                _connections.send(connectionId, "ERROR request " + params.get(0) + "failed"); // user is not admin
            }
        }
        else if (params.get(0).toLowerCase() == "changeprice")
        {
            if (currUser.getType() == "admin")
            {
                String movieName = params.get(1);
                int newPrice = Integer.parseInt(params.get(2));
                if (_sharedData.getMovies().contains(movieName) &&  newPrice > 0)
                {
                    _sharedData.getDataExecutor().changeMoviePrice(movieName,newPrice);
                    _connections.send(connectionId, "ACK changeprice " + movieName + " success" );
                    broadcastToLoggedInUsers(getBroadcastMessage(_sharedData.getMovies().get(movieName)));
                }
                else
                    _connections.send(connectionId, "ERROR request " + params.get(0) + "failed"); // user is not admin
            }
            else
            {
                _connections.send(connectionId, "ERROR request " + params.get(0) + "failed"); // user is not admin
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
