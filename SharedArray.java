//package MT_Collatz_COP5518;

import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {
    private int Counter = 1;
    private int N;
    private static int DEFAULT_N = 5000;
    private int[] histogram;

    private ReentrantLock mutex = new ReentrantLock();
    //private ReentrantLock mutexForArray = new ReentrantLock();

    public SharedArray(int user_defined_N){
        this.N = user_defined_N;
        //this.histogram = new int[this.N];
        this.histogram = new int[this.N/6];
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
        try{
            this.mutex.lock();
            return this.Counter;
        } finally {
            this.incrementValue();
            this.mutex.unlock();
        }
        
    }

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
    }

    public void printValues() {
        for (int i = 0; i < this.histogram.length; i++){
            System.out.println("k=" + (i + 1) + ", " +this.histogram[i]);
        }
    }

    public void calculateFrequencies() {
        
    }

}