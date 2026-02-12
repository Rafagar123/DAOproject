package DAOs;
import DbManager.DbManager;
import Models.Filmografia;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DbManager.Logger;
import java.util.List;


public abstract class DAO<T>{
    protected Connection db;
    protected static Logger logger = new Logger();
    
    protected void cerrarEstados(PreparedStatement stmt, ResultSet rs)throws SQLException{
        if (stmt != null){ 
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.error("Error cerrando Statement");
                throw new SQLException("Error cerrando Statement", e.getMessage());
            }
        }

        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Error cerrando ResultSet");
                throw new SQLException("Error cerrando ResultSet", e.getMessage());
            }
        }
    };
    protected void hacerRollback (Connection db)throws SQLException{
        try{
            db.rollback();
        } catch (SQLException e) {
                logger.error("Error haciendo rollback");
                throw new SQLException("Error haciendo rollback", e.getMessage());
            }
    };  
    protected abstract void cargarDatos(String met, PreparedStatement stmt, T obj)throws SQLException;   
    protected abstract T crear(ResultSet rs)throws SQLException;
     
     
    public abstract T listOne(int id)throws SQLException;
    public abstract List<T> listAll()throws SQLException;
    public abstract void insert(T obj)throws SQLException;
    public abstract void update(T obj)throws SQLException;
    public abstract void delete(int id) throws SQLException;

 
}
