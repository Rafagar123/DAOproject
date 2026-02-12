package DAOs;

import DbManager.DbManager;
import Models.Acceso;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesoDAO {
    private Connection db;
    private static final String INSERT = "INSERT INTO acceso"
            + "(id_acceso, id_filmografia, id_cuenta, fecha_acceso, tipo_suscripcion_id) VALUES (?,?,?,?,?)";
    private static final String LISTALL = "SELECT * FROM acceso";
    private static final String LISTONE = "SELECT * FROM acceso WHERE id_acceso = ?";
    private static final String DELETE = "DELETE FROM acceso WHERE id_acceso = ?";
    private static final String UPDATE = "UPDATE acceso SET id_filmografia = ?, id_cuenta= ?, "
            + "fecha_acceso= ?, tipo_suscripcion_id= ? WHERE id_acceso = ?";

    public AccesoDAO(DbManager db) {
        this.db = db.getConnection();
    }
    
    public void acceso_listall () throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTALL);
        ResultSet rs = stmt.executeQuery();
        
        try{
            while(rs.next()){
                Acceso acceso = new Acceso
                (rs.getInt("id_acceso"),
                rs.getInt("id_filmografia"),
                rs.getInt("id_cuenta"),
                rs.getDate("fecha_acceso"),
                rs.getInt("tipo_suscripcion_id"));
                System.out.println(acceso.toString());    
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
    
    public void acceso_listone (int id_acceso) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTONE);
        stmt.setInt(1, id_acceso);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        try{
            Acceso acceso = new Acceso
            (rs.getInt("id_acceso"),
            rs.getInt("id_filmografia"),
            rs.getInt("id_cuenta"),
            rs.getDate("fecha_acceso"),
            rs.getInt("tipo_suscripcion_id"));
            System.out.println(acceso.toString());     
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

    
    public void acceso_delete (int id_acceso) throws SQLException{
            PreparedStatement stmt = db.prepareStatement(DELETE);
            stmt.setInt(1, id_acceso);
        try{
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
    
    public void acceso_insert (int id_acceso, int id_filmografia, 
            int id_cuenta, Date fecha_acceso, int tipo_suscripcion_id) throws SQLException{
            PreparedStatement stmt = db.prepareStatement(INSERT);
        try{
            stmt.setInt(1, id_acceso);
            stmt.setInt(2, id_filmografia);
            stmt.setInt(3, id_cuenta);
            stmt.setDate(4, fecha_acceso);
            stmt.setInt(5, tipo_suscripcion_id); 
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
    
    public void acceso_update (int id_acceso, int id_filmografia, 
            int id_cuenta, Date fecha_acceso, int tipo_suscripcion_id) throws SQLException{
            PreparedStatement stmt = db.prepareStatement(UPDATE);
        try{
            stmt.setInt(5, id_acceso);
            stmt.setInt(1, id_filmografia);
            stmt.setInt(2, id_cuenta);
            stmt.setDate(3, fecha_acceso);
            stmt.setInt(4, tipo_suscripcion_id); 
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
