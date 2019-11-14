package ClientServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
 
public class ServerThread extends Thread {
    private Socket socket;
 
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
 
    @SuppressWarnings("unchecked")
	public void run() {
        System.out.println("Processing: " + socket);
        try 
        {
        	ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            OutputStream os = socket.getOutputStream();
            
            List<Integer> ds = new ArrayList<Integer>();
            ds = (List<Integer>) ois.readObject();
            Collections.sort(ds);
            oos.writeObject(ds);
            
            ExecutorService executor = Executors.newFixedThreadPool(10);
            List<Future<Integer>> list = new ArrayList<Future<Integer>>();
            for (int i :ds) {
                ThreadCallable c = new ThreadCallable(i);
                list.add(executor.submit(c));
            }
            int s =0;
            for (Future<Integer> f : list) {
                s =s + f.get();
            }
            os.write(s);
        } catch (IOException | ClassNotFoundException | InterruptedException | ExecutionException e) {
            System.err.println("Request Processing Error: " + e);
        }
        System.out.println("Complete processing: " + socket);
        
    }
}
