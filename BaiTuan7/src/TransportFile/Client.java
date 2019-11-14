package TransportFile;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {

    private static Socket s;
    private static String TenFile;
    private static BufferedReader stdin;
    private static PrintStream os;

    public static void main(String[] args) throws IOException {
        while(true) {
            try {
                s = new Socket("127.0.0.1", 6969);
                stdin = new BufferedReader(new InputStreamReader(System.in));
            } catch (Exception e) {
                System.err.println("Error Connect to Server");
                System.exit(1);
            }

            os = new PrintStream(s.getOutputStream());

            try {
                switch (Integer.parseInt(selectAction())) {
                    case 1:
                        os.println("1");
                        GuiFile();
                        continue;
                    case 2:
                        os.println("2");
                        System.out.print("File Path: ");
                        TenFile = stdin.readLine();
                        os.println(TenFile);
                        TaiFile(TenFile);
                        continue;
                    case 3:
                        s.close();
                        System.exit(1);
                }
            } catch (Exception e) {
                System.err.println("File not Found");
            }

        }

    }

    public static String selectAction() throws IOException {
        System.out.println("1. Gui File");
        System.out.println("2. Tai File");
        System.out.println("3. Thoat");
        System.out.println("Moi ban chon: ");

        return stdin.readLine();
    }


    public static void GuiFile() {
        try {
            System.out.print("File Path: ");
            TenFile = stdin.readLine();

            File f = new File(TenFile);
            byte[] n = new byte[(int) f.length()];
            if(!f.exists()) {
                System.out.println("File khong ton tai!");
                return;
            }

            FileInputStream fis = new FileInputStream(f);
            BufferedInputStream bis = new BufferedInputStream(fis);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(n, 0, n.length);

            OutputStream os = s.getOutputStream();

            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(f.getName());
            dos.writeLong(n.length);
            dos.write(n, 0, n.length);
            dos.flush();
            System.out.println("File "+TenFile+" da gui den Server.");
        } catch (Exception e) {
            System.err.println("Loi: "+e);
        }
    }

    public static void TaiFile(String Ten) {
        try {
            int bytesRead;
            InputStream in = s.getInputStream();

            DataInputStream dis = new DataInputStream(in);

            Ten = dis.readUTF();
            OutputStream output = new FileOutputStream(Ten);
            long size = dis.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }

            output.close();
            in.close();

            System.out.println("Da nhan File "+Ten+" tu Server.");
        } catch (IOException ex) {
            System.out.println("Loi: "+ex);
        }

    }
}

