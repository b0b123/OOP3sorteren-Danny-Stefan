package net.sentientturtle.OOP3Sorteren.sort;

import java.util.Objects;

/**
 * Abstract class for sorting algorithms that can be executed in single steps
 */
public abstract class AbstractSort<E extends Comparable<E>> { // Name is still terrible at telling what this class does.
    protected E[] data;

    /**
     * Creates a new instance of the sort, with the provided data set
     * @param data Data set to be sorted, may be null
     */
    public AbstractSort(E[] data) {
        this.data = Objects.requireNonNull(data);
    }

    /**
     * Gets the current data set, may be null!
     * @return Data set currently being sorted.
     */
    public E[] getData() {
        return data;
    }

    protected void swap(int index1, int index2) {
        E temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }

    /**
     * Executes one step of the implemented sorting algorithm.
     * @return True if sorting is completed, false otherwise.
     */
    public abstract boolean step();

    /**
     * Returns the current state of the sort, true if done, false otherwise.
     * @return True if the current sort is complete, false otherwise.
     */
    public abstract boolean isDone();
}
