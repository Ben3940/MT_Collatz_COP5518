/**
 * This is the SharedArray wrapper class for the histogram and counter variables (the shared resources)
 * The main program (MTCollatz.java) will create threads which will request from this class access to these shared resources
 * This wrapper class contains the mutex to control access to these shared resources and prevent race condtions
    
   @authors:    Ben Yanick and Gina  Wittman
   @date:      07/11/2023
   
 * COP5518 Project1
 * File name: SharedArray.java
*/

import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {
    private int Counter = 1;
    private int N;
    private static int DEFAULT_N = 5000;
    private int[] histogram;

    private ReentrantLock mutex = new ReentrantLock();

    // Used when implementing two mutexes during experiments
    //private ReentrantLock mutexForArray = new ReentrantLock();

    /**
     * Constructor for SharedArray with user-defined N
     * @param user_defined_N the user-defined size of the array
     */
    public SharedArray(int user_defined_N){
        this.N = user_defined_N;

        // We devide by 6 to cut off "stopping times" towards the end whose occurrences are 0
        this.histogram = new int[this.N/6];
    }

     
    /**
    *  Default constructor for SharedArray with default N
    */
    public SharedArray(){
        this.N = DEFAULT_N;
        this.histogram = new int[this.N];
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

    
    //
    /**
     * Increment the current value in the shared array
     */
    public void incrementValue() {
        try {
            this.mutex.lock();
            this.Counter++;
        } finally {
            this.mutex.unlock();
        }
       
    }

    /**
     * Adds stopTime to shared histogram
     * @param stopTime
     */
    public void addStoppingTime(int stopTime){

        // Implementation when using two mutex to test program.
        // try {
        //     this.mutexForArray.lock();
        //     //this.histogram[n] = stopTime;
        //     this.histogram[stopTime]++;
        // } finally {
        //     this.mutexForArray.unlock();
        // }

        try {
            this.mutex.lock();
            this.histogram[stopTime]++;
        } finally {
            this.mutex.unlock();
        }
    }
    
    //Print the values stored in the shared array
    /**
     * Prints histogram to stdout
     */
    public void printValues() {
        for (int i = 0; i < this.histogram.length; i++){
            System.out.println("k=" + (i + 1) + ", " +this.histogram[i]);
        }
    }
}
