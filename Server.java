// Server.java

    
import java.net.*;

    
public class Server {
  
    public static void main(String[] args) {
        try {      
            int port = Integer.parseInt(args[0]);           
            ServerSocket serverSocket = new ServerSocket(port);        
            System.err.println("Waiting for a client to connnect");        
            Socket socket = serverSocket.accept();             
            System.err.println("Accepted connection on port " + port);

            new ReadWriteThread(System.in, socket.getOutputStream(), "–-> ").start();   
            new ReadWriteThread(socket.getInputStream(), System.out, "–-> ").start();
                    
        } catch (Exception e) {        
            e.printStackTrace();        
            System.err.println("\nUsage: java Server <port>");                
        }            
    }          
}
