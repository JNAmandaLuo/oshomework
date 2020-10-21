package nachos.threads;

import nachos.machine.*;

/**
 * An implementation of condition variables that disables interrupt()s for
 * synchronization.
 *
 * <p>
 * You must implement this.
 *
 * @see nachos.threads.Condition
 */
public class Condition2 {
    /**
     * Allocate a new condition variable.
     *
     * @param conditionLock the lock associated with this condition variable. The
     *                      current thread must hold this lock whenever it uses
     *                      <tt>sleep()</tt>, <tt>wake()</tt>, or
     *                      <tt>wakeAll()</tt>.
     */
    public Condition2(Lock conditionLock) {
        this.conditionLock = conditionLock;

        // my code begin
        this.waitQueue = ThreadedKernel.scheduler.newThreadQueue(false);
        // my code end
    }

    /**
     * Atomically release the associated lock and go to sleep on this condition
     * variable until another thread wakes it using <tt>wake()</tt>. The current
     * thread must hold the associated lock. The thread will automatically reacquire
     * the lock before <tt>sleep()</tt> returns.
     */
    public void sleep() {
        Lib.assertTrue(conditionLock.isHeldByCurrentThread());

        conditionLock.release();
        
        // my code begin
        boolean intStatus = Machine.interrupt().disable();  // 线程队列的方法必须在中断禁用时才能调用
		waitQueue.waitForAccess(KThread.currentThread());
		KThread.sleep();
		Machine.interrupt().restore(intStatus);
        // my code end

        conditionLock.acquire();
    }

    /**
     * Wake up at most one thread sleeping on this condition variable. The current
     * thread must hold the associated lock.
     */
    public void wake() {
        Lib.assertTrue(conditionLock.isHeldByCurrentThread());

        // my code begin
        boolean intStatus = Machine.interrupt().disable();
		KThread thread = waitQueue.nextThread();
		if (thread != null) {
			thread.ready();
        }
        Machine.interrupt().restore(intStatus);
        // my code end
    }

    /**
     * Wake up all threads sleeping on this condition variable. The current thread
     * must hold the associated lock.
     */
    public void wakeAll() {
        Lib.assertTrue(conditionLock.isHeldByCurrentThread());

        // my code begin
        boolean intStatus = Machine.interrupt().disable();
        KThread thread;
        while(true){
            thread = waitQueue.nextThread();
            if (thread != null) {
                thread.ready();
            }
            else
            {
                break;
            }
        }
        Machine.interrupt().restore(intStatus);
        // my code end

    }

    private Lock conditionLock;

    // my code begin
    private ThreadQueue waitQueue;
    // my code end
}
