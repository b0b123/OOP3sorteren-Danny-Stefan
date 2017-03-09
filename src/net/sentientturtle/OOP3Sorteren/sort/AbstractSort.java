package net.sentientturtle.OOP3Sorteren.sort;

import java.util.Objects;

/**
 * Abstract class for sorting algorithms that can be executed in single steps
 */
public abstract class AbstractSort<E extends Comparable<E>> { // Name is still terrible at telling what this class does.
    protected DataSet<E> dataSet;
    /**
     * Creates a new instance of the sort, with the provided data set
     * @param dataSet DataSet to be sorted, may not be null
     */
    public AbstractSort(DataSet<E> dataSet) {
        this.dataSet = Objects.requireNonNull(dataSet);
    }

    /**
     * Gets the current data set
     * @return DataSet currently being sorted.
     */
    public DataSet<E> getDataSet() {
        return dataSet;
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
