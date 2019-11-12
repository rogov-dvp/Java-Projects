package parts;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MinotaurAction {
	public static void main(String[] args) {
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(0);
	Runnable runPath = new Runnable() {
		public void run() {
			
			System.out.println("hehexd");
		}
	};
	method();
		
	executor.scheduleAtFixedRate(runPath,0, 1, TimeUnit.SECONDS);
}
	public static void method() {
		System.out.println("hehexd");
	}
}

