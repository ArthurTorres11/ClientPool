// Arthur Torres - RA 74010

package server;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 12345;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        System.out.println("Server started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            executorService.execute(new ClientHandler(clientSocket));
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            out.writeUTF(getSystemInfo());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSystemInfo() {
        String os = System.getProperty("os.name");
        String processor = System.getenv("PROCESSOR_IDENTIFIER");
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ip = "Unknown";
            e.printStackTrace();
        }
        File[] roots = File.listRoots();
        StringBuilder storageInfo = new StringBuilder();
        for (File root : roots) {
            storageInfo.append(root.getAbsolutePath()).append(": ")
                    .append(root.getUsableSpace() / 1024 / 1024).append(" MB free of ")
                    .append(root.getTotalSpace() / 1024 / 1024).append(" MB total\n");
        }
        return "S.O: " + os + "\nProcessador: " + processor + "\nIP: " + ip + "\nArmazenamento:\n" + storageInfo;
    }
}
