package bgu.spl181.net.impl.MovieRentalService.Requests;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.AckCom;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.ErrorCom;
import bgu.spl181.net.impl.UserServiceTextBased.Messages.BaseMessage;
import bgu.spl181.net.impl.UserServiceTextBased.Messages.RequestMes;
import bgu.spl181.net.impl.UserServiceTextBased.User;

import java.util.LinkedList;

public class BalanceInfoReq extends RequestMes {

    private int balance; //from Json

   public BalanceInfoReq(){
       super("balance info");
   }
    @Override
    public BaseMessage Execute(Connections<BaseMessage> connections, int connId, LinkedList<User> users, boolean IsClienLogged) {
       if (IsClienLogged)
            return new AckCom("balance " + balance);
       else
           return new ErrorCom(" request " + ServiceName+ " failed");
    }
}
