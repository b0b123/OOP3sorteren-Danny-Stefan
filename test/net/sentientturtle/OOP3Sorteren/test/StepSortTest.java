package net.sentientturtle.OOP3Sorteren.test;

import net.sentientturtle.OOP3Sorteren.BubbleStepSort;
import net.sentientturtle.OOP3Sorteren.StepSort;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class StepSortTest {
    private void testSort(StepSort<Integer> sort) {
        Random random = new Random();

        Integer[] dataSet = new Integer[500];
        for (int i = 0; i < dataSet.length; i++) dataSet[i] = random.nextInt();

        Integer[] clone = dataSet.clone();
        Arrays.sort(clone);
        sort.setData(dataSet);

        //noinspection StatementWithEmptyBody
        while (!sort.step()){}
        assertArrayEquals(clone, sort.getData());
    }

    // Test if BubbleStepSort properly sorts
    @Test
    public void testBubbleSort() {
        BubbleStepSort<Integer> bubbleStepSort = new BubbleStepSort<>();
        testSort(bubbleStepSort);
    }
}