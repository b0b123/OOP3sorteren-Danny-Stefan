package net.sentientturtle.OOP3Sorteren.test.thread;

import net.sentientturtle.OOP3Sorteren.thread.Coroutine;
import net.sentientturtle.OOP3Sorteren.thread.YieldingRunnable;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class CoroutineTest {
    @Test
    public void testCoroutine() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        YieldingRunnable yieldingRunnable = new YieldingRunnable() {
            @Override
            protected void run() throws InterruptedException {
                atomicInteger.incrementAndGet();
                Coroutine.yield();
                atomicInteger.incrementAndGet();
            }
        };
        Coroutine coroutine = new Coroutine(yieldingRunnable);
        assert atomicInteger.get() == 0;
        coroutine.step();
        assert atomicInteger.get() == 1;
        assert !coroutine.isDone();
        coroutine.stepThrough();
        assert atomicInteger.get() == 2;
        assert coroutine.isDone();

        atomicInteger.set(0);
        coroutine = new Coroutine(new YieldingRunnable() {
            @Override
            protected void run() throws InterruptedException {
                atomicInteger.incrementAndGet();
                Coroutine.yield();
                atomicInteger.incrementAndGet();
            }
        });
        assert atomicInteger.get() == 0;
        coroutine.step();
        assert atomicInteger.get() == 1;
        coroutine.stop();
        assert atomicInteger.get() == 1;
        assert coroutine.isDone();

        RuntimeException exception = new RuntimeException("TEST");
        coroutine = new Coroutine(new YieldingRunnable() {
            @Override
            protected void run() throws InterruptedException {
                throw exception;
            }
        });
        coroutine.step();
        assert coroutine.isDone();
        assert coroutine.getRunnable().getStopCause() == exception;
    }
}
