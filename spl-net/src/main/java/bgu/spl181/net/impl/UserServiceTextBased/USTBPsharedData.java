package bgu.spl181.net.impl.UserServiceTextBased;

import bgu.spl181.net.impl.MovieRentalService.DB.MovieRentalDbExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class USTBPsharedData<T extends User,E extends USTBPDataExecutor<T>> {

    private ConcurrentHashMap<String,T> _users;
    private ConcurrentHashMap<Integer,T> _loggedInUsers;
    private E _executor;

    public USTBPsharedData(Map<String,T> users, E executor)
    {
        _loggedInUsers = new ConcurrentHashMap<>();
        _users = new ConcurrentHashMap<>();
        _users.putAll(users);
        _executor = executor;
    }


    public Map<Integer, T> getLoggedInUsers() {
        return _loggedInUsers;
    }

    public Map<String, T> getUsers() {
        return _users;
    }

    public E getDataExecutor()
    {
        return _executor;
    }
}
