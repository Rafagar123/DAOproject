package DAOs;

import DbManager.DbManager;
import Models.Suscripcion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuscripcionDAO {
    private Connection db;
    private static final String INSERT = "INSERT INTO suscripcion (id, cuenta_id, "
            + "tipo_id, fecha_contratacion, fecha_fin) VALUES (?,?,?,?,?)";
    private static final String LISTALL = "SELECT * FROM suscripcion";
    private static final String LISTONE = "SELECT * FROM suscripcion WHERE id = ?";
    private static final String DELETE = "DELETE FROM suscripcion WHERE id= ?";
    private static final String UPDATE = "UPDATE suscripcion SET cuenta_id = ?, "
            + "tipo_id = ?, fecha_contratacion = ?, fecha_fin = ? WHERE id = ?";

    public SuscripcionDAO(DbManager db) {
        this.db = db.getConnection();
    }
    
    public void suscripcion_listall () throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTALL);
        ResultSet rs = stmt.executeQuery();
        
        try{
            while(rs.next()){
                Suscripcion suscripcion = new Suscripcion
                (rs.getInt("id"),
                rs.getInt("cuenta_id"),
                rs.getInt("tipo_id"),
                rs.getDate("fecha_contratacion"),
                rs.getDate("fecha_fin"));
                System.out.println(suscripcion.toString());    
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
            
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
        }
    };
    
    public void suscripcion_listone (int id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTONE);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        try{
            Suscripcion suscripcion = new Suscripcion
            (rs.getInt("id"),
            rs.getInt("cuenta_id"),
            rs.getInt("tipo_id"),
            rs.getDate("fecha_contratacion"),
            rs.getDate("fecha_fin"));
            System.out.println(suscripcion.toString());   
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
            
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
        }
    };

    
    public void suscripcion_delete (int id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(DELETE);
        try{
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
        }
    };
    
    public void suscripcion_insert (int id, int cuenta_id, int tipo_id, 
            Date fecha_contratacion, Date fecha_fin) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(INSERT);
        try{
            stmt.setInt(1, id);
            stmt.setInt(2, cuenta_id);
            stmt.setInt(3, tipo_id);
            stmt.setDate(4, fecha_contratacion);
            stmt.setDate(5, fecha_fin); 
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
        }
    };
    
    public void suscripcion_update (int id, int cuenta_id, int tipo_id, 
            Date fecha_contratacion, Date fecha_fin) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(UPDATE);
        try{
            stmt.setInt(5, id);
            stmt.setInt(1, cuenta_id);
            stmt.setInt(2, tipo_id);
            stmt.setDate(3, fecha_contratacion);
            stmt.setDate(4, fecha_fin); 
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
        }
    };
  
}
