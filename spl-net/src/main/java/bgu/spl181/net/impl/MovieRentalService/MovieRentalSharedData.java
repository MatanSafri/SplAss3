package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.impl.MovieRentalService.DB.MovieRentalDbExecutor;
import bgu.spl181.net.impl.MovieRentalService.DataObjects.Movie;
import bgu.spl181.net.impl.MovieRentalService.DataObjects.MovieUser;
import bgu.spl181.net.impl.UserServiceTextBased.USTBPsharedData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieRentalSharedData extends USTBPsharedData<MovieUser,MoviesRentalDataExecutor>{

    private ConcurrentHashMap<String,Movie> _movies;
    private AtomicInteger _idGenerator = new AtomicInteger(0);


    public MovieRentalSharedData(Map<String, MovieUser> users,Map<String, Movie> movies,MoviesRentalDataExecutor executor) {
        super(users,executor);
        _movies = new ConcurrentHashMap<>();
        _movies.putAll(movies);
    }

    public ConcurrentHashMap<String, Movie> getMovies() {
        return _movies;
    }

    public int getNextMovieID()
    {
        return _idGenerator.getAndIncrement();
    }

}
