package net.sentientturtle.OOP3Sorteren.sort;

/**
 * Implementation of quick sort that yields every comparison/swap
 * @param <E> Types to sort
 */
public class QuickSort<E extends Comparable<E>> extends YieldingSort<E> {

    /**
     * Creates a new instance of quick sort, with the provided data set
     *
     * @param yieldingArray YieldingArray to be sorted, may not be null
     */
    public QuickSort(YieldingArray<E> yieldingArray) {
        super(yieldingArray);
    }

    /**
     * Sorts the given data set, yielding every comparison and swap
     * @throws InterruptedException If the yield was interrupted
     */
    @Override
    public void run() throws InterruptedException {
        quickSort(0, yieldingArray.size()-1);
    }

    /**
     * Recursive implementation of quick sort, yields every comparison and swap
     * @param start Start of the sub array to quick sort (Inclusive)
     * @param end End of the sub array to quick sort (Inclusive)
     * @throws InterruptedException If the yield was interrupted
     */
    private void quickSort(int start, int end) throws InterruptedException {
        if (start < end) {
            int i = partition(start, end);
            quickSort(start, i - 1);    // (∩｀-´)⊃━☆ﾟ.*・｡ﾟ
            quickSort(i + 1, end);      // Uses evil coroutine side effect magic to halt during sorts while maintaining recursive implementation
        }
    }

    /**
     * Partitions the data set, yielding every comparison and swap
     * @param start Start of the subarray to partition (Inclusive)
     * @param end End of the subarray to partition (Inclusive)
     * @return Index of the pivot value after partitioning
     * @throws InterruptedException If the yield was interrupted
     */
    // Implements Nico Lomuto's partitioning scheme
    private int partition(int start, int end) throws InterruptedException {
        E pivot = yieldingArray.get(end);
        int i = start - 1;

        for (int j = start; j < end; j++) {
            if (yieldingArray.compareTo(j, pivot) <= 0) {
                i++;
                yieldingArray.swap(i, j);
            }
        }
        yieldingArray.swap(i + 1, end);
        return i + 1;
    }
}
