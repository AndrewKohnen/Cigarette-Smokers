import java.util.concurrent.Semaphore;
public class Control {
	//the semaphores and semaphore arrays
	private Semaphore[] array = new Semaphore[6];
	private Semaphore mutex;
	private Semaphore selects;
	private Semaphore tobacco;
	private Semaphore paper;
	private Semaphore spark;
	
	//the three smokers at the table, each has an infinite 
	//supply of a resource and requires the other two 
	//items needed for a cigarette places on the table
	private Smokers horacio;
	private Smokers edgar;
	private Smokers arthur;
	
	//The Cached resources to be sure the appropriate increments are made
	//for the respective element combinations 
	private Cached C_tobacco;
	private Cached C_paper;
	private Cached C_spark;
	
	//counter that controls indexing of the Semaphore array 
	private int[] counter;
	
	//the agent that places the items on the table 
	private Agent Agatha;
	
	//constructor that starts the program 
	public Control(){
		main();
	}
	
	public void main (){
		//intializes the array, The semaphores, and the Counters 
		mutex = new Semaphore(1);
		counter = new int[1];
		counter[0] = 0;
		for(int k = 0; k < array.length; k++){
			array[k] = new Semaphore(0);
		}
		paper = new Semaphore(0);
		spark = new Semaphore(0);
		tobacco = new Semaphore(0);
		selects = new Semaphore(1);
		
		//Creates the 7 threads used by the program, 3 smokers and 3 resources plus the agent 
		Agatha = new Agent(selects, tobacco, paper, spark);
		C_tobacco = new Cached (tobacco, array, mutex, 4, "Tobacco", counter);
		C_paper = new Cached (paper, array, mutex, 2, "Paper",counter);
		C_spark = new Cached (spark, array, mutex, 1, "Spark",counter);
		//smokers are given a unique Semaphore element from the array to signify whether they can go 
		//based on the resources that have been places on the table 
		horacio = new Smokers(selects, array, counter, array[2], "Horacio");
		edgar = new Smokers(selects, array, counter, array[5], "Edgar");
		arthur = new Smokers(selects, array, counter, array[4], "Arthur");
		
		Agatha.start();
		C_tobacco.start();
		C_paper.start();
		C_spark.start();
		horacio.start();	
		edgar.start();
		arthur.start();
	}
}
