package bgu.spl181.net.impl.UserServiceTextBased;

import java.util.Map;

public interface USTBPDataExecutor<T extends User> {
    void register(T user);
    void setUsers(Map<String,T> users);
}
