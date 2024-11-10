package src.com.chatapp.server;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static List<ClientHandler> clients = new ArrayList<>();
   
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException{
        ServerSocket serversocket = new ServerSocket(5000); //serversocket is meant to listen to client connections
        System.out.println("Server started. Listening for incoming connections...");
        while(true){
            Socket clientsocket = serversocket.accept(); //we accept the client connection to the server
            System.out.println("Client connected: " + clientsocket);
            ClientHandler clientThread = new ClientHandler(clientsocket, clients); //we create a thread fpr handling
            clients.add(clientThread); //we add the client to the list
            new Thread(clientThread).start();
        }
    }
    
}

class ClientHandler implements Runnable { //The Runnable interface should be implemented by any class whose instances are intended to be executed by a thread. The class must define a method of no arguments called run.
    private Socket clientSocket;
    private List<ClientHandler> clients;
    private PrintWriter out;
    private BufferedReader in;
  
    public ClientHandler(Socket socket, List<ClientHandler> clients) throws IOException {
        this.clientSocket = socket;
        this.clients = clients;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
  
    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Broadcast message to all clients
                for (ClientHandler aClient : clients) {
                    aClient.out.println(inputLine);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
  }