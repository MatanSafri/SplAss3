package bgu.spl181.net.impl.MovieRentalService;

import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MoviesDB {

    private JsonObject _jsonObj;
    private Gson _gson;
    private ConcurrentHashMap<Integer,Movie> _movies;

    private static class SingletonHolder {
        private static MoviesDB instance = new MoviesDB();
    }
    private MoviesDB() {
        _movies = new ConcurrentHashMap<>();

        _gson = new GsonBuilder().registerTypeAdapter(Integer.class,
                (JsonSerializer<Integer>)(integer, type, jsonSerializationContext) ->
                        new JsonPrimitive(integer.toString()))
                .create();

        try{

            _jsonObj = _gson.fromJson(new FileReader("Users.json.txt"), JsonObject.class);
            LoadMovies();
        }
        catch (FileNotFoundException e){
            System.out.println("couldn't open file");
        }
    }
    public static MoviesDB getInstance() {
        return MoviesDB.SingletonHolder.instance;
    }

    public ConcurrentHashMap<Integer, Movie> getMovies() {
        return _movies;
    }

    public void SaveUsers()
    {
        HashMap<String,Collection<Movie>> jsonString = new HashMap<>();
        jsonString.put("movies",_movies.values());
        String sterilizeObj = _gson.toJson(jsonString);
        try {
            Files.write(Paths.get("Movies.json"), sterilizeObj.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LoadMovies() {

        JsonArray jsonArray = _jsonObj.get("movies").getAsJsonArray();
        for (JsonElement jsonUser :
                jsonArray) {
            Movie movie = _gson.fromJson(jsonUser,Movie.class);
            _movies.put(movie.getId(),movie);
        }
    }
}
