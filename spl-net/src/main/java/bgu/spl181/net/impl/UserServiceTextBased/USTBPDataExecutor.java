package bgu.spl181.net.impl.UserServiceTextBased;

public interface USTBPDataExecutor<T extends User> {
    void register(T user);
}
