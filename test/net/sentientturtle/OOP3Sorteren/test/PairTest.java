package net.sentientturtle.OOP3Sorteren.test;

import net.sentientturtle.OOP3Sorteren.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class PairTest {
    @Test
    public void testPair() {
        Random random = new Random();
        Pair<Integer, Integer> testPair = new Pair<>();
        int x = random.nextInt();
        int y = x;
        while (x == y) y = random.nextInt();

        Assert.assertNull(testPair.getObject1());
        Assert.assertNull(testPair.getObject2());
        Assert.assertEquals(testPair, new Pair<>());
        Assert.assertFalse(testPair.contains(x));
        Assert.assertFalse(testPair.contains(y));
        Assert.assertEquals(testPair.hashCode(), new Pair<>().hashCode());

        testPair.setObject1(x);

        Assert.assertEquals(testPair.getObject1(), (Integer) x);
        Assert.assertNull(testPair.getObject2());
        Assert.assertNotEquals(testPair, new Pair<>());
        Assert.assertTrue(testPair.contains(x));
        Assert.assertFalse(testPair.contains(y));
        Assert.assertNotEquals(testPair.hashCode(), new Pair<>().hashCode());

        testPair.setObject2(y);

        Assert.assertEquals(testPair.getObject1(), (Integer) x);
        Assert.assertEquals(testPair.getObject2(), (Integer) y);
        Assert.assertNotEquals(testPair, new Pair<>());
        Assert.assertTrue(testPair.contains(x));
        Assert.assertTrue(testPair.contains(y));
        Assert.assertNotEquals(testPair.hashCode(), new Pair<>().hashCode());

        testPair.setObject1(null);

        Assert.assertNull(testPair.getObject1());
        Assert.assertEquals(testPair.getObject2(), (Integer) y);
        Assert.assertNotEquals(testPair, new Pair<>());
        Assert.assertFalse(testPair.contains(x));
        Assert.assertTrue(testPair.contains(y));
        Assert.assertNotEquals(testPair.hashCode(), new Pair<>().hashCode());
    }
}
