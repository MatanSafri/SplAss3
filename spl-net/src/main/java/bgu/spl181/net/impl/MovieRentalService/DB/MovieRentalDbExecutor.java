package bgu.spl181.net.impl.MovieRentalService.DB;


import bgu.spl181.net.impl.MovieRentalService.DataObjects.Movie;
import bgu.spl181.net.impl.MovieRentalService.DataObjects.MovieUser;
import bgu.spl181.net.impl.MovieRentalService.MoviesRentalDataExecutor;

import java.util.Map;

public class MovieRentalDbExecutor extends USTBPDBExecutor<MovieUser> implements MoviesRentalDataExecutor {

    private Object _lockMovies = new Object();
    private DataCommands<String,Movie> _moviesDB;
    private Map<String,Movie> _movies;

    public MovieRentalDbExecutor(Map<String,Movie> movies,
                                 Map<String,MovieUser> moviesUsers,
                                 DataCommands<String,Movie> moviesDataCommands,
                                 DataCommands<String,MovieUser> usersDataCommands)
    {
        super(moviesUsers,usersDataCommands);
        _moviesDB = moviesDataCommands;
        _movies = movies;
    }

    @Override
    public void addBalanceToUser(MovieUser user, int amountToAdd) {
        synchronized (_lockUsers) {
            user.setBalance(user.getBalance() + amountToAdd);
            _usersDB.saveData(_users);
        }
    }

    @Override
    public void rentMovie(MovieUser user, Movie movie) {
        synchronized (_lockMovies) {
            synchronized (_lockUsers) {
                movie.setAvailableAmount(movie.getAvailableAmount() - 1);
                MoviesDB.getInstance().saveData(_movies);
                user.getMovies().add(movie);
                _usersDB.saveData(_users);
            }
        }

    }

    @Override
    public void returnMovie(MovieUser user, Movie movie) {
        synchronized (_lockMovies) {
            synchronized (_lockUsers) {
                movie.setAvailableAmount(movie.getAvailableAmount() + 1);
                MoviesDB.getInstance().saveData(_movies);
                user.getMovies().remove(movie);
                _usersDB.saveData(_users);
            }
        }
    }

    @Override
    public void addMovie(Movie movie) {
        synchronized (_lockMovies) {
            _movies.put(movie.getName(), movie);
            _moviesDB.saveData(_movies);
        }
    }

    @Override
    public void removeMovie(String movieName) {
        synchronized (_lockMovies) {
            _movies.remove(movieName);
            _moviesDB.saveData(_movies);
        }
    }

    @Override
    public void changeMoviePrice(String movieName, int newPrice) {
        synchronized (_lockMovies) {
            _movies.get(movieName).setPrice(newPrice);
            _moviesDB.saveData(_movies);
        }
    }

}
