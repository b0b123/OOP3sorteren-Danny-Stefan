package net.sentientturtle.OOP3Sorteren.test;

import net.sentientturtle.OOP3Sorteren.sort.AbstractSort;
import net.sentientturtle.OOP3Sorteren.sort.BubbleSort;
import net.sentientturtle.OOP3Sorteren.sort.DataSet;
import net.sentientturtle.OOP3Sorteren.sort.InsertionSort;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class AbstractSortTest {
    private final static boolean IS_VERBOSE = true;
    private Random random = new Random();

    private void testSort(AbstractSort<Integer> sort) {
        System.out.println("TESTING SORT: " + sort.getClass().getSimpleName());
        Integer[] dataSet = sort.getDataSet().getData();
        Integer[] clone = dataSet.clone();
        Arrays.sort(clone);

        //noinspection StatementWithEmptyBody
        while (!sort.step()){
            if (IS_VERBOSE) System.out.println(Arrays.toString(dataSet));
        }
        if (IS_VERBOSE) System.out.println(Arrays.toString(dataSet));
        assert sort.isDone();
        assertArrayEquals(clone, sort.getDataSet().getData());
        System.out.println();
    }

    private DataSet<Integer> getDataSet() {
        Integer[] data = new Integer[10];
        for (int i = 0; i < data.length; i++) data[i] = random.nextInt(20);
        return new DataSet<>(data);
    }

    // Test if BubbleSort properly sorts
    @Test
    public void testBubbleSort() {
        testSort(new BubbleSort<>(getDataSet()));
    }

    @Test
    public void testInsertionSort() {testSort(new InsertionSort<>(getDataSet()));}
}