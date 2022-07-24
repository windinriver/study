package CreateThread;import java.util.concurrent.*;public class CallableTest implements Callable<Integer> {    private int start;    private int end;    CallableTest(int start, int end){        this.start = start;        this.end = end;    }    @Override    public Integer call() throws Exception {        for (int i= start; i <= end; i++) {            System.out.println(Thread.currentThread().getName() + "i");        }        Thread.sleep(3000);        System.out.println("子线程执行结束");        return start + end;    }    public static void main(String[] args) throws ExecutionException, InterruptedException {        CallableTest callableTest01 = new CallableTest(1, 50);        FutureTask<Integer> futureTask = new FutureTask<>(callableTest01);        new Thread(futureTask).start();        Integer integer = futureTask.get();        System.out.println("主线程会阻塞直到子线程执行结束：执行结果" + integer);    }}