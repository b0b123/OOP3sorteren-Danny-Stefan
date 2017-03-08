package net.sentientturtle.OOP3Sorteren;

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

    /**
     * Executes one step of the implemented sorting algorithm.
     * @return True if sorting is completed after this step, false otherwise.
     * @throws IllegalStateException Thrown if done sorting, or no data is provided.
     */
    public abstract boolean step() throws IllegalStateException;

    /**
     * Returns the current state of the sort, true if done, false otherwise.
     * @return True if the current sort is complete, false otherwise.
     */
    public abstract boolean isDone();
}
