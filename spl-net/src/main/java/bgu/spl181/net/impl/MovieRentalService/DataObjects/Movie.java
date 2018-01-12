package bgu.spl181.net.impl.MovieRentalService.DataObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Movie {

    private String _name;
    private int _id;
    private int _price;
    private ArrayList<String> _bannedCountries;
    private int _availableAmount;
    private int _totalAmount;

    public String getName() {
        return _name;
    }

    public int getTotalAmount() {
        return _totalAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this._availableAmount = availableAmount;
    }

    public void setBannedCountries(ArrayList<String> bannedCountries) {
        this._bannedCountries = bannedCountries;
    }

    public void setTotalAmount(int totalAmount) {
        this._totalAmount = totalAmount;
    }

    public void setPrice(int price) {
        this._price = price;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getAvailableAmount() {
        return _availableAmount;
    }

    public ArrayList<String> getBannedCountries() {
        return _bannedCountries;
    }

    public int getPrice() {
        return _price;
    }

    public int getId()
    {
        return _id;
    }

    public Movie(String name, int _id){
        this._name=name;
        this._id=_id;
        _bannedCountries = new ArrayList<>();
    }

    public Movie(String name,int id,int totalAmount,int price,ArrayList<String> bannedCountries)
    {
        _name  = name;
        _id = id;
        _totalAmount = totalAmount;
        _price = price;
        _bannedCountries  = bannedCountries;
    }
}

