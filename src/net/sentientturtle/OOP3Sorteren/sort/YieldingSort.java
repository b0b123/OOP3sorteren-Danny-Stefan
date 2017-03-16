package net.sentientturtle.OOP3Sorteren.sort;

import net.sentientturtle.OOP3Sorteren.thread.Coroutine;

import java.util.Objects;

/**
 * Abstract class for sorting algorithms that yield every comparison and swap
 * @see net.sentientturtle.OOP3Sorteren.thread.Coroutine
 */
// (∩｀-´)⊃━☆ﾟ.*・｡ﾟ
// Uses evil side effect magic to halt during sorts while maintaining recursive implementation
public abstract class YieldingSort<E extends Comparable<E>> extends Coroutine {
    protected YieldingArray<E> yieldingArray;
    /**
     * Creates a new instance of the sort, with the provided yielding array
     * @param yieldingArray YieldingArray to be sorted, may not be null
     */
    public YieldingSort(YieldingArray<E> yieldingArray) {
        this.yieldingArray = Objects.requireNonNull(yieldingArray);
    }

    /**
     * Gets yielding array being sorted
     * @return YieldingArray currently being sorted.
     */
    public YieldingArray<E> getYieldingArray() {
        return yieldingArray;
    }

    /**
     * Sorts this YieldingArray
     */
    protected abstract void run() throws InterruptedException;
}
