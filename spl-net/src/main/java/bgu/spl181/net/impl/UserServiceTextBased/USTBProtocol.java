package bgu.spl181.net.impl.UserServiceTextBased;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.BaseCommand;
import bgu.spl181.net.impl.UserServiceTextBased.Commands.ErrorCom;
import bgu.spl181.net.impl.UserServiceTextBased.Messages.BaseMessage;
import bgu.spl181.net.impl.UserServiceTextBased.Messages.LoginMes;
import bgu.spl181.net.srv.ConnectionHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class USTBProtocol implements BidiMessagingProtocol <String> {

    private Connections<String> connections;
    private int connectionId;
    private boolean _shouldTerminate = false;
    private Map<String,User> users;
    private boolean isClientLoggedIn = false;
    private User currUser;

    public USTBProtocol(Map<String,User> _users){
        this.users=_users;
    }

    public void start(int connectionId, Connections<String> connections){
        this.connectionId = connectionId;
        this.connections = connections;
    }

    public void process(String message){


       /*  BaseMessage response = message.Execute(connections, connectionId,users,isClientLoggedIn);

       if(message.getName().equals("Signout") && message.getResponse()) {
            _shouldTerminate = true;
            isClientLoggedIn=false;
        }
        else if (message.getName()=="Login" && message.getResponse()) {
            currUser = ((LoginMes)message).getCurrUser(users);
            isClientLoggedIn = true;
            //System.out.println(message); //maybe don't need this
        }
        connections.send(connectionId, response);*/
    }

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return _shouldTerminate;
    }

    private BaseMessage StringToMsg(String msg){
        ArrayList<String> tmp = seperateString(msg);
        switch (tmp.get(0).toUpperCase()){
            case "LOGIN":{
                return new LoginMes(tmp.get(1),tmp.get(2));
            }
            case "REGISTER":{

            }
            case "REQUEST":{
                break;
            }
            case "SIGNOUT":{
                break;
            }
            default:
                return null;
        }

        return null;
    }




    private ArrayList<String> seperateString(String msg){
        ArrayList<String> tmp = new ArrayList<>();
        int j=0;
        boolean isName=false; //if we already got "
        for (int i=0;i< msg.length();i++){
            if (tmp.get(i)==" " && !isName) {
                tmp.add(msg.substring(j, i-1));
                j=i+1;
            }
            else if (tmp.get(i)== "\"")
            {
                if (!isName)
                    isName=true;
                else
                    isName = false;
            }

        }
        tmp.add(msg.substring(j,msg.length()-1));
        return tmp;
    }
}
