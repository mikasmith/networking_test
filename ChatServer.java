// ChatServer.java

    
import java.io.*;
import java.net.*;
import java.util.*;

    
public class ChatServer {
    private static List<ClientHandler> clients = new LinkedList<ClientHandler>();
    //Creates a linked list of ClientHandlers to keep track of the clients.//
        
            
    public static void main(String[] args) {
        try{
            new ChatServer().startServer(Integer.parseInt(args[0])); //Passes the port number as the command line argument to the startserver method to create a new ChatServer
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("\nUsage: java ChatServer <port>");            
        }         
    }
        
           
    public void startServer(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);//create new ServerSocket instance and assign it to serverSocket
        System.err.println("ChatServer started");
        while (true) { //infinite while loop 
            ClientHandler ch = new ClientHandler(serverSocket.accept()); //waits until a client connects to the socket , accept returns a Socket instance when a client connects. Instance is passed to the ClientHandler constructor and assigned to Ch. 
            System.err.println("Accepted connection from " + ch); //gives clients identification
            synchronized (clients) {
                clients.add(ch); //Add new client to the list 
            }
            ch.start(); //Starts the new ClientHandler thread executing        
        }       
    }
        
    //line is the string to be sent, sender is the client which sent line to the server//        
    public static void sendAll(String line, ClientHandler sender) {
        System.err.println("Sending ’" + line + "’ to : " + clients); //status message
        synchronized(clients){ //Exclusive access to clients 
            for (ClientHandler cl : clients) { 
                cl.send(sender + ": " + line); //sends the message to each client by callind send() method of ClientHandler once for each client.                
            }         
        }   
    }
        
    //there is an instance of this class for each client that connects to the server.//        
    public static class ClientHandler extends Thread {
        private BufferedReader input;
        private PrintWriter output;
        String id;
        private static int count = 0;

        //Class constructor which is passed a Socket instance 
        public ClientHandler(Socket socket) throws Exception {
            //get an InputStream on the socket, wrap a InputStreamReader around it, wrap a BufferedReader around that, and assign it all to input. 
            input = new BufferedReader(
                                                   
                                       new InputStreamReader(socket.getInputStream()));

            //Gets an outputstream on the socket, wraps a Printwriter around it and assigns the result to output. 
            output = new PrintWriter(socket.getOutputStream(), true);
            id = "client_" + ++count; //creates an id string so the client can be identified         
        }
                
                    
        public void send(String line) {
            output.println(line);      
        }
                
                    
        public String toString() {
            return id;     
        }
                

        //Called whenever a ClientHandler thread is started
        public void run() {

            //Executed as the thread terminates. ctrl-C in client window. 
            try{
                send("Welcome! You are " + this + "."); //Prints identity in clients window
                String line;

                //Repeatedly reads a line from input and calls the sendAll() method of ChatServer. The input is the socket of the thread. The result is that whatever the connected client sends is printed on the screen of all clients
                while ((line = input.readLine()) != null) {
                    sendAll(line, this);
                }
                            
            }catch (IOException e) {
                e.printStackTrace();
                            
            }finally {
                            
                synchronized (clients) {
                    clients.remove(this); //Remove terminated client from clients list, using exclusive access to clients 
                                    
                }
                            
                System.err.println(this + " closed connection!"); //prints when client closed 
                            
            }
                    
        }
                
    }

}
