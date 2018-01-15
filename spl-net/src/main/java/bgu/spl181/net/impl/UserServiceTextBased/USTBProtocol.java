package bgu.spl181.net.impl.UserServiceTextBased;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import javafx.util.Pair;

import java.util.ArrayList;


public abstract class USTBProtocol<T extends User,K extends USTBPsharedData<T,? extends USTBPDataExecutor>> implements BidiMessagingProtocol<String> {

    protected Connections<String> _connections;
    protected int connectionId;
    protected boolean _shouldTerminate = false;
    protected K _sharedData;
    protected boolean isClientLoggedIn = false;
    protected T currUser;

    public USTBProtocol(K sharedData){
        _sharedData = sharedData;
    }

    public void start(int connectionId, Connections<String> connections){
        this.connectionId = connectionId;
        this._connections = connections;
    }

    public void process(String message){

        ArrayList<String> tmp = separateString(message);
        String messageType = tmp.get(0).toUpperCase();
        switch (messageType){
            case "LOGIN":{
                synchronized (_sharedData.getUsers()) {
                    processLogin(tmp.get(1), tmp.get(2));
                }
                break;
            }
            case "REGISTER": {
                ArrayList<Pair<String, String>> datablock = new ArrayList<>();
                for (int m = 3; m < tmp.size(); m++) {
                    int j=0;
                    for (int i = 0; i < tmp.get(m).length(); i++) {
                        if (tmp.get(m).charAt(i) == '=') {
                            datablock.add(new Pair<String, String>(tmp.get(m).substring(j,i),tmp.get(m).substring(i+1)));
                            break;
                        }
                    }
                }
                synchronized (_sharedData.getUsers()) {
                    processBasicRegister(tmp.get(1), tmp.get(2), datablock);
                }
                break;
            }
            case "REQUEST":{

                tmp.remove(tmp.get(0));//delete the word 'request' from param
                String serviceName = getServiceName(tmp);

                //delete the service name from the parameters
                ArrayList<String> serviceNameSplit = separateString(serviceName);
                for (int i=0;i<serviceNameSplit.size();i++){
                    tmp.remove(serviceNameSplit.get(i));
                }
                processBasicRequest(serviceName,tmp);
                break;
            }
            case "SIGNOUT":{
                synchronized (_sharedData.getUsers()) {
                    processSignout();
                }
                break;
            }
            default:
                break;
        }
    }



    public void broadcastToLoggedInUsers(String msg)
    {
        _connections.broadcastSpecific(msg, _sharedData.getLoggedInUsers().keySet());
    }

    /**
     * @return true if the connection should be terminated
     */
    public boolean shouldTerminate(){
        return _shouldTerminate;
    }

    protected String getServiceName(ArrayList<String> param)
    {
        return  param.get(0);
    }

    protected abstract void processRequest(String serviceName,ArrayList<String> params);
    protected abstract T getUser(String userName, String password);
    protected  void processRegisterExtraData(String userName, String password,ArrayList<Pair<String,String>> datablock)
    {

        _connections.send(connectionId, "ACK registration succeeded");
    }

    private void  processBasicRegister(String userName, String password, ArrayList<Pair<String,String>> datablock)
    {
        System.out.println(userName + " requested register");
        if (password==null || userName==null || _sharedData.getUsers().get(userName) !=null|| isClientLoggedIn )
        {
            _connections.send(connectionId, "ERROR registration failed");
        }
        else{

            if (datablock.size() == 0) {
                _connections.send(connectionId, "ACK registration succeeded");
                T user = getUser(userName,password);
                _sharedData.getUsers().put(userName,user);
            }
            else{
                processRegisterExtraData(userName,password,datablock);
            }
        }

    }

    private void  processBasicRequest(String serviceName,ArrayList<String> params)
    {
        if (isClientLoggedIn)
        {
            processRequest(serviceName,params);
        }
        else
        {
            _connections.send(connectionId, "ERROR request " + serviceName +" failed");
        }
    }

    private synchronized void processSignout()
    {
        if (isClientLoggedIn) {
            isClientLoggedIn = false;
            currUser.setIsLogged(false);
            currUser = null;
            _sharedData.getLoggedInUsers().remove(connectionId);
            _connections.send(connectionId, "ACK signout succeeded");
            _shouldTerminate = true;
        }
        else {
            _connections.send(connectionId, "ERROR signout failed");
        }
    }

    private void processLogin(String userName,String password)
    {
        T tmp = _sharedData.getUsers().get(userName);
        if (tmp == null|| isClientLoggedIn  || tmp.isLoggedIn() || !isPassFit(tmp , userName, password) ) {
            _connections.send(connectionId, "ERROR login failed");
        }
        else
        {
            isClientLoggedIn = true;
            tmp.setIsLogged(true);
            currUser=tmp;
            _sharedData.getLoggedInUsers().put(connectionId,currUser);
            _connections.send(connectionId, "ACK login succeeded");
        }
    }

    private boolean isPassFit(User user, String UserName, String Pass){
        return (user.getUserName().toLowerCase().equals(UserName.toLowerCase()) && user.getPassword().equals(Pass));
    }

    private ArrayList<String> separateString(String msg){
        ArrayList<String> tmp = new ArrayList<>();
        int j=0;
        boolean isName=false; //if we already got "
        for (int i=0;i< msg.length();i++){
            if (msg.charAt(i)== ' ' && !isName) {
                tmp.add(msg.substring(j, i));
                j=i+1;
            }
            else if (msg.charAt(i)== '\"')
            {
                if (!isName) {
                    isName = true;
                }
                else {
                    isName = false;
                }
            }

        }
        tmp.add(msg.substring(j,msg.length()));
        return tmp;
    }
}
