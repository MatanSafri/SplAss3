package bgu.spl181.net.impl.UserServiceTextBased;

import bgu.spl181.net.impl.MovieRentalService.DB.MovieRentalDbExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class USTBPsharedData<T extends User,E extends USTBPDataExecutor<T>> {

    protected ConcurrentHashMap<String,T> _users;
    protected ConcurrentHashMap<Integer,T> _loggedInUsers;
    protected E _executor;

    public USTBPsharedData(Map<String,T> users, E executor)
    {
        _loggedInUsers = new ConcurrentHashMap<>();
        _users = new ConcurrentHashMap<>();
        _users.putAll(users);
        _executor = executor;
        _executor.setUsers(_users);
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
