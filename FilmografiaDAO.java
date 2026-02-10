package DAOs;

import DbManager.DbManager;
import Models.Filmografia;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmografiaDAO {
    private Connection db;
    private static final String INSERT = "INSERT INTO filmografia"
            + "(id, titulo, fecha_estreno, sinopsis, pais_id, clasificacion_id) VALUES (?,?,?,?,?,?)";
    private static final String LISTALL = "SELECT * FROM filmografia";
    private static final String LISTONE = "SELECT * FROM filmografia WHERE id = ?";
    private static final String DELETE = "DELETE FROM filmografia WHERE id = ?";
    private static final String UPDATE = "UPDATE filmografia SET titulo = ?, fecha_estreno= ?, "
            + "sinopsis= ?, pais_id = ?, clasificacion_id= ? WHERE id = ?";

    public FilmografiaDAO(DbManager db) {
        this.db = db.getConnection();
    }
    
    private Filmografia crearFilmografia(ResultSet rs) throws SQLException{
        try{
            return new Filmografia(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getDate("fecha_estreno"),
                rs.getString("sinopsis"),
                rs.getInt("pais_id"),
                rs.getInt("clasificacion_id"));
        }catch (SQLException e){
            throw new SQLException ("Error creando Filmografia", e.getMessage());
        }
    };
    
    
    private void cerrarEstados(PreparedStatement stmt, ResultSet rs)throws SQLException{
        if (stmt != null){ 
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando Statement", e.getMessage());
            }
        }
        
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando ResultSet", e.getMessage());
            }
        }
    };
    

    public void filmografia_listall () throws SQLException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            stmt = db.prepareStatement(LISTALL);
            rs = stmt.executeQuery();
            while(rs.next()){
                System.out.print(crearFilmografia(rs));    
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally { // TEN EN CUENTA QUE EN UN TRY CATCH, EL FINALLY SIEMPRE SE EJECUTA AL FINAL. POR ESO MISMO, QUEREMOS QUE SIEMPRE SE CIERRA EL PREPAREDSTATEMENT Y EL RESULTSET
            cerrarEstados(stmt, rs);
        }
    };
 
    
    public void filmografia_listone (int id) throws SQLException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            stmt = db.prepareStatement(LISTONE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            System.out.print(crearFilmografia(rs));    
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            cerrarEstados(stmt, rs);
        }
    };

    
    public void filmografia_delete (int id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(DELETE);
        ResultSet rs = null;
        
        try{
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            cerrarEstados(stmt, rs);
        }
    };
    
    
    public void filmografia_insert (int id, String titulo, Date fecha_Estreno,
            String sinopsis, int pais_id, int clasificacion_id) throws SQLException{
        PreparedStatement stmt = null;
        ResultSet rs =  null;

        try{
            stmt = db.prepareStatement(INSERT);
            stmt.setInt(1, id);            
            stmt.setString(2, titulo);
            stmt.setDate(3, fecha_Estreno);
            stmt.setString(4, sinopsis);
            stmt.setInt(5, pais_id);
            stmt.setInt(6, clasificacion_id); 
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            cerrarEstados(stmt, rs);
        }
    };
    
    
    public void filmografia_update (int id, String titulo, Date fecha_Estreno,
            String sinopsis, int pais_id, int clasificacion_id) throws SQLException{
        PreparedStatement stmt = null;
        ResultSet rs =  null;
        
        try{
            stmt = db.prepareStatement(UPDATE);
            stmt.setInt(6, id);
            stmt.setString(1, titulo);
            stmt.setDate(2, fecha_Estreno);
            stmt.setString(3, sinopsis);
            stmt.setInt(4, pais_id);
            stmt.setInt(5, clasificacion_id);  
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            cerrarEstados(stmt, rs);
        } 
    };
  
}




       