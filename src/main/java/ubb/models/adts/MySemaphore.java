package ubb.models.adts;

import ubb.business.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MySemaphore implements MyISemaphore {
    private final Map<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    private Integer currentAddress;

    public MySemaphore() {
        semaphoreTable = new ConcurrentHashMap<>();
        currentAddress = 0;
    }

    @Override
    public synchronized Integer getFreeAddress() {
        currentAddress += 1;
        return this.currentAddress;
    }

    @Override
    public Pair<Integer, List<Integer>> getSemaphoreWithKey(Integer key) {
        return this.semaphoreTable.get(key);
    }

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return this.semaphoreTable;
    }

    @Override
    public synchronized void add(Integer key, Pair<Integer, List<Integer>> semaphoreToAdd) {
        this.semaphoreTable.put(key, semaphoreToAdd);
    }

    @Override
    public boolean isDefined(Integer key) {
        return this.semaphoreTable.containsKey(key);
    }
}
