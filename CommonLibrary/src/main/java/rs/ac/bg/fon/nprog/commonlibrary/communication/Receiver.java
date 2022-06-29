package rs.ac.bg.fon.nprog.commonlibrary.communication;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author Dragon
 */
public class Receiver {

    private final Socket socket;

    public Receiver(Socket socket) {
        this.socket = socket;
    }

    public Object receive() throws Exception {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return in.readObject();
        } catch (Exception ex) {
            throw new Exception("Error receiving object:" + ex.getMessage());
        }
    }
}
