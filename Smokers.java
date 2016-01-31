import java.util.concurrent.Semaphore;
public class Smokers extends Thread{
		private Semaphore select; 
		private String name;
		private int[] counter;
		private Semaphore res;
		
		public Smokers (Semaphore s, Semaphore[] array, int[] test,Semaphore r, String n){
			select = s;
			this.counter = test;
			name = n;
			res = r;
		}
		
		public void run (){
			while(true){
				//if the semaphore given to the Smoker has been released then
				//the smoker may act 
				try {
					res.acquire();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// have a smoke then signify to the agent the process can start over again 
					System.out.println(name +" completes his cigarette");
					counter[0] = 0;
					System.out.println("Reset Counter");
					System.out.println(name + " smokes");System.out.println(name + " Stubs Out");
					System.out.println("--------------------------------------------------");
					select.release();
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}

}
