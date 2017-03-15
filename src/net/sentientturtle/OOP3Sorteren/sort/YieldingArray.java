package net.sentientturtle.OOP3Sorteren.sort;

import net.sentientturtle.OOP3Sorteren.thread.Coroutine;
import net.sentientturtle.OOP3Sorteren.util.Pair;

import java.util.Objects;

/**
 * Array wrapper that yields every comparison and swap
 * May not contain null
 * @see Coroutine
 * @param <E> Type of this array
 */
public class YieldingArray<E extends Comparable<E>> {
    private E[] data;
    private Pair<Integer, Integer> lastSwapped;

    /**
     * Creates a new YieldingArray from the specified array object
     * @param data Initial data to use, may not contain null, and is copied.
     */
    public YieldingArray(E[] data) {
        Objects.requireNonNull(data);
        this.data = data.clone();
        for (E datum : data) if (datum == null) throw new NullPointerException("Data array contains null!");
        lastSwapped = new Pair<>(-1, -1);
    }

    /**
     * Swaps value at index1 with value at index2 in this yielding array
     * @param index1 Source index to swap with
     * @param index2 Target index to swap to
     * @throws InterruptedException If the yield was interrupted
     */
    public void swap(int index1, int index2) throws InterruptedException {
        Coroutine.yield();
        E temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
        lastSwapped.setObject1(index1);
        lastSwapped.setObject2(index2);
    }

    /**
     * Returns a pair of the indices of the last-swapped values
     * @return Pair integers containing the indices of the last-swapped values, or -1 if no swap has occurred.
     */
    public Pair<Integer, Integer> getLastSwapped() {
        return lastSwapped;
    }

    /**
     * Returns the size of this yielding array
     * @return Size of this array
     */
    public int size() {
        return data.length;
    }

    /**
     * Compares two values by their given indices, equivalent to array[index1].compareTo(array[index2])
     * @param index1 Index of the value to compare width
     * @param index2 Index of the value to compare to
     * @return A negative number, zero, or positive number if the value at index1 is lesser, equal or greater than the value at index2
     * @throws InterruptedException If the yield was interrupted
     */
    public int compare(int index1, int index2) throws InterruptedException {
        Coroutine.yield();
        return data[index1].compareTo(data[index2]);
    }

    /**
     * Compares a value by it's index with the given value, equivalent to array[index].compareTo(value)
     * @param index Index of the value to compare width
     * @param value Value to compare to
     * @return A negative number, zero, or a positive number if the value at index is lesser, equal, or greater than the given value
     * @throws InterruptedException If the yield is interrupted
     */
    public int compareTo(int index, E value) throws InterruptedException {
        Coroutine.yield();
        return data[index].compareTo(value);
    }

    /**
     * Gets the value at the given index
     * @param index Index to fetch the value at
     * @return Value at the given index, is not null
     */
    public E get(int index) {
        return data[index];
    }

    /**
     * Returns a copy of this yielding array's data
     * @return Copy of this yielding array's data
     */
    public E[] getData() {
        return data.clone();
    }
}
