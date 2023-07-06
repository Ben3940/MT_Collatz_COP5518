package MT_Collatz_COP5518;

import java.util.concurrent.locks.ReentrantLock;

public class SharedArray {
    private int Counter = 0;
    private int N;
    private static int DEFAULT_N = 5000;

    public SharedArray(int user_defined_N){
        this.N = user_defined_N;
    }

    public SharedArray(){
        this.N = DEFAULT_N;
    }

    public int getN() {
        return this.N;
    }
}