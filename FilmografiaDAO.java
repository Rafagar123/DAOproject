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
    
     private void cargarDatos(String met, PreparedStatement stmt, Filmografia film) throws SQLException{
        try{
            if (met == "insert"){
                stmt.setInt(1, film.getId());            
                stmt.setString(2, film.getTitulo());
                stmt.setDate(3, film.getFecha_estreno());
                stmt.setString(4, film.getSinopsis());
                stmt.setInt(5, film.getPais_id());
                stmt.setInt(6, film.getClasificacion_id());
            } else if (met == "update"){
                stmt.setInt(6, film.getId());            
                stmt.setString(1, film.getTitulo());
                stmt.setDate(2, film.getFecha_estreno());
                stmt.setString(3, film.getSinopsis());
                stmt.setInt(4, film.getPais_id());
                stmt.setInt(5, film.getClasificacion_id());
            }
        }catch (SQLException e){
            throw new SQLException ("Error cargando los datos", e.getMessage());
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
            throw new SQLException("Error listando elementos", e.getMessage());
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
            throw new SQLException("Error listando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, rs);
        }
    };

    
    public void filmografia_delete (int id) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(DELETE);
        
        try{
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("Error borrando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, null);
        }
    };
    
   public void filmo_insert (Filmografia film) throws SQLException{
        PreparedStatement stmt = null;

        try{
            stmt = db.prepareStatement(INSERT);
            cargarDatos("insert", stmt, film);
            stmt.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("Error insertando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, null);
        }
    };
    
    public void filmografia_update (Filmografia film) throws SQLException{
        PreparedStatement stmt = null;
        
        try{
            stmt = db.prepareStatement(INSERT);
            cargarDatos("update", stmt, film);
            stmt.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("Error actualizando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, null);
        } 
    };
  
}




       