package net.sentientturtle.OOP3Sorteren.sort;

/**
 * Implementation of merge sort that yields every comparison/swap
 * @param <E> Types to sort
 */
public class MergeSort<E extends Comparable<E>> extends YieldingSort<E> {

    /**
     * Creates a new instance of merge sort, with the provided data set
     * @param yieldingArray YieldingArray to be sorted, may not be null
     */
    public MergeSort(YieldingArray<E> yieldingArray) {
        super(yieldingArray);
    }

    /**
     * Sorts the given data set, yielding every comparison and swap
     * @throws InterruptedException If the yield was interrupted
     */
    @Override
    public void run() throws InterruptedException {
        mergeSort(0, yieldingArray.size());
    }

    private void mergeSort(int start, int end) throws InterruptedException {
        int len = end-start;
        if (len > 1) {
            mergeSort(start, start+(len/2));    // (∩｀-´)⊃━☆ﾟ.*・｡ﾟ
            mergeSort(start+(len/2), end);      // Uses evil coroutine side effect magic to halt during sorts while maintaining recursive implementation
            merge(start, end, len/2);
        }
    }

    private void merge(int start, int end, int split) throws InterruptedException {
        E[] data = yieldingArray.getData();
        Object[] workspace = new Object[end-start];
        int x = start;
        int y = start+split;
        for (int i = 0; i < workspace.length; i++) {
            if (x >= start + split) {
                workspace[i] = data[y++];
            } else if (y >= end) {
                workspace[i] = data[x++];
            } else if (yieldingArray.compare(x,  y) <= 0) {
                workspace[i] = data[x++];
            } else {
                workspace[i] = data[y++];
            }
        }

        for (int i = 0; i < workspace.length; i++) {
            //noinspection unchecked Only objects of type E are inserted
            yieldingArray.set(start+i, (E) workspace[i]);
        }
    }

}
