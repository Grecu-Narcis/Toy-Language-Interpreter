package ubb.models.adts;

import ubb.business.Pair;

import java.util.List;
import java.util.Map;

public interface MyIBarrierTable {
    Integer getFreeAddress();

    void add(Integer key, Pair<Integer, List<Integer>> barrier);

    Pair<Integer, List<Integer>> getBarrier(Integer key);

    void update(Integer key, Pair<Integer, List<Integer>> barrier);

    boolean isDefined(Integer key);

    Map<Integer, Pair<Integer, List<Integer>>> getContent();
}
