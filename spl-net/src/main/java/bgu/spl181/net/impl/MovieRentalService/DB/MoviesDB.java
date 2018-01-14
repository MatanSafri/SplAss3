package bgu.spl181.net.impl.MovieRentalService.DB;

import bgu.spl181.net.impl.MovieRentalService.DataObjects.Movie;
import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MoviesDB implements DataCommands<String,Movie> {

    private JsonObject _jsonObj;
    private Gson _gson;



    private static class SingletonHolder {
        private static MoviesDB instance = new MoviesDB();
    }
    private MoviesDB() {

        _gson = new GsonBuilder().registerTypeAdapter(Integer.class,
                (JsonSerializer<Integer>)(integer, type, jsonSerializationContext) ->
                        new JsonPrimitive(integer.toString()))
                .setPrettyPrinting().create();


    }
    public static MoviesDB getInstance() {
        return MoviesDB.SingletonHolder.instance;
    }

    @Override
    public synchronized boolean saveData(Map<String, Movie> data) {
        HashMap<String,Collection<Movie>> jsonString = new HashMap<>();
        jsonString.put("movies",data.values());
        String sterilizeObj = _gson.toJson(jsonString);
        sterilizeObj = sterilizeObj.replace("\\\"","");
        try {
            Files.write(Paths.get("Movies.json"), sterilizeObj.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public synchronized Map<String, Movie> getData() {
        Map<String, Movie> movies = new HashMap();
        try {
            _jsonObj = _gson.fromJson(new FileReader("Movies.json"), JsonObject.class);
            JsonArray jsonArray = _jsonObj.get("movies").getAsJsonArray();
            for (JsonElement jsonUser :
                    jsonArray) {
                Movie movie = _gson.fromJson(jsonUser,Movie.class);
                // save strings inside ""
                ArrayList<String> bannedCountries = new ArrayList<>();

                for (int i = 0;i <  movie.getBannedCountries().size();i++)
                {
                    bannedCountries.add("\"" +  movie.getBannedCountries().get(i) + "\"");
                }
                movie.setBannedCountries(bannedCountries);

                movie.setName("\"" + movie.getName() + "\"");

                movies.put(movie.getName(),movie);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return movies;
        }
    }
}
