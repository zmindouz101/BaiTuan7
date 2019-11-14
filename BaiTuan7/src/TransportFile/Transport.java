package TransportFile;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;
public class Transport implements Runnable {

    private Socket s;
    private BufferedReader in = null;

    public Transport(Socket client) {
        this.s = client;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(
                    s.getInputStream()));
            String chon;
            while ((chon = in.readLine()) != null) {
                switch (chon) {
                    case "1":
                        UpLoadFile();
                        break;
                    case "2":
                        String outGoingFileName;
                        while ((outGoingFileName = in.readLine()) != null) {
                        	DownLoadFile(outGoingFileName);
                        }
                        break;
                    case "3":
                        System.exit(1);
                        break;
                    default:
                        System.out.println("Khong cô yeu cau nay..!");
                        break;
                }

            }

        } catch (IOException ex) {

        }
    }

    public void UpLoadFile() {
        try {
            int bytesRead;
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String fileName = dis.readUTF();
            OutputStream output = new FileOutputStream(fileName);
            long size = dis.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            output.close();
            dis.close();
            System.out.println("UpLoad File "+fileName+" from Client success.");
        } catch (IOException ex) {
            System.err.println("Error UpLoad File!");
        }
    }
    public void DownLoadFile(String fileName) {
        try {

            File myFile = new File(fileName);
            byte[] n = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(n, 0, n.length);


            OutputStream os = s.getOutputStream();

            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(n.length);
            dos.write(n, 0, n.length);
            dos.flush();
            System.out.println("DownLoad "+fileName+" success..!");
        } catch (Exception e) {
            System.err.println("File not found..!");
        }
    }
}
