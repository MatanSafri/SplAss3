package bgu.spl181.net.impl.UserServiceTextBased.Messages;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.BaseCommand;
import bgu.spl181.net.impl.UserServiceTextBased.User;

import java.util.LinkedList;

public abstract class BaseMessage {

    String name;
    boolean response; //true- succeeded, false- failed

    public String getName() {
        return name;
    }
    public boolean getResponse(){
        return response;
    }

    public abstract BaseMessage Execute(Connections<BaseMessage> connections,
                                        int connId,
                                        LinkedList<User> users,
                                        boolean IsClienLogged);


    public User getUser(LinkedList<User> users, String UserName){
        for(User user: users){
            if (user.getUserName().equals(UserName))
                return user;
        }
        return null;
    }

}
