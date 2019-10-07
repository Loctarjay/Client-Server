import java.io.*;
import java.net.*;

public class Client {

    private String serverName;
    private int serverPort;

    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public static void main(String[] args) {
        Client client = new Client("Localhost", 12000);
        client.startClient();
    }

    public void startClient() {
        try {
            Socket socket = new Socket(serverName, serverPort);
            WriteServer writeServ = new WriteServer(socket,this);
            writeServ.start();
            if (writeServ.isAlive()){
                new ReadServer(socket).start();
            }
            System.out.println("Connected to the chat server");

        } catch (IOException ex) {
            System.out.println("Cannot connect to the server: " + ex.getMessage());
        }
    }
}