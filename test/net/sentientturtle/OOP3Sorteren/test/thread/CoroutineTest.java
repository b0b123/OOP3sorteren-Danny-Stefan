package net.sentientturtle.OOP3Sorteren.test.thread;

import net.sentientturtle.OOP3Sorteren.thread.Coroutine;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class CoroutineTest {
    @Test
    public void testCoroutine() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        Coroutine coroutine = new Coroutine() {
            @Override
            protected void run() throws InterruptedException {
                atomicInteger.incrementAndGet();
                Coroutine.yield();
                atomicInteger.incrementAndGet();
            }
        };
        assert atomicInteger.get() == 0;
        coroutine.step();
        assert atomicInteger.get() == 1;
        assert !coroutine.isFinished();
        coroutine.stepThrough();
        assert atomicInteger.get() == 2;
        assert coroutine.isFinished();

        atomicInteger.set(0);
        coroutine = new Coroutine() {
            @Override
            protected void run() throws InterruptedException {
                atomicInteger.incrementAndGet();
                Coroutine.yield();
                atomicInteger.incrementAndGet();
            }
        };
        assert atomicInteger.get() == 0;
        coroutine.step();
        assert atomicInteger.get() == 1;
        coroutine.stop();
        assert atomicInteger.get() == 1;
        assert coroutine.isFinished();

        RuntimeException exception = new RuntimeException("TEST");
        coroutine = new Coroutine() {
            @Override
            protected void run() throws InterruptedException {
                throw exception;
            }
        };
        coroutine.step();
        assert coroutine.isFinished();
        assert coroutine.getStopCause() == exception;
    }
}
