package bgu.spl181.net.impl.UserServiceTextBased.Messages;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.AckCom;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.BaseCommand;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.ErrorCom;
import bgu.spl181.net.impl.UserServiceTextBased.User;
import com.sun.org.apache.xml.internal.serialize.BaseMarkupSerializer;

import java.util.LinkedList;

public class SignoutMes extends BaseMessage {

    public SignoutMes(){
        this.name = "Signout";
    }
    public BaseMessage Execute(Connections<BaseMessage> connections, int connId,
                               LinkedList<User> users,boolean IsClienLogged){

        if (IsClienLogged)
            return new AckCom("signout succeeded");
        else
            return new ErrorCom("signout failed");
    }


}
