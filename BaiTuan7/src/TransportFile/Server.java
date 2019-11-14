package TransportFile;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;
public class Server {

    private static ServerSocket ss;
    private static Socket s = null;

    public static void main(String[] args) throws IOException {

        try {
            ss = new ServerSocket(6969);
            System.out.println("Binding to port " + 6969 + ", please wait  ...");
            System.out.println("Server started: " + ss);
            System.out.println("Waiting Client...");
        } 
        catch (Exception e) {
            System.exit(1);
        }


        while (true) {
            try {
                s = ss.accept();
                System.out.println("Client accepted: " + s);
                Thread t = new Thread(new Transport(s));
                t.start();
            } 
            catch (Exception e) 
            {
                System.err.println("Connection Error..!");
            }
        }
    }
}

