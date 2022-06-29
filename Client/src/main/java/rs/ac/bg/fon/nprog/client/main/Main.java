
package rs.ac.bg.fon.nprog.client.main;

import rs.ac.bg.fon.nprog.client.communication.Communication;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dragon
 */
public class Main {
    public static void main(String[] args) {
        try {
            Communication.getInstance().connect();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
