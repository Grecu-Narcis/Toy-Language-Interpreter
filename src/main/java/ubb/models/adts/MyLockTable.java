package ubb.models.adts;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class MyLockTable implements MyILockTable {
    private HashMap<Integer, Integer> lockTable;
    private final Lock lock;
    private int currentAddress;

    public MyLockTable() {
        this.lockTable = new HashMap<>();
        this.currentAddress = 0;
        this.lock = new java.util.concurrent.locks.ReentrantLock();
    }

    @Override
    public void put(Integer key, Integer value) {
        lock.lock();

        this.lockTable.put(key, value);

        lock.unlock();
    }

    @Override
    public int getFreeLocation() {
        lock.lock();

        this.currentAddress += 1;

        lock.unlock();

        return this.currentAddress;
    }

    @Override
    public synchronized boolean isDefined(Integer key) {
        return this.lockTable.containsKey(key);
    }

    @Override
    public synchronized Integer getValueAtAddress(Integer key) {
        return this.lockTable.get(key);
    }

    @Override
    public void update(Integer key, Integer newValue) {
        lock.lock();

        this.lockTable.put(key, newValue);

        lock.unlock();
    }

    @Override
    public synchronized Map<Integer, Integer> getContent() {
        return this.lockTable;
    }
}
