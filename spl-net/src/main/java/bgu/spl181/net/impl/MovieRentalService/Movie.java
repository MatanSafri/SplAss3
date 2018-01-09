package bgu.spl181.net.impl.MovieRentalService;

import java.util.LinkedList;

public class Movie {

    private String name;
    private int id;
    private int price;
    private LinkedList<String> bannedCountries;
    private int availableAmount;
    private int totalAmount;

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public LinkedList<String> getBannedCountries() {
        return bannedCountries;
    }

    public int getPrice() {
        return price;
    }

    public int getId()
    {
        return id;
    }

    public Movie(String name, int _id){
        this.name=name;
        this.id=_id;
        bannedCountries = new LinkedList<>();
    }
}

