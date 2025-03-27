// Arthur Torres - RA 74010

import java.net.*;
import java.io.*;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String systemInfo = in.readUTF();
            System.out.println("Informações do sistema:\n" + systemInfo);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
