package bgu.spl181.net.impl.MovieRentalService.DB;

import bgu.spl181.net.impl.MovieRentalService.DB.DataCommands;
import bgu.spl181.net.impl.UserServiceTextBased.USTBPDataExecutor;
import bgu.spl181.net.impl.UserServiceTextBased.USTBPsharedData;
import bgu.spl181.net.impl.UserServiceTextBased.User;

import java.util.Map;

public class USTBPDBExecutor<T extends User> implements USTBPDataExecutor<T> {


    protected DataCommands<String,T> _usersDB;
    protected Map<String,T> _users;

    @Override
    public void setUsers(Map<String, T> _users) {
        this._users = _users;
    }

    public USTBPDBExecutor(DataCommands<String,T> usersDB)
    {
        _usersDB = usersDB;
    }

    @Override
    public void register(T user) {
        _users.put(user.getUserName(), user);
        _usersDB.saveData(_users);
    }


}
