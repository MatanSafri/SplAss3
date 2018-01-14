package bgu.spl181.net.impl.UserServiceTextBased;

import bgu.spl181.net.srv.ConnectionHandler;

import java.util.LinkedList;

public class User {
    private transient boolean isLogged;
    private String username;
    private String password;

    public User ( String _userName, String pass){
        username = _userName;
        password = pass;
        isLogged = false;
    }

   public String getUserName(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public boolean isLoggedIn(){
        return isLogged;
    }

    public void setIsLogged(boolean _isLogged){
        this.isLogged=_isLogged;
    }

}
