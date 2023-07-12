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
*/
import java.time.Instant;
import java.time.Duration;

/**
 * Constructor for MTCollatz thread
 * @param threadID the ID of the thread
 * @param sharedArray the shared array object
*/

public class MTCollatz extends Thread {
    private int ThreadID;
    private SharedArray _sharedArray;

    /**
     * Constructor for class
     * 
     * @param threadID
     * @param sharedArray
     */
    public MTCollatz(int threadID, SharedArray sharedArray){
        this.ThreadID = threadID;
        this._sharedArray = sharedArray;
    }

    /*
     * Run method for a thread.  Thread will grab the next value, compute the Collatz stopping time, and push it to the shared histogram array
     */
    @Override
    public void run() {
        while (true){
            int n;
            int timeStep = 1;

            // Grab next n
            n = this._sharedArray.getValue();
            if (n > this._sharedArray.getN()){
                break;
            }

            // Perform Collatz calculation
            while (n > 1){
                if (n % 2 == 0){
                    n = n / 2;
                } else {
                    n = 3*n + 1;
                }
                timeStep++;
            }

            // Push stopping time to histogram
            this._sharedArray.addStoppingTime(timeStep);
        }
    }

    /**
     * Prints execution time for given range 1...n and number of threads used.  Prints content to stderr
     * @param n
     * @param thread_count
     * @param time
     */
    public static void printTime(int n, int thread_count, Duration time) {
        System.err.println(n + ", " + thread_count + ", " + time.toMillis());
    }

    public static void main(String[] args){
        // Retrieve input values from command-line arguments
        int n = Integer.parseInt(args[0]);
        int thread_count = Integer.parseInt(args[1]);
        MTCollatz[] threads = new MTCollatz[thread_count];
        

        // Instance of sharedArray that will be referenced by all MTCollatz objects created
        SharedArray sharedArray = new SharedArray(n);
        
        // Create and start MTCollatz threads
        for (int i = 0; i < thread_count; i++){
            threads[i] = new MTCollatz(6, sharedArray);
        }

        // Start timing program
        Instant start = Instant.now();

        // Have threads start computing stopping times
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

        // Stop timing program
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        
        // Print the values in the shared array to stdout
        sharedArray.printValues();

        // Print execution times to stderr
        MTCollatz.printTime(n, thread_count, timeElapsed);
    }
}
