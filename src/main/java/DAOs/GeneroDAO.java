package DAOs;

import DbManager.DbManager;
import Models.Genero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneroDAO {
    private Connection db;
    private static final String INSERT = "INSERT INTO genero (id, nombre) VALUES (?,?)";
    private static final String LISTALL = "SELECT * FROM genero";
    private static final String LISTONE = "SELECT * FROM genero WHERE id = ?";
    private static final String DELETE = "DELETE FROM genero WHERE id = ?";
    private static final String UPDATE = "UPDATE genero SET nombre = ? WHERE id = ?";

    public GeneroDAO(DbManager db) {
        this.db = db.getConnection();
    }
    
    public void Genero_listall () throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTALL);
        ResultSet rs = stmt.executeQuery();
        
        try{
            while(rs.next()){
                Genero genero = new Genero
                (rs.getInt("id"),
                rs.getString("nombre"));
                System.out.println(genero.toString());    
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
    
    public void genero_listone (int id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTONE);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        try{
            Genero genero = new Genero
            (rs.getInt("id"),
            rs.getString("nombre"));
            System.out.println(genero.toString());     
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

    
    public void genero_delete (int id) throws SQLException{
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
    
    public void genero_insert (int id, String nombre) throws SQLException{
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
    
    public void genero_update (int id, String nombre) throws SQLException{
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