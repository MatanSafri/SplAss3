package bgu.spl181.net.impl.MovieRentalService;

import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UsersDB {

    private JsonObject _jsonObj;
    private Gson _gson;
    private ConcurrentHashMap<String,MovieUser> _movieUsers;

    private static class SingletonHolder {
        private static UsersDB instance = new UsersDB();;
    }
    private UsersDB() {
        _movieUsers = new ConcurrentHashMap<>();
        _gson = new GsonBuilder().registerTypeAdapter(Integer.class,
                (JsonSerializer<Integer>)(integer, type, jsonSerializationContext) ->
                        new JsonPrimitive(integer.toString()))
                .create();

        LoadUsers();
    }
    public static UsersDB getInstance() {
        return SingletonHolder.instance;
    }

    public void SaveUsers()
    {
        HashMap<String,Collection<MovieUser>> jsonString = new HashMap<>();
        jsonString.put("users",_movieUsers.values());
        String sterilizeObj = _gson.toJson(jsonString);
        try {
            Files.write(Paths.get("Users.json"), sterilizeObj.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try (FileOutputStream fout = new FileOutputStream("Users.json");
             BufferedWriter writer = new BufferedWriter(fout,"utf-8"); {
                HashMap<String,Collection<MovieUser>> jsonString = new HashMap<>();
                jsonString.put("users",_movieUsers.values());
                String sterilizeObj = _gson.toJson(jsonString);
                //oos.writeUTF(sterilizeObj);

        } catch (IOException ex2) {
            ex2.printStackTrace();
        }*/
    }

    public ConcurrentHashMap<String, MovieUser> getUsers() {
        return _movieUsers;
    }

    private void LoadUsers() {
        try (FileReader fileReader =new FileReader("Users.json")){
            _jsonObj = _gson.fromJson(fileReader, JsonObject.class);
            JsonArray jsonArray = _jsonObj.get("users").getAsJsonArray();
            for (JsonElement jsonUser :
                    jsonArray) {
                MovieUser movieUser = _gson.fromJson(jsonUser, MovieUser.class);
                _movieUsers.put(movieUser.getUserName(), movieUser);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}