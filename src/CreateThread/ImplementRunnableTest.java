package CreateThread;

public class ImplementRunnableTest implements Runnable{
    @Override
    public void run() {
        System.out.println("实现runnable方式创建线程：" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        System.out.println("主线程");
        new Thread(new ImplementRunnableTest()).start();
        System.out.println("主线程end");
    }
}
