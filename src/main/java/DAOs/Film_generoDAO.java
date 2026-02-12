package DAOs;

import DbManager.DbManager;
import Models.Film_genero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Film_generoDAO {
    private Connection db;
    private static final String INSERT = "INSERT INTO film_genero (film_id, genero_id) VALUES (?,?)";
    private static final String LISTALL = "SELECT * FROM film_genero";
    private static final String LISTONE = "SELECT * FROM film_genero WHERE film_id = ?";
    private static final String DELETE = "DELETE FROM film_genero WHERE film_id = ?";
    private static final String UPDATE = "UPDATE film_genero SET genero_id = ? WHERE film_id = ?";

    public Film_generoDAO(DbManager db) {
        this.db = db.getConnection();
    }
    
    public void film_genero_listall () throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTALL);
        ResultSet rs = stmt.executeQuery();
        
        try{
            while(rs.next()){
                Film_genero film_genero = new Film_genero
                (rs.getInt("film_id"),
                rs.getInt("genero_id"));
                System.out.println(film_genero.toString());    
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
    
    public void film_genero_listone (int film_id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTONE);
        stmt.setInt(1, film_id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        try{
            Film_genero film_genero = new Film_genero
            (rs.getInt("film_id"),
            rs.getInt("genero_id"));
            System.out.println(film_genero.toString());    
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

    
    public void film_genero_delete (int film_id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(DELETE);
        try{
            stmt.setInt(1, film_id);
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
    
    public void film_genero_insert (int film_id, int genero_id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(INSERT);
        try{
            stmt.setInt(1, film_id);
            stmt.setInt(2, genero_id); 
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
    
    public void film_genero_update (int film_id, int genero_id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(UPDATE);
        try{
            stmt.setInt(2, film_id);
            stmt.setInt(1, genero_id); 
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
