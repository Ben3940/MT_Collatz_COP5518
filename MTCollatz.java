import java.time.Instant;
import java.time.Duration;
import java.util.concurrent.locks.ReentrantLock;



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
                if (n % 2 == 0){
                    n = n / 2;
                } else {
                    n = 3*n + 1;
                }
                timeStep++;
            }
            this._sharedArray.addStoppingTime(timeStep);
            
        }
    }

    public static void printTime(int n, int thread_count, Duration time) {
        System.err.println(n + ", " + thread_count + ", " + time.toMillis());
    }

    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int thread_count = Integer.parseInt(args[1]);
        MTCollatz[] threads = new MTCollatz[thread_count];

        

        // Instance of sharedArray that will be referenced by all MTCollatz objects created
        SharedArray sharedArray = new SharedArray(n);
        ReentrantLock lock = new ReentrantLock();

        for (int i = 0; i < thread_count; i++){
            threads[i] = new MTCollatz(6, sharedArray, lock);
        }

        Instant start = Instant.now();

        for (int i = 0; i < thread_count; i++){
            threads[i].start();
        }

        try {
            for (int i = 0; i < thread_count; i++){
                threads[i].join();
            }
            
        } catch (InterruptedException xcp) {
            System.out.println("unable to join threads");
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        
        sharedArray.printValues();
        //MTCollatz.printTime(n, thread_count, timeElapsed);
        //sharedArray.printThreadContributions();
    }
}
