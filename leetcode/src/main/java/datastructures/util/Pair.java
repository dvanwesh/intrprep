package datastructures.util;

import lombok.Value;

@Value
public class Pair<T, U> {
    private final T key;
    private final U value;

    public Pair(T key, U value) {
        this.key = key;
        this.value = value;
    }
}
