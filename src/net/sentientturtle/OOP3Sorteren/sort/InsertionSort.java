package net.sentientturtle.OOP3Sorteren.sort;


/**
 * Created by stefa on 8-3-2017.
 */
public class InsertionSort<E extends Comparable<E>> extends AbstractSort<E> {
    private int index;
    private boolean isDone;
    private int sortedLength;
    private E currentElement;

    public InsertionSort(E[] data) {
        super(data);
        index = 0;
        isDone = false;
        sortedLength = 0;
        currentElement = null;
    }


    @Override
    public synchronized boolean step() {
        if (isDone) return true;
        if (data.length == 1) return (isDone = true);

        if (currentElement == null) {
            if (sortedLength > data.length-1) return (isDone = true);
            currentElement = data[sortedLength];
            index = sortedLength;
        }
        if (index > 0 && currentElement.compareTo(data[index-1]) < 0) {
            swap(index, index-1);
            index--;
        } else {
            sortedLength++;
            currentElement = null;
        }
        return false;
    }


    @Override
    public boolean isDone() {
        return isDone;
    }

}
