package MT_Collatz_COP5518;

import java.time.Instant;

import MT_Collatz_COP5518.SharedArray;


public class MTCollatz extends Thread {

    private String _threadID;
    private SharedArray _sharedArray;


    public MTCollatz(String threadID, SharedArray sharedArray){
        this._threadID = threadID;
        this._sharedArray = sharedArray;
    }

    @Override
    public void run() {
        System.out.println("Thread Created");
    }

    public static void main(String[] args){
        // Instance of sharedArray that will be referenced by all MTCollatz objects created
        SharedArray sharedArray = new SharedArray();

    }
}
