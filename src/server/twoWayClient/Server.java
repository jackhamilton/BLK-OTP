package server.twoWayClient;

import gui.twoWayClient.gui;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The Server is a package that connects to Clients and transfers data.
 * @author Jack
 * @version 1.4 Alpha
 */
public class Server implements Runnable{
    
    /**
     * List of the currently connected clients.
     */
    public List<Socket> clients = new ArrayList<Socket>();
    /**
     * The current ServerSocket.
     */
    public ServerSocket serverSocket = null;
    private Thread listenThread = new Thread(this);
    private boolean running = false;
    
    /**
     * Creates a new Server and initializes a ServerSocket.
     * @param port The port to listen on.
     */
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);            
        } catch (Exception e ){
            System.out.println("Failed to connect.");
        }
    }
    
    /**
     * Sends data to a client.
     * @param clientIndex The client in the clients array to send data to.
     * @param o The data to send.
     */
    public void send(int clientIndex, Object o) {
        try {
            PrintWriter out = new PrintWriter(clients.get(clientIndex).getOutputStream(), true);
            out.println(o);
        } catch (Exception e){}
    }
    
    /**
     * Receives data from a client.
     * @param clientIndex The client in the clients array to receive data from.
     * @return The data received.
     */
    public String receive(int clientIndex) {
        try {
            InputStreamReader is = new InputStreamReader(clients.get(clientIndex).getInputStream());
            BufferedReader br = new BufferedReader(is);
            String read = br.readLine();
            return read;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    /**
     * Start listening for connections.
     */
    public void start() {
        running = true;
        listenThread.start();
    }
    
    /**
     * Stop listening for connections.
     */
    public void stop() {
        running = false;
    }
    
    public void onConnect() {
        send(clients.size() - 1, gui.name);
        String name = receive(clients.size() - 1);
        boolean doesExist = false;
        for(int x = 0; x < gui.model.getSize(); x++) {
            if (name.equals(gui.model.getElementAt(x))) {
                doesExist = true;
            }
        }
        if (!name.equals("") && !doesExist) {
            gui.model.addElement(name);
        } else if (name.equals("")){
            gui.model.addElement(clients.get(clients.size() - 1).getLocalSocketAddress().toString());
        } else if (doesExist) {
            int y = 0;
            while (doesExist) {
                doesExist = false;
                for(int x = 0; x < gui.model.getSize(); x++) {
                    if ((name + y).equals(gui.model.getElementAt(x))) {
                        doesExist = true;
                    }
                }
                if (doesExist) {
                    y++;
                }
            }
            gui.model.addElement(name + y);
        }
    }
    
    /**
     * @deprecated Use {@link start()} instead.
     */
    public void run() {
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                onConnect();
            } catch (Exception e) {}
        }
    }
}