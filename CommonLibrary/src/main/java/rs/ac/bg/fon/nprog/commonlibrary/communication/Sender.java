package rs.ac.bg.fon.nprog.commonlibrary.communication;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Dragon
 */
public class Sender {

    private final Socket socket;

    public Sender(Socket socket) {
        this.socket = socket;
    }

    public void send(Object object) throws Exception {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.writeObject(object);
            out.flush();
        } catch (IOException ex) {
            throw new Exception("Error sending object: " + ex.getMessage());
        }
    }
}
