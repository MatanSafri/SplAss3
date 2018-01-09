package bgu.spl181.net.impl.UserServiceTextBased.Messages;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.AckCom;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.BaseCommand;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.ErrorCom;
import bgu.spl181.net.impl.UserServiceTextBased.User;
import org.omg.CORBA.StringHolder;

import java.util.LinkedList;
import java.util.Map;

public class RegisterMes extends BaseMessage {

    String UserName;
    String Password;
    String extra;


public RegisterMes(String _UserName, String pass, String _extra ){
        name ="Register";
        this.UserName = _UserName;
        this.Password=pass;
        this.extra=_extra;
    }

    public BaseCommand Execute(Connections<BaseMessage> connections, int connId,
                               Map<String,User> users, boolean isClientLogged){

    if (Password==null || UserName==null || users.get(UserName)==null|| isClientLogged )
        //TODO: handle data block does not fit requirements
        {
            response=false;
            return new ErrorCom("registration failed");
        }
        else{
            User user = new User (false, UserName, Password);
            users.put(UserName,user);
            response=true;
            return new AckCom("registration succeeded");
        }

    }


}
