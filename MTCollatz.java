//package MT_Collatz_COP5518;

/**
 * This is a program of Multi-Threaded Collatz Stopping Time Generator
 * The programm computes the Collatz sequences for numbers between 1 and 10000 using 8 threads
 * and is solving for a race condition that can result when multiple threads compete for the same resources
    
   @authors:    Ben Yanick and Gina  Wittman
   @date:      07/11/2023
   
 * COP5518 Project1
 * File name: MTCollatz.java
*/

/**Import classes
 * Instant for time tracking
 * ArrayList for dynamic arrays
 * ReentrantLock for thread synchronization using locks
*/
import java.time.Instant;
import java.time.Duration;
import java.util.concurrent.locks.ReentrantLock;


//import MT_Collatz_COP5518.SharedArray;

/**
 * Constructor for MTCollatz thread
 * @param threadID the ID of the thread
 * @param sharedArray the shared array object
 * @param mutex the lock for thread synchronization
*/

public class MTCollatz extends Thread {
    private int ThreadID;
    private SharedArray _sharedArray;

    public MTCollatz(int threadID, SharedArray sharedArray, ReentrantLock mutex){
        this.ThreadID = threadID;
        this._sharedArray = sharedArray;
    }

    @Override
    public void run() {
        //while (this._sharedArray.Continue()){
        while (true){
            int n;
            int timeStep = 1;

            n = this._sharedArray.getValue();
            if (n > this._sharedArray.getN()){
                break;
            }

            while (n > 1){
            this.mutex.lock();
            try {
                // Get current value from shared array
                n_ORIG = this._sharedArray.getValue();
                if (n_ORIG == -1){
                    break;
                }
                n = n_ORIG;
                // Increment value and counter
                this._sharedArray.incrementValue();
                counter++;
            } finally {
                this.mutex.unlock();
            }
            
            // Perform Collatz calculation
            while (n != 1){
                if (n % 2 == 0){
                    n = n / 2;
                } else {
                    n = 3*n + 1;
                }
                timeStep++;
            }
            this._sharedArray.addStoppingTime(timeStep);

            this.mutex.lock();

            try{
                // Add stopping time to shared array
                this._sharedArray.addStoppingTime(n_ORIG - 1, timeStep);
            } finally {
                this.mutex.unlock();
                
            }
        }
    }

    public static void printTime(int n, int thread_count, Duration time) {
        System.err.println(n + ", " + thread_count + ", " + time.toMillis());
    }

    public static void main(String[] args){
        // Retrieve input values from command-line arguments
        int n = Integer.parseInt(args[0]);
        int thread_count = Integer.parseInt(args[1]);
        MTCollatz[] threads = new MTCollatz[thread_count];
        

        // Instance of sharedArray that will be referenced by all MTCollatz objects created
        // Create a sharedArray object to be referenced by all MTCollatz threads
        SharedArray sharedArray = new SharedArray(n);
        ReentrantLock lock = new ReentrantLock();
        
        // Create and start MTCollatz threads
        for (int i = 0; i < thread_count; i++){
            threads[i] = new MTCollatz(6, sharedArray, lock);
        }

        Instant start = Instant.now();

        for (int i = 0; i < thread_count; i++){
            threads[i].start();
        }

        try {
            // Wait for all threads to finish
            for (int i = 0; i < thread_count; i++){
                threads[i].join();
            }
            
        } catch (InterruptedException xcp) {
            System.out.println("unable to join threads");
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        
        MTCollatz.printTime(n, thread_count, timeElapsed);
        
      // Print the values in the shared array
        sharedArray.printValues();
    }
}
