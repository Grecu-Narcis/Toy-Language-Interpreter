package ubb.models.adts;

import java.util.Map;

public interface MyILockTable {
    void put(Integer key, Integer value);
    int getFreeLocation();
    boolean isDefined(Integer key);
    Integer getValueAtAddress(Integer key);
    void update(Integer key, Integer newValue);
    Map<Integer, Integer> getContent();
}
