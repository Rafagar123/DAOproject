package DAOs;

import DbManager.DbManager;
import Models.Tipo_suscripcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tipo_suscripcionDAO {
    private Connection db;
    private static final String INSERT = "INSERT INTO tipo_suscripcion"
            + "(tipo_id, nombre, precio, duracion_meses) VALUES (?,?,?,?)";
    private static final String LISTALL = "SELECT * FROM tipo_suscripcion";
    private static final String LISTONE = "SELECT * FROM tipo_suscripcion WHERE tipo_id = ?";
    private static final String DELETE = "DELETE FROM tipo_suscripcion WHERE tipo_id = ?";
    private static final String UPDATE = "UPDATE tipo_suscripcion SET nombre = ?, precio = ?, "
            + "duracion_meses = ? WHERE tipo_id = ?";

    public Tipo_suscripcionDAO(DbManager db) {
        this.db = db.getConnection();
    }
    
    public void tipo_suscripcion_listall () throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTALL);
        ResultSet rs = stmt.executeQuery();
        
        try{
            while(rs.next()){
                Tipo_suscripcion tipo_suscripcion = new Tipo_suscripcion
                (rs.getInt("tipo_id"),
                rs.getString("nombre"),
                rs.getFloat("precio"),
                rs.getInt("duracion_meses"));
                System.out.println(tipo_suscripcion.toString());    
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
    
    public void tipo_suscripcion_listone (int tipo_id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTONE);
        stmt.setInt(1, tipo_id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        try{
            Tipo_suscripcion tipo_suscripcion = new Tipo_suscripcion
            (rs.getInt("tipo_id"),
            rs.getString("nombre"),
            rs.getFloat("precio"),
            rs.getInt("duracion_meses"));
            System.out.println(tipo_suscripcion.toString());      
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

    
    public void tipo_suscripcion_delete (int tipo_id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(DELETE);
        try{
            stmt.setInt(1, tipo_id);
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
    
    public void tipo_suscripcion_insert (int tipo_id, String nombre, float precio, 
            int duracion_meses) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(INSERT);
        try{
            stmt.setInt(1, tipo_id);
            stmt.setString(2, nombre);
            stmt.setFloat(3, precio);
            stmt.setInt(4, duracion_meses);
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
    
    public void tipo_suscripcion_update (int tipo_id, String nombre, float precio, 
            int duracion_meses) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(UPDATE);
        try{
            stmt.setInt(4, tipo_id);
            stmt.setString(1, nombre);
            stmt.setFloat(2, precio);
            stmt.setInt(3, duracion_meses);
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
