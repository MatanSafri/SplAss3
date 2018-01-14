package bgu.spl181.net.impl.MovieRentalService.DB;

import bgu.spl181.net.impl.MovieRentalService.DataObjects.MovieUser;
import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UsersDB implements DataCommands<String,MovieUser> {

    private JsonObject _jsonObj;
    private Gson _gson;

    @Override
    public boolean saveData(Map<String, MovieUser> data) {

        HashMap<String,Collection<MovieUser>> jsonString = new HashMap<>();
        jsonString.put("users",data.values());
        String sterilizeObj = _gson.toJson(jsonString);
        sterilizeObj = sterilizeObj.replace("\\\"","");
        try {
            Files.write(Paths.get("Users.json"), sterilizeObj.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Map<String, MovieUser> getData() {
        Map<String, MovieUser> movieUsers = new HashMap<>();
        try (FileReader fileReader =new FileReader("Users.json")){
            _jsonObj = _gson.fromJson(fileReader, JsonObject.class);
            JsonArray jsonArray = _jsonObj.get("users").getAsJsonArray();
            for (JsonElement jsonUser :
                    jsonArray) {
                MovieUser movieUser = _gson.fromJson(jsonUser, MovieUser.class);
                // save strings inside ""
                movieUser.getMovies().forEach(movie -> movie.setName("\"" + movie.getName() + "\""));
                movieUser.setCountry("\"" + movieUser.getCountry() + "\"");
                movieUsers.put(movieUser.getUserName(), movieUser);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            return movieUsers;
        }
    }

    private static class SingletonHolder {
        private static UsersDB instance = new UsersDB();;
    }
    private UsersDB() {
        _gson = new GsonBuilder().registerTypeAdapter(Integer.class,
                (JsonSerializer<Integer>)(integer, type, jsonSerializationContext) ->
                        new JsonPrimitive(integer.toString()))
                .setPrettyPrinting().create();

    }
    public static UsersDB getInstance() {
        return SingletonHolder.instance;
    }

}