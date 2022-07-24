package ProConsumer;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource {
	private volatile boolean FLAG = true;
	private AtomicInteger atomicInteger = new AtomicInteger();

	private BlockingQueue<String> blockingQueue = null;

	public MyResource(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	public void myProd() throws InterruptedException {
		String data = null;
		boolean retValue;
		while (FLAG) {
			data = atomicInteger.incrementAndGet() + "";
			retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
			if (retValue) {
				System.out.println(Thread.currentThread().getName() + "\t"
						+ "插⼊队列" + data + "成功");
			} else {
				System.out.println(Thread.currentThread().getName() + "\t"
						+ "插⼊队列" + data + "失败");
			}
			TimeUnit.SECONDS.sleep(1);
		}
		System.out.println(Thread.currentThread().getName() + "\t⽼板叫停了， FLAG已更新为false，停⽌⽣产");
	}


	public  void myCons() throws Exception {
		String res;
		while (FLAG) {
			res = blockingQueue.poll(2L, TimeUnit.SECONDS);
			if (null == res || "".equals(res)) {
				System.out.println(Thread.currentThread().getName() + "\t 超过2秒钟没有消费，退出消费");
				return;
			}
			System.out.println(Thread.currentThread().getName() + "\t\t消费 队列" + res + "成功");
		}
	}

	public void stop() {
		this.FLAG = false;
	}
}

public class BlockingQueueDemo {

	public static void main(String[] args) {
		MyResource myResource = new MyResource(new ArrayBlockingQueue<>(5));
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t⽣产线 程启动");
			try {
				myResource.myProd();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "prod").start();
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t⽣产线 程启动");
			try {
				myResource.myProd();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "prod-2").start();
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t消费线 程启动");
			try {
				myResource.myCons();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "cons").start();
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t消费线 程启动");
			try {
				myResource.myCons();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "cons-2").start();


		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("5秒钟后，叫停");
		myResource.stop();
	}
}
