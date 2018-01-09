package bgu.spl181.net.impl.UserServiceTextBased.Messages;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.AckCom;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.BaseCommand;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.ErrorCom;
import bgu.spl181.net.impl.UserServiceTextBased.User;

import java.util.LinkedList;
import java.util.Map;

public class LoginMes extends BaseMessage {
    String UserName;
    String Password;

    public LoginMes( String _UserName, String pass){
        this.name = "Login";
        this.UserName=_UserName;
        this.Password= pass;
    }

    public BaseCommand Execute(Connections<BaseMessage> connections,
                               int connId, Map<String,User> users, boolean IsClienLogged){
        User tmp = users.get(UserName);
        if (tmp == null|| IsClienLogged  || tmp.isLoggedIn() || !isPassFit(tmp , UserName, Password) ) {
            response = false;
            return new ErrorCom("login failed");
        }
        else
        {
            tmp.setIsLogged(true);
            response =true;
            return new AckCom("login succeeded");
        }
    }



    private boolean isPassFit(User user, String UserName, String Pass){
        return (user.getUserName().equals(UserName) && user.getPassword().equals(Pass));
    }

    public User getCurrUser(Map<String,User> users) {
        return users.get(UserName);
    }

    }
