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
     * Steps this coroutine until the next yield, or it finishes
     * Will do nothing if this coroutine has already finished
     * @throws InterruptedException If this coroutine was interrupted
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
     * Repeatedly steps until this coroutine is finished
     * @throws InterruptedException If this coroutine is interrupted at any point during the step-through
     */
    public void stepThrough() throws InterruptedException {
        while (!this.isFinished()) {
            step();
        }
    }

    /**
     * Halts the calling thread
     * @throws InterruptedException If the calling thread is interrupted
     * @throws IllegalStateException If this method is called from outside a coroutine
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

    // Sets running state
    private synchronized void setRunning(boolean running) {
        this.isRunning = running;
        if (!running) this.notifyAll();
    }

    /**
     * Returns the current state of this coroutine
     * @return True if this coroutine is running (not yielded) false otherwise.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Sets the setInterrupt flag on this coroutine
     */
    private void setInterrupt() {
        this.isInterrupted = true;
    }

    /**
     * Returns the interrupted state of this coroutine
     * @return True if this coroutine was interrupted, false otherwise
     */
    public boolean isInterrupted() {
        return isInterrupted;
    }

    /**
     * Sets this coroutine's finished flag to true
     */
    private void setFinished() {
        isFinished = true;
    }

    /**
     * Returns the current completion state of this coroutine
     * May return false shortly after this coroutine finishes before the thread is marked dead
     * @return True if this coroutine has finished, false otherwise
     */
    public boolean isFinished() {
        return isFinished;
    }


    /**
     * Sets this coroutine's started flag to true
     */
    private void setStarted() {
        isStarted = true;
    }

    /**
     * Returns the started status of this coroutine
     * @return True if this coroutine has started, false otherwise
     */
    private boolean isStarted() {
        return isStarted;
    }

    /**
     * Sets the stop cause of this coroutine
     * @param stopCause Throwable that caused this coroutine to stop
     */
    private void setStopCause(Throwable stopCause) {
        this.stopCause = stopCause;
    }

    /**
     * Gets the throwable that stopped this coroutine, or null if either the coroutine is not yet finished, or finished without throwing anything
     * @return Stop cause of this coroutine
     */
    public Throwable getStopCause() {
        return stopCause;
    }
}
