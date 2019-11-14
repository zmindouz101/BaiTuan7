package ClientServer;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
 
public class Client {
    public final static String SERVER_IP = "localhost";
    public final static int SERVER_PORT = 9999;
 
    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
    	List<Integer> ds = new ArrayList<Integer>();
    	if(args == null)
    	{
    		System.out.println("Chưa có dữ liệu.>>!");
    	}
    	else
    	{
    		for(String i: args)
    		{
    			int n = Integer.parseInt(i);
    			ds.add(n);
    		}
    	}
        Socket socket = null;
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT); // Connect to server
            System.out.println("Connected: " + socket);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            InputStream is = socket.getInputStream();
            oos.writeObject(ds);
            try {
            	List<Integer> dskq = (List<Integer>) ois.readObject();
            	System.out.println("Danh sach so duoc sap xep:");
            	for(int i : dskq)
            		System.out.print(i+ " ");
            	int kq = is.read();
            	System.out.println("\nTong la: "+ kq);
            }
            catch (IOException e)
            {
            	System.out.println("Khong co phan hoi..!");
            }
        } catch (IOException ie) {
            System.out.println("Can't connect to server");
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}