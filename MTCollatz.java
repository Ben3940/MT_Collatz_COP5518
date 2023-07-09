//package MT_Collatz_COP5518;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


//import MT_Collatz_COP5518.SharedArray;


public class MTCollatz extends Thread {

    private String _threadID;
    private SharedArray _sharedArray;
    private ReentrantLock mutex;


    public MTCollatz(String threadID, SharedArray sharedArray, ReentrantLock mutex){
        this._threadID = threadID;
        this._sharedArray = sharedArray;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        boolean finished = false;
        int counter = 0;
        while (counter < this._sharedArray.getN()){
            int n_ORIG;
            int n;
            int timeStep = 1;

            this.mutex.lock();
            try {
                n_ORIG = this._sharedArray.getValue();
                if (n_ORIG == -1){
                    break;
                }
                n = n_ORIG;
                this._sharedArray.incrementValue();
                counter++;
            } finally {
                this.mutex.unlock();
            }

            while (n != 1){
                if (n % 2 == 0){
                    n = n / 2;
                } else {
                    n = 3*n + 1;
                }
                timeStep++;
            }

            this.mutex.lock();

            try{
                this._sharedArray.addStoppingTime(n_ORIG - 1, timeStep);
            } finally {
                this.mutex.unlock();
                
            }
        }
    }

    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int thread_count = Integer.parseInt(args[1]);
        MTCollatz[] threads = new MTCollatz[thread_count];

        // Instance of sharedArray that will be referenced by all MTCollatz objects created
        SharedArray sharedArray = new SharedArray(n);
        ReentrantLock lock = new ReentrantLock();

        for (int i = 0; i < thread_count; i++){
            threads[i] = new MTCollatz(String.valueOf(i), sharedArray, lock);
            threads[i].start();
        }

        try {
            for (int i = 0; i < thread_count; i++){
                threads[i].join();
            }
        } catch (InterruptedException xcp) {
            System.err.println("unable to join threads");
        }

        sharedArray.printValues();






    }
}
