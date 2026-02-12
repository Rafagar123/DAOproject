
package DbManager;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "C:\\Users\\thepi\\Documents\\log\\log.txt";
    
    
    public static void info(String msg){
        log("INFO",msg);
    }
    
    public static void error(String msg){
        log("ERROR",msg);
    }
    
   public static String timestamp(){
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); 
    }
   
   private static void log(String level, String msg){
       String logEntry = String.format("[%s] %s - %s%n", level, timestamp(),msg);
        try(FileWriter fw = new FileWriter(LOG_FILE, true)){
           fw.write(logEntry);
        }catch(IOException e){
           System.err.println("No se pudo escribir en el log" + e.getMessage());
        }
   }
       
}
