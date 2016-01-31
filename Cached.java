import java.util.concurrent.Semaphore;
public class Cached extends Thread{
	//the elements taken from control 
	private Semaphore resource; //will be defined by what we pass from main, i.e. cache_paper 
	private Semaphore[] cache_Array;
	private Semaphore mutex;
	private int[] counter;
	
	//unqiue elements that identify the resource 
	private int resource_id;
	private String name; 
	
	public Cached (Semaphore res, Semaphore[] a, Semaphore m, int rid, String n, int[] t){
		resource = res;
		cache_Array = a;
		mutex = m;
		resource_id = rid;
		name = n;
		counter = t;
	}
	
	//clean out the Semaphore array to compensate the numerous releases made by the program 
	//made in the spots 0 1 and 3
	public static void ArrayClean (Semaphore[] a){
		for(int i = 0; i < a.length; i++){
			int j = a[i].availablePermits();
			if (j > 2){
				a[i].drainPermits();
			}
		}
	}
	
	public void run(){
		while(true){
			try {
				//is paper has been placed on the table count it
				resource.acquire();
				ArrayClean(cache_Array);
				System.out.println(name + " is being taken");
				//increment the array by the respective amount the allow another Cache resource to act 
				mutex.acquire();
				counter[0] = counter[0] + resource_id;
				System.out.println("Incremented counter by " + resource_id + " to " + counter[0]);
				cache_Array[counter[0]-1].release();
				System.out.println("Array." + counter[0] + ".up");
				mutex.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
