package ClientServer;

import java.util.concurrent.Callable;

public class ThreadCallable implements Callable<Integer> {
    
    private int a;
 
    public ThreadCallable(int a) {
        this.a = a;
    }
    
    public Integer call() throws Exception {
        return a;
    }
}