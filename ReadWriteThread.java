// ReadWriteThread.java

    
import java.io.*;
   
public class ReadWriteThread extends Thread {
       
           
    private BufferedReader input;
       
    private PrintWriter output;
       
            
    public ReadWriteThread(InputStream input, OutputStream output) {
            
        this.input = new BufferedReader(new InputStreamReader(input));
            
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
