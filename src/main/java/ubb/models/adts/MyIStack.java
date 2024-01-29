package ubb.models.adts;

import ubb.exceptions.StackException;

import java.util.List;
import java.util.Stack;

public interface MyIStack<T> {
    T pop() throws StackException;
    void push(T itemToPush);
    boolean isEmpty();
    List<T> reverse();
    List<T> getStackAsList();
}
