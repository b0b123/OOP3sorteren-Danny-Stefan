package net.sentientturtle.OOP3Sorteren;

/**
 * Implementation of BubbleSort that allows stepping-through
 * @param <E> Types to sort
 */
public class BubbleStepSort<E extends Comparable<E>> extends StepSort<E> {
    public int index;
    private boolean hasSwapped;
    private boolean isDone;

    /**
     * Creates a new instance of the sort, with the provided data set
     *
     * @param data Data set to be sorted
     */
    public BubbleStepSort(E[] data) {
        super(data);
    }

    private void swap(int index1, int index2) {
        E temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }

    @Override
    protected void reset() {
        index = 0;
        hasSwapped = false;
        isDone = false;
    }

    @Override
    public boolean step() throws IllegalStateException {
        if (isDone) throw new IllegalStateException("Sort is already completed!");
        if (data == null) throw new IllegalStateException("Data is null!");
        if (data.length == 1) return (isDone = true);

        if (data[index].compareTo(data[index + 1]) > 0) {
            swap(index, index + 1);
            hasSwapped = true;
        }

        if (++index + 1 >= data.length) {
            if (!hasSwapped) {
                isDone = true;
                return true;
            } else {
                hasSwapped = false;
                index = 0;
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
