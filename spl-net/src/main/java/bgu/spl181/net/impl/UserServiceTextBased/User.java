package bgu.spl181.net.impl.UserServiceTextBased;

import bgu.spl181.net.impl.UserServiceTextBased.Messages.SignoutMes;
import bgu.spl181.net.srv.ConnectionHandler;

import java.util.LinkedList;

public class User {
    private boolean isLogged=false;
    private String username;
    private String password;

    public User (boolean _isLogged, String _userName, String pass){

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
