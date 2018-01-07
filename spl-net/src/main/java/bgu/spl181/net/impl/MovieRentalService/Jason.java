package bgu.spl181.net.impl.MovieRentalService;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class Jason {
    private static JsonObject _jsonObj;

    public static void executeFromJsonArray(JsonArray jsonUsers, LinkedList<MovieUser> users) {

        String userName;
        String type;
        String password;
        String country;
        String movieNAme;
        int movieId;
        ArrayList<Movie> RentMovies = new ArrayList<>();
        JsonArray jsonArray= _jsonObj.get("users").getAsJsonArray();
        try {
            for (Object jsonUser :
                    jsonUsers) {
                userName = ((JsonObject) jsonUser).get("username").getAsString();
                type = ((JsonObject) jsonUser).get("type").getAsString();
                password = ((JsonObject) jsonUser).get("password").getAsString();
                country = ((JsonObject) jsonUser).get("country").getAsString();



    }

}