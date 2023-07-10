//package MT_Collatz_COP5518;



public class SharedArray {
    private int Counter = 1;
    private int N;
    private static int DEFAULT_N = 5000;
    private int[] histogram;


    public SharedArray(int user_defined_N){
        this.N = user_defined_N;
        this.histogram = new int[this.N];
    }

    public SharedArray(){
        this.N = DEFAULT_N;
        this.histogram = new int[this.N];
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
        this.Counter++;
    }

    public void addStoppingTime(int n, int stopTime){
        this.histogram[n] = stopTime;
    }

    public void printValues() {
        for (int i = 0; i < this.histogram.length; i++){
            //System.out.println(i + 1 + " " + this.histogram[i]);
            System.out.println(this.histogram[i]);
        }
    }

    public void calculateFrequencies() {
        
    }

}