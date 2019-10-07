import java.io.*;
import java.net.Socket;

public class ReadServer extends Thread{

    private BufferedReader reader;

    public ReadServer(Socket socket) {
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        }catch (IOException e){
            System.out.println("Cannot connect to server, with reading: "+ e.getMessage());
        }
    }

    public void run() {
        while (true){
            try {
                System.out.println(reader.readLine());
            } catch (IOException e) {
                System.out.println("Cannot read from the server: " + e.getMessage());
                break;
            }
        }
    }

}