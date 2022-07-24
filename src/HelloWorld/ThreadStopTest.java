package HelloWorld;

public class ThreadStopTest {

    public static void main(String[] args) throws InterruptedException {
//        StopThread stopThread = new StopThread();
//        stopThread.start();
//        Thread.sleep(1000L);
//        stopThread.stop();
//        while (stopThread.isAlive()) {}
//        stopThread.print();
// /////////////////////////////////////////////////////////////////////

        //使用自己实现的中断
        Runnable runnable = new ThreadMyStopRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(10000L);
        ((ThreadMyStopRunnable) runnable).zhongduan();
        ((ThreadMyStopRunnable) runnable).doPring();


    }

    private static class StopThread extends Thread {
        private int i;
        private int j;
        @Override
        public void run(){
            synchronized (this) {
                ++i;
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ++j;
            }
        }

        public void print() {
            System.out.println("i=" + i);
            System.out.println("j=" + j);
        }
    }
}
