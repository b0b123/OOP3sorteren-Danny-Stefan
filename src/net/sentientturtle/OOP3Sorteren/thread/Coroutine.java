package net.sentientturtle.OOP3Sorteren.thread;

import java.util.*;

/**
 * Basic coroutine implementation, uses a separate thread for each coroutine
 */
public class Coroutine {
    private static Map<Thread, YieldingRunnable> threadMap = Collections.synchronizedMap(new HashMap<>());
    private final Thread thread;
    private final YieldingRunnable yieldingRunnable;

    /**
     * Creates a new coroutine with the given yielding runnable
     * @param yieldingRunnable Yielding runnable to execute in this coroutine
     */
    public Coroutine(YieldingRunnable yieldingRunnable) {
        this.yieldingRunnable = yieldingRunnable;
        this.thread = new Thread(() -> {
            yieldingRunnable.setStarted();
            yieldingRunnable.setRunning(true);
            try {
                Coroutine.yield();
                yieldingRunnable.run();
            } catch (InterruptedException e) {
                yieldingRunnable.setFinished();
                yieldingRunnable.setInterrupt();
            } catch (Throwable e) {
                yieldingRunnable.setStopCause(e);
            }
            yieldingRunnable.setFinished();
            yieldingRunnable.setRunning(false);
        });
        threadMap.put(thread, yieldingRunnable);
        thread.start();
        while (!yieldingRunnable.isStarted()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops this coroutine and interrupts it's thread
     */
    public void stop() {
        thread.interrupt();
        while (!yieldingRunnable.isFinished()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Steps this coroutine's runnable until the next yield, or the runnable finishes
     * Will do nothing if the runnable has already finished
     * @throws InterruptedException If the runnable was interrupted
     */
    public void step() throws InterruptedException {
        if (!yieldingRunnable.isFinished()) {
            synchronized (thread) {
                thread.notify();
            }
            synchronized (yieldingRunnable) {
                yieldingRunnable.wait();
            }
            if (yieldingRunnable.isInterrupted()) throw new InterruptedException();
            if (yieldingRunnable.isFinished()) threadMap.remove(thread);
        }
    }

    /**
     * Repeatedly steps until this coroutine's runnable is finished
     * @throws InterruptedException If this coroutine's runnable is interrupted at any point during the step-through
     */
    public void stepThrough() throws InterruptedException {
        while (!yieldingRunnable.isFinished()) {
            step();
        }
    }

    /**
     * Halts the calling thread
     * @throws InterruptedException If the calling thread is interrupted
     * @throws IllegalStateException If this method is called from a thread not running in a YieldingRunnable
     */
    public static void yield() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        if (threadMap.containsKey(currentThread)) {
            threadMap.get(currentThread).setRunning(false);
            synchronized (currentThread) {
                currentThread.wait();
            }
            threadMap.get(currentThread).setRunning(true);
        } else {
            throw new IllegalStateException("Yield called from a non-coroutine thread!");
        }
    }

    /**
     * Returns the current completion state of this coroutine
     * May return false shortly after this coroutine's runnable finishes before the thread is marked dead
     * @return True if this coroutine's runnable has finished, false otherwise
     */
    public boolean isDone() {
        return yieldingRunnable.isFinished();
    }

    /**
     * Gets this coroutine's runnable
     * @return this coroutine's runnable
     */
    public YieldingRunnable getRunnable() {
        return yieldingRunnable;
    }
}
