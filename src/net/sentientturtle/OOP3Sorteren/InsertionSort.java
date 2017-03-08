package net.sentientturtle.OOP3Sorteren;


/**
 * Created by stefa on 8-3-2017.
 */
public class InsertionSort<E extends Comparable<E>> extends AbstractSort<E> {
    public int index;
    private boolean isDone;


    public InsertionSort(E[] data) {
        super(data);
        index = 0;
        isDone = false;
    }


    @Override
    public synchronized boolean step() throws IllegalStateException {
        if (isDone) throw new IllegalStateException("Sort is already completed");
        if (data.length == 1) return (isDone = true);

        int k;
        E currentElement = data[index];
        for (k = index - 1; k >= 0 && currentElement.compareTo(data[k]) < 0; k--) { // TODO: Split into separate steps
            data[k + 1] = data[k];
        }
        data[k + 1] = currentElement;
        return (isDone = ++index >= data.length);
    }


    @Override
    public boolean isDone() {
        return isDone;
    }

}
