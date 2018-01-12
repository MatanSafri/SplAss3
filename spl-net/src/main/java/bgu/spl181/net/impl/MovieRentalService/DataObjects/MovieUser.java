package bgu.spl181.net.impl.MovieRentalService.DataObjects;

import bgu.spl181.net.impl.UserServiceTextBased.User;

import java.util.LinkedList;

public class MovieUser extends User {
    private int balance;
    private String type;
    private String country;
    private LinkedList<Movie> movies = new LinkedList<>();


    public MovieUser(String UserName , String password ,String _type, int _balance,
                     LinkedList<Movie> _rentMovies, String _country){

        super( UserName,password);
        this.balance= _balance;
        this.type=_type;
        this.country=_country;
        this.movies= _rentMovies;
    }

    public void setBalance (int balance)
    {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public String getCountry() {
        return country;
    }

    public LinkedList<Movie> getMovies() {
        return movies;
    }

    public String getType() {
        return type;
    }
}
