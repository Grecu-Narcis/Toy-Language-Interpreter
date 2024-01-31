package ubb.models.adts;

import ubb.business.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBarrierTable implements MyIBarrierTable {
    private final Map<Integer, Pair<Integer, List<Integer>>> barrierTable;
    private Integer currentAddress;

    public MyBarrierTable() {
        this.barrierTable = new HashMap<>();
        this.currentAddress = 0;
    }

    @Override
    public synchronized Integer getFreeAddress() {
        this.currentAddress += 1;

        return this.currentAddress;
    }

    @Override
    public synchronized void add(Integer key, Pair<Integer, List<Integer>> barrier) {
        this.barrierTable.put(key, barrier);
    }

    @Override
    public Pair<Integer, List<Integer>> getBarrier(Integer key) {
        return this.barrierTable.get(key);
    }

    @Override
    public synchronized void update(Integer key, Pair<Integer, List<Integer>> barrier) {
        this.barrierTable.put(key, barrier);
    }

    @Override
    public boolean isDefined(Integer key) {
        return this.barrierTable.containsKey(key);
    }

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return this.barrierTable;
    }
}
