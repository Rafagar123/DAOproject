package DAOs;

import DbManager.DbManager;
import Models.Filmografia;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DbManager.Logger;
import java.util.ArrayList;
import java.util.List;

public class FilmoDAO extends DAO<Filmografia>{
    private Connection db;
    private static final String INSERT = "INSERT INTO filmografia"
            + "(id, titulo, fecha_estreno, sinopsis, pais_id, clasificacion_id) VALUES (?,?,?,?,?,?)";
    private static final String LISTALL = "SELECT * FROM filmografia";
    private static final String LISTONE = "SELECT * FROM filmografia WHERE id = ?";
    private static final String DELETE = "DELETE FROM filmografia WHERE id = ?";
    private static final String UPDATE = "UPDATE filmografia SET titulo = ?, fecha_estreno= ?, "
            + "sinopsis= ?, pais_id = ?, clasificacion_id= ? WHERE id = ?";

    public FilmoDAO(DbManager db) {
        this.db = db.getConnection();
        logger.info("Se ha establecido la conexión");
    }
    @Override
    protected void cargarDatos(String met, PreparedStatement stmt, Filmografia film) throws SQLException {
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
            logger.error("Error cargando los datos");
            throw new SQLException ("Error cargando los datos", e.getMessage());
        }    
    };

    @Override
    protected Filmografia crear(ResultSet rs) throws SQLException {
        try{
            return new Filmografia(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getDate("fecha_estreno"),
                rs.getString("sinopsis"),
                rs.getInt("pais_id"),
                rs.getInt("clasificacion_id"));
        }catch (SQLException e){
            logger.error("Error creando elemento");
            throw new SQLException ("Error creando elemento", e.getMessage());
        }    
    };

    @Override
    public Filmografia listOne(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            stmt = db.prepareStatement(LISTONE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            db.commit();
            System.out.print(crear(rs));
            logger.info("Se ha hecho un ListOne");
            return crear(rs);
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error listando elemento");
            throw new SQLException("Error listando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, rs);
        }
    };

    @Override
    public List<Filmografia> listAll() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List = new ArrayList<Filmografia>();
        
        try{
            stmt = db.prepareStatement(LISTALL);
            rs = stmt.executeQuery();
            db.commit();
            while(rs.next()){
                System.out.print(crear(rs));    
            }
            logger.info("Se ha hecho un ListAll");
            return 
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error listando elementos");
            throw new SQLException("Error listando elementos", e.getMessage());
        }finally { // TEN EN CUENTA QUE EN UN TRY CATCH, EL FINALLY SIEMPRE SE EJECUTA AL FINAL. POR ESO MISMO, QUEREMOS QUE SIEMPRE SE CIERRA EL PREPAREDSTATEMENT Y EL RESULTSET
            cerrarEstados(stmt, rs);
        }
    };

    @Override
    public void insert(Filmografia film) throws SQLException {
        PreparedStatement stmt = null;

        try{
            stmt = db.prepareStatement(INSERT);
            cargarDatos("insert", stmt, film);
            stmt.executeUpdate();
            logger.info("Se ha hecho un Insert");
            db.commit();
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error insertando elemento");
            throw new SQLException("Error insertando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, null);
        }
    };

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(DELETE);
        
        try{
            stmt.setInt(1, id);
            stmt.executeUpdate();
            db.commit();
            logger.info("Se ha hecho un Delete");
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error eliminando elemento");
            throw new SQLException("Error eliminando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, null);
        }
    };

    @Override
    public void update(Filmografia film) throws SQLException {
        PreparedStatement stmt = null;
        
        try{
            stmt = db.prepareStatement(INSERT);
            cargarDatos("update", stmt, film);
            stmt.executeUpdate();
            db.commit();
            logger.info("Se ha hecho un Update");
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error actualizando elemento");
            throw new SQLException("Error actualizando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, null);
        } 
    };
}

    
    /*
    public void filmografia_update_mejor (int num_dato_modificar, String nuevo_Dato, 
            int id) throws SQLException{
        
        Filmografia film = null;
        film = filmografia_listone (id);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        switch(num_dato_modificar){
            case 1: 
                film = new Filmografia(
                        film.getId(), 
                        nuevo dato
                                )
            case 2:
                film = new Filmogradia()
        
        
        }
        try{
            stmt = db.prepareStatement(INSERT);
            cargarDatos("update", stmt, film);
            stmt.executeUpdate();
        }catch(SQLException e){
            hacerRollback(db);
            throw new SQLException("Error actualizando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, rs);
        } 
    };
    
    */