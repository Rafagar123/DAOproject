
package DbManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

 public class DbManager {
    
    private static final Dotenv dotenv = Dotenv.load();
    private static final String USER = dotenv.get("DB_USER");
    private static final String PSS = dotenv.get("DB_PSS");
    private static final String URL = dotenv.get("DB_URL");
    private Connection conexion = null;
       
    public void conectar() {
        try {
            conexion = DriverManager.getConnection(URL,USER,PSS);
            System.out.println("Conexion exitosa"); 
        } catch (SQLException e) {
            System.out.println(e.getMessage()+ "Error al conectar"); 
        }
    }
    
    
    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()){
                conexion.close();
                System.out.println("Desconexion exitosa"); 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()+ "Error al conectar"); 
        }
    } 

    public Connection getConnection() {
        return conexion; 
    }
   
}