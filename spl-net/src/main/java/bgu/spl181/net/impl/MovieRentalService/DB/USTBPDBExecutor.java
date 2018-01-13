package bgu.spl181.net.impl.MovieRentalService.DB;

import bgu.spl181.net.impl.MovieRentalService.DB.DataCommands;
import bgu.spl181.net.impl.UserServiceTextBased.USTBPDataExecutor;
import bgu.spl181.net.impl.UserServiceTextBased.USTBPsharedData;
import bgu.spl181.net.impl.UserServiceTextBased.User;

import java.util.Map;

public class USTBPDBExecutor<T extends User> implements USTBPDataExecutor<T> {

    protected Object _lockUsers = new Object();

    protected DataCommands<String,T> _usersDB;
    protected Map<String,T> _users;

    public USTBPDBExecutor(Map<String,T> users,
                           DataCommands<String,T> usersDB)
    {
        _usersDB = usersDB;
        _users = users;
    }

    @Override
    public void register(T user) {
        synchronized (_lockUsers) {
            _users.put(user.getUserName(), user);
            _usersDB.saveData(_users);
        }
    }


}
