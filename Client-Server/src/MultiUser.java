import java.io.*;
import java.net.Socket;

public class MultiUser extends Thread {

    private Socket socket;
    private Server server;
    private PrintWriter writer;

    public MultiUser(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            String userName = reader.readLine();
            server.addUser(userName, socket.getInetAddress());
            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);
            String clientMessage;

            do {
                clientMessage = reader.readLine();
                if (!clientMessage.equalsIgnoreCase("List") && !clientMessage.equalsIgnoreCase("exit")) {
                    serverMessage = userName + " -->> " + clientMessage;
                    server.broadcast(serverMessage, this);
                } else {
                    printUsers();
                }
            } while (!clientMessage.equalsIgnoreCase("exit"));

            serverMessage = userName + ": has exited the chat.";
            server.broadcast(serverMessage, this);
            server.removeUser(userName, socket.getInetAddress(), this);
            socket.close();

        } catch (IOException ex) {
            System.out.println("Error in MultiUser: " + ex.getMessage());
        }
    }

    void printUsers() {
        if (!server.getUsers().isEmpty()) {
            writer.println("Connected users: " + server.getUsers().keySet());
        } else {
            writer.println("No other users connected");
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }
}
