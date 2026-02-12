package DAOs;

import DbManager.DbManager;
import Models.Pais;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaisDAO {
    private Connection db;
    private static final String INSERT = "INSERT INTO pais (id, nombre) VALUES (?,?)";
    private static final String LISTALL = "SELECT * FROM pais";
    private static final String LISTONE = "SELECT * FROM pais WHERE id = ?";
    private static final String DELETE = "DELETE FROM pais WHERE id = ?";
    private static final String UPDATE = "UPDATE pais SET nombre = ? WHERE id = ?";

    public PaisDAO(DbManager db) {
        this.db = db.getConnection();
    }
    
    public void Pais_listall () throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTALL);
        ResultSet rs = stmt.executeQuery();
        
        try{
            while(rs.next()){
                Pais pais = new Pais
                (rs.getInt("id"),
                rs.getString("nombre"));
                System.out.println(pais.toString());    
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
    
    public void pais_listone (int id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTONE);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        try{
            Pais pais = new Pais
            (rs.getInt("id"),
            rs.getString("nombre"));
            System.out.println(pais.toString());     
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

    
    public void pais_delete (int id) throws SQLException{
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
    
    public void pais_insert (int id, String nombre) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(INSERT);
        try{
            stmt.setInt(1, id);
            stmt.setString(2, nombre); 
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
    
    public void pais_update (int id, String nombre) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(UPDATE);
        try{
            stmt.setInt(2, id);
            stmt.setString(1, nombre); 
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