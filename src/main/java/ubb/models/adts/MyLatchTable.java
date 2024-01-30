package ubb.models.adts;

import java.util.HashMap;
import java.util.Map;

public class MyLatchTable implements MyILatchTable {
    private Map<Integer, Integer> latchTable;
    private Integer currentAddress;

    public MyLatchTable()
    {
        latchTable = new HashMap<>();
        currentAddress = 0;
    }

    @Override
    public synchronized void put(Integer key, Integer value) {
        this.latchTable.put(key, value);
    }

    @Override
    public synchronized void update(Integer key, Integer value) {
        this.latchTable.put(key, value);
    }

    @Override
    public synchronized Integer getFreeAddress() {
        this.currentAddress += 1;
        return this.currentAddress;
    }

    @Override
    public synchronized Map<Integer, Integer> getContent() {
        return this.latchTable;
    }

    @Override
    public Integer getValueAtAddress(Integer address) {
        return this.latchTable.get(address);
    }

    @Override
    public boolean isDefined(Integer key) {
        return this.latchTable.containsKey(key);
    }
}
