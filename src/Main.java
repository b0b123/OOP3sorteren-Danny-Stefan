import net.sentientturtle.OOP3Sorteren.BubbleStepSort;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Integer[] data = new Integer[20];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(200);
        }
        BubbleStepSort<Integer> bubbleStepSort = new BubbleStepSort<>(data);
        do {
            System.out.println(Arrays.toString(bubbleStepSort.getData()) + "\t" + bubbleStepSort.index);
        } while (!bubbleStepSort.step());
    }
}
