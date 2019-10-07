import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class WriteServer extends Thread {

    private PrintWriter writer;
    private Socket socket;
    private Client client;

    public WriteServer(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        }catch (IOException e){
            System.out.println("Cannot connect to server, with writing: " + e.getMessage());
        }
    }

    public void run() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter a username:");
        String userName = scan.nextLine();
        writer.println(userName);

        String text;
        System.out.println("Welcome: " + userName);
        do {
            text = scan.nextLine();

            if (text.equalsIgnoreCase("exit")){
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Cannot write to the server: " + e.getMessage());
                }
            }else {
                writer.println(text);
            }
        }while (!socket.isClosed());
    }
}