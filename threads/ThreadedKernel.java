package nachos.threads;

import nachos.machine.*;

/**
 * A multi-threaded OS kernel.
 */
public class ThreadedKernel extends Kernel {
    /**
     * Allocate a new multi-threaded kernel.
     */
    public ThreadedKernel() {
        super();
    }

    /**
     * Initialize this kernel. Creates a scheduler, the first thread, and an alarm,
     * and enables interrupts. Creates a file system if necessary.
     */
    public void initialize(String[] args) {
        // set scheduler
        // 作业一中指定的调度器为nachos.thread.RoundRobinScheduler
        // 循环调度程序在FIFO队列中跟踪等待线程，使用链表实现。
        // 当一个线程开始等待访问时，它被附加到列表的末尾。
        // 下一个接收访问的线程总是列表中的第一个线程。
        // 这使得访问是在先到先得的基础上提供的。
        String schedulerName = Config.getString("ThreadedKernel.scheduler");
        scheduler = (Scheduler) Lib.constructObject(schedulerName);

        // set fileSystem
        // 作业一中无文件系统对象
        String fileSystemName = Config.getString("ThreadedKernel.fileSystem");
        if (fileSystemName != null)
            fileSystem = (FileSystem) Lib.constructObject(fileSystemName);
        else if (Machine.stubFileSystem() != null)
            fileSystem = Machine.stubFileSystem();
        else
            fileSystem = null;

        // 创建一个线程对象
        // KThread是可用于执行Nachos内核代码的线程。
        // 所有的Nachos线程都是nachos.threads.KThread的实例。
        new KThread(null); 

        // 创建时钟对象
        // 时钟对象利用硬件计时器来提供优先权，并能允许线程休眠到特定时间
        alarm = new Alarm();

        // 启用中断
        Machine.interrupt().enable();
    }

    /**
     * Test this kernel. Test the <tt>KThread</tt>, <tt>Semaphore</tt>,
     * <tt>SynchList</tt>, and <tt>ElevatorBank</tt> classes. Note that the
     * autograder never calls this method, so it is safe to put additional tests
     * here.
     */
    public void selfTest() {
        // 线程自检
        KThread.selfTest();
        // 信号量自检
        Semaphore.selfTest();
        // 同步队列自检
        SynchList.selfTest();
        // 作业一中无ElevatorBank硬件
        if (Machine.bank() != null) {
            ElevatorBank.selfTest();
        }
    }

    /**
     * A threaded kernel does not run user programs, so this method does nothing.
     */
    public void run() {
    }

    /**
     * Terminate this kernel. Never returns.
     */
    public void terminate() {
        Machine.halt();
    }

    /** Globally accessible reference to the scheduler. */
    public static Scheduler scheduler = null;
    /** Globally accessible reference to the alarm. */
    public static Alarm alarm = null;
    /** Globally accessible reference to the file system. */
    public static FileSystem fileSystem = null;

    // dummy variables to make javac smarter
    private static RoundRobinScheduler dummy1 = null;
    private static PriorityScheduler dummy2 = null;
    private static LotteryScheduler dummy3 = null;
    private static Condition2 dummy4 = null;
    private static Communicator dummy5 = null;
    private static Rider dummy6 = null;
    private static ElevatorController dummy7 = null;
}
