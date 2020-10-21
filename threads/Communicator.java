package nachos.threads;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>,
 * and multiple threads can be waiting to <i>listen</i>. But there should never
 * be a time when both a speaker and a listener are waiting, because the two
 * threads can be paired off at this point.
 */
public class Communicator {
    /**
     * Allocate a new communicator.
     */
    public Communicator() {
        // my code begin
        this.speakerQueue = ThreadedKernel.scheduler.newThreadQueue(false);
        this.listenerQueue = ThreadedKernel.scheduler.newThreadQueue(false);
        // my code end
    }

    /**
     * Wait for a thread to listen through this communicator, and then transfer
     * <i>word</i> to the listener.
     *
     * <p>
     * Does not return until this thread is paired up with a listening thread.
     * Exactly one listener should receive <i>word</i>.
     *
     * @param	word	the integer to transfer.
     */
    public void speak(int word) {
        // my code begin
        KThread listener =  listenerQueue.nextThread();
        if (listener != null)
        {
            // 配对成功

        }
        // my code end
    }

    /**
     * Wait for a thread to speak through this communicator, and then return
     * the <i>word</i> that thread passed to <tt>speak()</tt>.
     *
     * @return	the integer transferred.
     */    
    public int listen() {
	    return 0;
    }

    // my code begin
    private ThreadQueue speakerQueue;
    private ThreadQueue listenerQueue;
    // my code end
}
