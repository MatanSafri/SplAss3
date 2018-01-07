package bgu.spl181.net.impl.UserServiceTextBased;

import bgu.spl181.net.impl.UserServiceTextBased.Messages.SignoutMes;
import bgu.spl181.net.srv.ConnectionHandler;

import java.util.LinkedList;

public class User {
    private boolean isLogged=false;
    private String UserName;
    private String Password;

    public User (boolean _isLogged, String _userName, String pass){

    }

   public String getUserName(){
        return UserName;
    }

    public String getPassword(){
        return Password;
    }

    public boolean isLoggedIn(){
        return isLogged;
    }

    public void setIsLogged(boolean _isLogged){
        this.isLogged=_isLogged;
    }

}
