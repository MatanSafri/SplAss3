package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.impl.MovieRentalService.DataObjects.Movie;
import bgu.spl181.net.impl.MovieRentalService.DataObjects.MovieUser;
import bgu.spl181.net.impl.UserServiceTextBased.USTBPDataExecutor;

import java.util.Map;

public interface MoviesRentalDataExecutor extends USTBPDataExecutor<MovieUser> {
    void setMovies(Map<String,Movie> movies);
    void addBalanceToUser(MovieUser user, int amountToAdd);
    void rentMovie(MovieUser user,Movie movie);
    void returnMovie(MovieUser user,Movie movie);
    void addMovie(Movie movie);
    void removeMovie(String movieName);
    void changeMoviePrice(String movieName,int newPrice);
}
