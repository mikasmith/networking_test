// ReadWriteThread.java

    
import java.io.*;
   
public class ReadWriteThread extends Thread {  
           
    private BufferedReader input;    
    private PrintWriter output;
    /*Change the RWT so that it takes a string prefix as a 3rd parameter which will be added to every line which is output by the RWT. */ 
    public ReadWriteThread(InputStream input, OutputStream output, String prefix) {      
        this.input = new BufferedReader(new InputStreamReader(prefix+input));      
        this.output = new PrintWriter(output, true);
            
    }
                
    public void run() {    
        try {            
            String line;            
            while ((line = input.readLine()) != null) {                      
                output.println(line);           
            }            
        } catch (IOException e) {        
            e.printStackTrace();              
        }      
    }
}
