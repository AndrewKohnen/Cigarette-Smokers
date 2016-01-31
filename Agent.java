import java.util.Random;
import java.util.concurrent.Semaphore;
public class Agent extends Thread{
	private Semaphore selects;
	private Semaphore tobacco;
	private Semaphore spark;
	private Semaphore paper;
	private Random rn = new Random();
	private int round; 
	
	public Agent (Semaphore select, Semaphore t, Semaphore p, Semaphore s){
		selects = select;
		tobacco = t;
		paper = p;
		spark = s;
	}
	
	public void run(){
		while(true){
			try {
				//start the process by picking a random number
				sleep(2000);
				System.out.println("--------------------------------------------------");
				round = rn.nextInt(3);
				System.out.println("Agatha is selecting");
				//based on that random number place two objects on the table 
				if (round == 0){
					selects.acquire();
					paper.release(); System.out.println("Placing Paper");
					tobacco.release(); System.out.println("Placing Tobacco");
				}
				else if (round == 1){
					selects.acquire();
					spark.release(); System.out.println("Placing Match");
					tobacco.release();System.out.println("Placing Tobacco");
				}
				else if (round == 2){
					selects.acquire();
					spark.release(); System.out.println("Placing Match");
					paper.release(); System.out.println("Placing Paper");
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
