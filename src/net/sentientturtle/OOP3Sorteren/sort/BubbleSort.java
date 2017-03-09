package net.sentientturtle.OOP3Sorteren.sort;

/**
 * Implementation of BubbleSort that allows stepping-through
 * @param <E> Types to sort
 */
public class BubbleSort<E extends Comparable<E>> extends AbstractSort<E> {
    private int index;
    private int completed;
    private boolean hasSwapped;
    private boolean isDone;

    /**
     * Creates a new instance of this sort, with the provided data set
     *
     * @param data Data set to be sorted
     */
    public BubbleSort(E[] data) {
        super(data);
        index = 0;
        completed = 0;
        hasSwapped = false;
        isDone = (data.length == 1);
    }

    @Override
    public synchronized boolean step() {
        if (isDone) return true;

        if (data[index].compareTo(data[index + 1]) > 0) {
            swap(index, index + 1);
            hasSwapped = true;
        }

        if (++index + 1 >= data.length-completed) {
            if (hasSwapped) {
                hasSwapped = false;
                index = 0;
                completed++;
                return false;
            }
            return isDone = true;
        }
        return false;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
