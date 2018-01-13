package bgu.spl181.net.impl.MovieRentalService.DataObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Movie {

    private String name;
    private int id;
    private int price;
    private ArrayList<String> bannedCountries;
    private int availableAmount;
    private int totalAmount;

    public String getName() {
        return name;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public void setBannedCountries(ArrayList<String> bannedCountries) {
        this.bannedCountries = bannedCountries;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public ArrayList<String> getBannedCountries() {
        return bannedCountries;
    }

    public int getPrice() {
        return price;
    }

    public int getId()
    {
        return id;
    }

    public Movie(String name,int id,int totalAmount,int price,ArrayList<String> bannedCountries,int availableAmount)
    {
        this.name  = name;
        this.id = id;
        this.totalAmount = totalAmount;
        this.price = price;
        this.bannedCountries  = bannedCountries;
        this.availableAmount = availableAmount;
    }
}

