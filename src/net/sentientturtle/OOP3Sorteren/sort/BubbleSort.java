package net.sentientturtle.OOP3Sorteren.sort;

/**
 * Implementation of bubble sort that yields every comparison/swap
 * @param <E> Types to sort
 */
public class BubbleSort<E extends Comparable<E>> extends YieldingSort<E> {

    /**
     * Creates a new instance of bubble sort, with the provided data set
     *
     * @param yieldingArray YieldingArray to be sorted
     */
    public BubbleSort(YieldingArray<E> yieldingArray) {
        super(yieldingArray);
    }

    /**
     * Sorts the given data set, yielding every comparison and swap
     * @throws InterruptedException If the yield was interrupted
     */
    @Override
    public void run() throws InterruptedException {
        boolean swapped;
        int length = yieldingArray.size();
        do {
            swapped = false;
            for (int i = 1; i < length; i++) {
                if (yieldingArray.compare(i-1, i) > 0) {
                    yieldingArray.swap(i-1, i);
                    swapped = true;
                }
            }
            length--;
        } while (swapped);
    }
}
