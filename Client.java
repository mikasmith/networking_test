import java.net.*;

public class Client{


    public static void main(String[] args) {         
        Socket socket = null; 

        if(args.length >1){
            try {                 
                int port = Integer.parseInt(args[0]);

                socket = new Socket(args[1], port);
                System.err.println("Connected to " + args[1] + " on port " + port);               
                new ReadWriteThread(System.in, socket.getOutputStream(), "–-> ").start();
                new ReadWriteThread(socket.getInputStream(), System.out, "–-> ").start();         
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("\nClient Usage: java TCPExample <port> [host]");
                    
            }
        }
    }

}
