package rs.ac.bg.fon.nprog.server.thread;

import rs.ac.bg.fon.nprog.server.properties.util.UtilApplicationProperties;
import rs.ac.bg.fon.nprog.server.thread.coordinator.ThreadCoordinator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dragon
 */
public class ServerThread extends Thread {

    private final ServerSocket serverSocket;

    public ServerThread() throws IOException {//TODO: Make sure that it is handled. Some JOptionPane...
        int serverPort = UtilApplicationProperties.getInstance().getServerPort();

        this.serverSocket = new ServerSocket(serverPort);
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                System.out.println("Waiting for a client...");
                Socket socket = serverSocket.accept();
                ClientHandlerThread thread = new ClientHandlerThread(socket);
                thread.start();
                ThreadCoordinator.getInstance().addClientHandlerThread(thread);
                System.out.println("Client connected!");
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ThreadCoordinator.getInstance().stopAllClientHandlerThreads();
    }

    public void stopThread() throws IOException {
        if (serverSocket != null && serverSocket.isBound()) {//TODO: Check this out! Should i call stopAll() here or leave it as it is? Read some more docs about isBound()
            serverSocket.close();
        }
    }
}
