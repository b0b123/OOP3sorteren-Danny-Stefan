package net.sentientturtle.OOP3Sorteren.thread;

/**
 * Runnable implementation for usage with coroutines
 * @see Coroutine
 */
public abstract class YieldingRunnable {
    private boolean isStarted = false;
    private boolean isFinished = false;
    private boolean isRunning = false;
    private boolean isInterrupted = false;
    private Throwable stopCause = null;

    /**
     * Method called when coroutine is started, can use Coroutine#yield and Coroutine#setInterrupt
     * SHOULD NOT BE CALLED DIRECTLY, use #start instead!
     * @throws InterruptedException Thrown when the runnable is interrupted
     */
    protected abstract void run() throws InterruptedException;

    // Sets running state for this runnable
    synchronized void setRunning(boolean running) {
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
    void setInterrupt() {
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
    void setFinished() {
        isFinished = true;
    }

    /**
     * Gets this runnable's finished status
     * @return True if this runnable has finished executing, false otherwise
     */
    public boolean isFinished() {
        return isFinished;
    }


    /**
     * Sets this runnable's started flag to true
     */
    void setStarted() {
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
    void setStopCause(Throwable stopCause) {
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
