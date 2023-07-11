//package MT_Collatz_COP5518;

import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {
    private int Counter = 1;
    private int N;
    private static int DEFAULT_N = 5000;
    private int[] histogram;
    private ReentrantLock mutex = new ReentrantLock();

    public SharedArray(int user_defined_N){
        this.N = user_defined_N;
        this.histogram = new int[this.N];
    }

    public SharedArray(){
        this.N = DEFAULT_N;
        this.histogram = new int[this.N];
    }

    public boolean Continue() {
        return this.Counter < N;
    }

    public int getN() {
        return this.N;
    }

    public int getValue() {
        if (this.Counter > this.N){
            return -1;
        } 
        return this.Counter;
    }

    public void incrementValue() {
        try {
            this.mutex.lock();
            this.Counter++;
        } finally {
            this.mutex.unlock();
        }
       
    }

    public void addStoppingTime(int n, int stopTime){
        try {
            this.mutex.lock();
            this.histogram[n] = stopTime;
        } finally {
            this.mutex.unlock();
        }
    }

    public void printValues() {
        for (int i = 0; i < this.histogram.length; i++){
            System.out.println(this.histogram[i]);
        }
    }

    public void calculateFrequencies() {
        
    }

}