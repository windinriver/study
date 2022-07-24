package HelloWorld;

//自己实现线程的中断, 使用状态标志位
public class ThreadMyStopRunnable implements Runnable {

    private boolean isStop = false;
    private int i;
    private int j;

    public void doPring() {
        System.out.println(i);
        System.out.println(j);
    }
    public void zhongduan() {
        isStop = true;
    }
    @Override
    public void run() {
        synchronized (ThreadMyStopRunnable.class) {
            while (!isStop) {
                ++i;
                ++j;

                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
