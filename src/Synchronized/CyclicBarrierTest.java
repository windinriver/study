package Synchronized;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {
        int N = 6;
        CyclicBarrier cb = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有运动员已准备完毕，发令枪：跑！");
            }
        });

        for (int i = 0; i < N; i++) {
            Thread t = new Thread(new CyclicBarrierTest.PrepareWork(cb), "运动员[" + i + "]");

            t.start();
            if (i == 3) {
                // 3运动员生病，不能参加了
                Thread.sleep(1000);
                t.interrupt(); // 运动员[3]置中断标志位
            }
        }
        Thread.sleep(2000);

        System.out.println("Barrier是否损坏：" + cb.isBroken());
    }

    private static class PrepareWork implements Runnable {

        private CyclicBarrier cb;

        public PrepareWork(CyclicBarrier cb) {
            this.cb = cb;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + ": 准 备完成");
                cb.await(); // 在栅栏等待

            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + ": 被 中断");
            }catch (BrokenBarrierException e) {
                e.printStackTrace();
            }


        }
    }
}
