import java.io.IOException;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String [] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }

    private int serverPort = 12000;
    private HashMap<String, String> users = new HashMap<>();
    private Set<MultiUser> userThreads = new HashSet<>();

    public void startServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server is running on port: " + serverPort);

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("New client is connected.");
                MultiUser newUser = new MultiUser(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException e){
            System.out.println("Server Error");
        }
    }

    public void broadcast(String message, MultiUser currentUser){
        for (MultiUser user: userThreads) {
            if(user != currentUser){
                user.sendMessage(message);
            }
        }
    }

    public void addUser(String username, InetAddress inetAddress){
        System.out.println(inetAddress);
        users.put(username, inetAddress.toString());
    }

    public void removeUser(String username, InetAddress inetAddress, MultiUser currentUser){
        boolean removed = users.remove(username, inetAddress.toString() );
        if (removed){
            userThreads.remove(currentUser);
            System.out.println(username + ": has exited the chat.");
        }
    }

    public HashMap<String, String> getUsers() {
        return users;
    }
}
