//package MT_Collatz_COP5518;

import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {
    private int Counter = 1;
    private int N;
    private static int DEFAULT_N = 5000;
    private int[] histogram;

    private ReentrantLock mutex = new ReentrantLock();
    //private ReentrantLock mutexForArray = new ReentrantLock();

    /**
     * Constructor for SharedArray with user-defined N
     * @param user_defined_N the user-defined size of the array
     */
    public SharedArray(int user_defined_N){
        this.N = user_defined_N;
        //this.histogram = new int[this.N];
        this.histogram = new int[this.N/6];
    }

     
    //Default constructor for SharedArray with default N
    public SharedArray(){
        this.N = DEFAULT_N;
        this.histogram = new int[this.N];
    }

    public boolean Continue() {
        return this.Counter < N;
    }
    /**
    * Get the size of the shared array
    * @return the size of the array
    */
    public int getN() {
        return this.N;
    }

    /**
    * Get the current value from the shared array
    * @return the current value
    */
    public int getValue() {
        try{
            this.mutex.lock();
            return this.Counter;
        } finally {
            this.incrementValue();
            this.mutex.unlock();
        }
        
    }

    
    //Increment the current value in the shared array
    public void incrementValue() {
        try {
            this.mutex.lock();
            this.Counter++;
        } finally {
            this.mutex.unlock();
        }
       
    }

    public void addStoppingTime(int stopTime){
        // try {
        //     this.mutexForArray.lock();
        //     //this.histogram[n] = stopTime;
        //     this.histogram[stopTime]++;
        // } finally {
        //     this.mutexForArray.unlock();
        // }
        try {
            this.mutex.lock();
            //this.histogram[n] = stopTime;
            this.histogram[stopTime]++;
        } finally {
            this.mutex.unlock();
        }
    /**
    * Add the stopping time to the shared array at index n
    * @param n the index in the array
    * @param stopTime the stopping time to be added
    */
    public void addStoppingTime(int n, int stopTime){
        this.histogram[n] = stopTime;
    }
    
    //Print the values stored in the shared array
    public void printValues() {
        for (int i = 0; i < this.histogram.length; i++){
            System.out.println("k=" + (i + 1) + ", " +this.histogram[i]);
        }
    }

    
    //Calculate the frequencies of stopping times in the shared array
    public void calculateFrequencies() {
        
    }

}
