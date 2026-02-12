package DAOs;

import DbManager.DbManager;
import Models.Reparto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepartoDAO {
    private Connection db;
    private static final String INSERT = "INSERT INTO reparto"
            + "(id_reparto, id_filmografia, nombre_actor, papel) VALUES (?,?,?,?)";
    private static final String LISTALL = "SELECT * FROM reparto";
    private static final String LISTONE = "SELECT * FROM reparto WHERE id_reparto = ?";
    private static final String DELETE = "DELETE FROM reparto WHERE id_reparto = ?";
    private static final String UPDATE = "UPDATE reparto SET id_filmografia = ?, "
            + "nombre_actor = ?, papel = ? WHERE id_reparto = ?";

    public RepartoDAO(DbManager db) {
        this.db = db.getConnection();
    }
    
    public void reparto_listall () throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTALL);
        ResultSet rs = stmt.executeQuery();
        
        try{
            while(rs.next()){
                Reparto reparto = new Reparto
                (rs.getInt("id_reparto"),
                rs.getInt("id_filmografia"),
                rs.getString("nombre_actor"),
                rs.getString("papel"));
                System.out.println(reparto.toString());    
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
    
    public void reparto_listone (int id_reparto) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTONE);
        stmt.setInt(1, id_reparto);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        try{
            Reparto reparto = new Reparto
            (rs.getInt("id_reparto"),
            rs.getInt("id_filmografia"),
            rs.getString("nombre_actor"),
            rs.getString("papel"));
            System.out.println(reparto.toString());     
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

    
    public void reparto_delete (int id_reparto) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(DELETE);
        try{
            stmt.setInt(1, id_reparto);
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
    
    public void reparto_insert (int id_reparto, int id_filmografia, 
            String nombre_actor, String papel) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(INSERT);
        try{
            stmt.setInt(1, id_reparto);
            stmt.setInt(2, id_filmografia);
            stmt.setString(3, nombre_actor);
            stmt.setString(4, papel);
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
    
    public void reparto_update (int id_reparto, int id_filmografia, 
            String nombre_actor, String papel) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(UPDATE);
        try{
            stmt.setInt(4, id_reparto);
            stmt.setInt(1, id_filmografia);
            stmt.setString(2, nombre_actor);
            stmt.setString(3, papel);
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
