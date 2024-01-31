package ubb.models.adts;

import ubb.business.Pair;

import java.util.List;
import java.util.Map;

public interface MyISemaphore {
    Integer getFreeAddress();
    Pair<Integer, List<Integer>> getSemaphoreWithKey(Integer key);
    Map<Integer, Pair<Integer, List<Integer>>> getContent();
    void add(Integer key, Pair<Integer, List<Integer>> semaphoreToAdd);
    boolean isDefined(Integer key);
}
