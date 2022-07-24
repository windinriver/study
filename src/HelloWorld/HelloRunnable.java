package HelloWorld;

public class HelloRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("实现Runnable");

    }

    public static void main(String[] args) {
        new Thread(new HelloRunnable()).start();
    }
}
