package net.sentientturtle.OOP3Sorteren.sort;

/**
 * Implementation of insertion sort that yields every comparison/swap
 * @param <E> Types to sort
 */
public class InsertionSort<E extends Comparable<E>> extends YieldingSort<E> {

    /**
     * Creates a new instance of insertion sort, with the provided data set
     *
     * @param yieldingArray YieldingArray to be sorted
     */
    public InsertionSort(YieldingArray<E> yieldingArray) {
        super(yieldingArray);
    }

    /**
     * Sorts the given data set, yielding every comparison and swap
     * @throws InterruptedException If the yield was interrupted
     */
    @Override
    public void run() throws InterruptedException {
        for (int i = 1; i < yieldingArray.size(); i++) {
            int j = i;
            while (j > 0 && yieldingArray.compare(j-1, j) > 0) {
                yieldingArray.swap(j-1, j);
                j--;
            }
        }
    }
}
