package server.oneWayClient;

import gui.oneWayClient.main;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack
 */
public class Client {
    
    private PrintWriter output = null;
    private InputStream input = null;
    
    public static List<Client> clients = new ArrayList();
    
    /**
     * Creates a new client and attempts to connect.
     * @param hostName The URL or IP of the server.
     * @param portNumber The port to connect to.
     */
    public Client(String hostName, int portNumber) {
        connect(hostName, portNumber);
        String out = receive();
        while (!out.equals("@end")) {
            out = receive();
            if (out == null) {
                out = "@end";
            }
        }
        clients.add(this);
    }
    
    /**
     * Connects to a server.
     * @param hostName The URL or IP of the server.
     * @param portNumber The port to connect to.
     */
    public void connect(String hostName, int portNumber) {
        try {
            Socket echoSocket = new Socket(hostName, portNumber);
            output = new PrintWriter(echoSocket.getOutputStream(), true);
            input = echoSocket.getInputStream();
        } catch (Exception e) {
            System.err.println("Failed to connect to server: " + hostName + ":" + portNumber);
        }
    }
    
    /**
     * Sends data to the server.
     * @param o The data to send.
     */
    public void send(Object o) {
        output.println(o);
        output.flush();
    }
    
    /**
     * Waits for the server to send data and receives it.
     * @return The received data.
     */
    public String receive() {
        try {
            byte[] buffer = new byte[8];
            int read;
            String out = "";
            read = input.read(buffer);
            send('y');
            int bytes = Integer.parseInt(new String(buffer, 0, read));
            buffer = new byte[bytes];
            read = input.read(buffer);
            out = new String(buffer, 0, read);
            System.out.println("Server: " + out);
            send('y');
            return out;
        } catch (Exception e) {
            System.err.println("Read error in recieve() function");
            System.err.println(e.getMessage());
            return null;
        }
    }
    
}