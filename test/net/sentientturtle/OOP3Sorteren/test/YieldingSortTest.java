package net.sentientturtle.OOP3Sorteren.test;

import net.sentientturtle.OOP3Sorteren.sort.*;
import net.sentientturtle.OOP3Sorteren.thread.Coroutine;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class YieldingSortTest {
    private final static boolean IS_VERBOSE = true;
    private Random random = new Random();

    private void testSort(YieldingSort<Integer> sort) throws InterruptedException {
        System.out.println("TESTING SORT: " + sort.getClass().getSimpleName());
        Integer[] dataSet = sort.getYieldingArray().getData();
        Integer[] clone = dataSet.clone();
        Arrays.sort(clone);

        sort.stepThrough();
        if (IS_VERBOSE) System.out.println(Arrays.toString(dataSet));
        //assert sort.isDone();
        assertArrayEquals(clone, sort.getYieldingArray().getData());
        System.out.println();
    }

    private YieldingArray<Integer> getDataSet() {
        Integer[] data = new Integer[10];
        for (int i = 0; i < data.length; i++) data[i] = random.nextInt(20);
        return new YieldingArray<>(data);
    }

    // Test if BubbleSort properly sorts
    @Test
    public void testBubbleSort() throws InterruptedException {
        testSort(new BubbleSort<>(getDataSet()));
    }

    @Test
    public void testInsertionSort() throws InterruptedException {
        testSort(new InsertionSort<>(getDataSet()));
    }

    @Test
    public void testQuickSort() throws InterruptedException {
        testSort(new QuickSort<>(getDataSet()));
    }

    @Test
    public void testMergeSort() throws InterruptedException {
        testSort(new MergeSort<>(getDataSet()));
    }
}