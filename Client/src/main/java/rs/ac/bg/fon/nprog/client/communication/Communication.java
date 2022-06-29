package rs.ac.bg.fon.nprog.client.communication;

import rs.ac.bg.fon.nprog.commonlibrary.communication.Receiver;
import rs.ac.bg.fon.nprog.commonlibrary.communication.Request;
import rs.ac.bg.fon.nprog.commonlibrary.communication.Response;
import rs.ac.bg.fon.nprog.commonlibrary.communication.Sender;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Dragon
 */
public class Communication {

    private static Communication instance;
    private Socket socket;
    private Sender sender;
    private Receiver receiver;
    private boolean connected = false;

    private Employee authenticatedEmployee;

    private static final String SERVER_ADRESS = "127.0.0.1";
    private static final int SERVER_PORT = 9000;

    private Communication() {
    }

    public static Communication getInstance() {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public void connect() throws IOException {
        socket = new Socket(SERVER_ADRESS, SERVER_PORT);
        System.out.println("Client is connected!");
        sender = new Sender(socket);
        receiver = new Receiver(socket);
        connected = true;

        Coordinator.getInstance().openLoginForm();
    }

    public Response sendRequest(Request request) throws Exception {
        if (!connected) {
            throw new Exception("Request cannot be send before a connection has been made!");
        }
        sender.send(request);
        System.out.println("Request has been sent...");
        return (Response) receiver.receive();
    }

    public void setAuthenticatedEmployee(Employee authenticatedEmployee) {
        this.authenticatedEmployee = authenticatedEmployee;
    }

    public Employee getAuthenticatedEmployee() {
        return authenticatedEmployee;
    }
}
