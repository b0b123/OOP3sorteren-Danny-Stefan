package net.sentientturtle.OOP3Sorteren.thread;

import java.util.*;

/**
 * Basic coroutine implementation, uses a separate thread for each coroutine
 */
public abstract class Coroutine {
    private static Map<Thread, Coroutine> threadMap = Collections.synchronizedMap(new HashMap<>());
    private final Thread thread;

    private boolean isStarted = false;
    private boolean isFinished = false;
    private boolean isRunning = false;
    private boolean isInterrupted = false;
    private Throwable stopCause = null;

    /**
     * Creates a new coroutine
     */
    public Coroutine() {
        Coroutine coroutine = this;
        this.thread = new Thread(() -> {
            coroutine.setStarted();
            coroutine.setRunning(true);
            try {
                Coroutine.yield();
                coroutine.run();
            } catch (InterruptedException e) {
                coroutine.setFinished();
                coroutine.setInterrupt();
            } catch (Throwable e) {
                coroutine.setStopCause(e);
            }
            coroutine.setFinished();
            coroutine.setRunning(false);
        });
        threadMap.put(thread, coroutine);
        thread.start();
        while (!coroutine.isStarted()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void run() throws InterruptedException;

    /**
     * Stops this coroutine and interrupts it's thread
     */
    public void stop() {
        thread.interrupt();
        while (!this.isFinished()) {
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
        if (!this.isFinished()) {
            synchronized (thread) {
                thread.notify();
            }
            synchronized (this) {
                this.wait();
            }
            if (this.isInterrupted()) throw new InterruptedException();
            if (this.isFinished()) threadMap.remove(thread);
        }
    }

    /**
     * Repeatedly steps until this coroutine's runnable is finished
     * @throws InterruptedException If this coroutine's runnable is interrupted at any point during the step-through
     */
    public void stepThrough() throws InterruptedException {
        while (!this.isFinished()) {
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
            Coroutine coroutine = threadMap.get(currentThread);
            coroutine.setRunning(false);
            synchronized (currentThread) {
                currentThread.wait();
            }
            coroutine.setRunning(true);
        } else {
            throw new IllegalStateException("Yield called from a non-coroutine thread!");
        }
    }

    // Sets running state for this runnable
    private synchronized void setRunning(boolean running) {
        this.isRunning = running;
        if (!running) this.notifyAll();
    }

    /**
     * Returns the current state of this runnable
     * @return True if this runnable is running (not yielded) false otherwise.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Sets the setInterrupt flag on this runnable
     */
    private void setInterrupt() {
        this.isInterrupted = true;
    }

    /**
     * Returns the interrupted state of this runnable
     * @return True if this runnable was interrupted, false otherwise
     */
    public boolean isInterrupted() {
        return isInterrupted;
    }

    /**
     * Sets this runnable's finished flag to true
     */
    private void setFinished() {
        isFinished = true;
    }

    /**
     * Returns the current completion state of this coroutine
     * May return false shortly after this coroutine's runnable finishes before the thread is marked dead
     * @return True if this coroutine's runnable has finished, false otherwise
     */
    public boolean isFinished() {
        return isFinished;
    }


    /**
     * Sets this runnable's started flag to true
     */
    private void setStarted() {
        isStarted = true;
    }

    /**
     * Returns the started status of this runnable
     * @return True if this runnable has started, false otherwise
     */
    public boolean isStarted() {
        return isStarted;
    }

    /**
     * Sets the stop cause of this runnable
     * @param stopCause Throwable that caused this runnable to stop
     */
    private void setStopCause(Throwable stopCause) {
        this.stopCause = stopCause;
    }

    /**
     * Gets the throwable that stopped this runnable, or null if either the runnable is not yet finished, or finished without throwing anything
     * @return Stop cause of this runnable
     */
    public Throwable getStopCause() {
        return stopCause;
    }
}
