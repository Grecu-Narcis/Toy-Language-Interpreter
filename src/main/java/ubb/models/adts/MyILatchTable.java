package ubb.models.adts;

import java.util.Map;

public interface MyILatchTable {
    void put(Integer key, Integer value);
    void update(Integer key, Integer value);
    Integer getFreeAddress();
    Map<Integer, Integer> getContent();
    Integer getValueAtAddress(Integer address);

    boolean isDefined(Integer key);
}
