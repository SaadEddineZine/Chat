package src.com.chatapp.client;
import java.net.*;
import java.util.*;
import java.io.*;

public class ChatClient {
    // Represents the socket connection to the chat server
    private Socket socket = null;

    // Allows for reading input from the console
    private BufferedReader inputConsole = null;

    // Allows for writing output to the socket connection
    private PrintWriter out = null;

    // Allows for reading input from the socket connection
    private BufferedReader in = null;

    public ChatClient(String address, int port) {
        try{

            // Create a new socket connection to the chat server
            socket = new Socket(address, port);
            System.out.println("Connected to the chat server");
            
            // Initialize input and output streams for the chat client
            inputConsole = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";
            
            // Start the chat session
            while(!line.equals("exit")){
                // Read input from the user
                line = inputConsole.readLine();
                // Send the message to the server
                out.println(line);
                System.out.println(in.readLine());
            }
            socket.close();
            inputConsole.close();
            out.close();

        } catch (UnknownHostException u) {
            System.out.println("Host unknown: " + u.getMessage());
        } catch (IOException i) {
            System.out.println("Unexpected exception: " + i.getMessage()); 
        }


    }
    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 5000);
    }
}
