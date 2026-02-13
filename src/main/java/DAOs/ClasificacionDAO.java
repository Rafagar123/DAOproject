package DAOs;

import DbManager.DbManager;
import Models.Clasificacion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DbManager.Logger;
import java.util.ArrayList;
import java.util.List;

public class ClasificacionDAO extends DAO<Clasificacion>{
    private Connection db;
    private static final String INSERT = "INSERT INTO clasificacion (id, nombre) VALUES (?,?)";
    private static final String LISTALL = "SELECT * FROM clasificacion";
    private static final String LISTONE = "SELECT * FROM clasificacion WHERE id = ?";
    private static final String DELETE = "DELETE FROM clasificacion WHERE id = ?";
    private static final String UPDATE = "UPDATE clasificacion SET nombre = ? WHERE id = ?";

    public ClasificacionDAO(DbManager db) {
        this.db = db.getConnection();
        logger.info("Se ha establecido la conexión");
    };

    @Override
    protected void cargarDatos(String met, PreparedStatement stmt, Clasificacion clasificacion) throws SQLException {
        try{
            if (met == "insert"){
                stmt.setInt(1, clasificacion.getId());            
                stmt.setString(2, clasificacion.getNombre());
            } else if (met == "update"){
                stmt.setInt(2, clasificacion.getId());            
                stmt.setString(1, clasificacion.getNombre());
            }
        }catch (SQLException e){
            logger.error("Error cargando los datos");
            throw new SQLException ("Error cargando los datos", e.getMessage());
        }    
    };

    @Override
    protected Clasificacion crear(ResultSet rs) throws SQLException {
        try{
            return new Clasificacion(
                rs.getInt("id"),
                rs.getString("nombre"));
        }catch (SQLException e){
            logger.error("Error creando elemento");
            throw new SQLException ("Error creando Filmografia", e.getMessage());
        }    
    };

    @Override
    public Clasificacion listOne(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Clasificacion clasificacion = new Clasificacion();
        
        try{
            stmt = db.prepareStatement(LISTONE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            db.commit();
            logger.info("Se ha hecho un ListOne");
            clasificacion = crear(rs);
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error listando elemento");
            throw new SQLException("Error listando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, rs);
        }
        return clasificacion;
    };

    @Override
    public List<Clasificacion> listAll() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Clasificacion> list = new ArrayList<Clasificacion>();
        
        try{
            stmt = db.prepareStatement(LISTALL);
            rs = stmt.executeQuery();
            db.commit();
            while(rs.next()){
                list.add(crear(rs));
            }
            logger.info("Se ha hecho un ListAll");
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error listando elementos");
            throw new SQLException("Error listando elementos", e.getMessage());
        }finally { // TEN EN CUENTA QUE EN UN TRY CATCH, EL FINALLY SIEMPRE SE EJECUTA AL FINAL. POR ESO MISMO, QUEREMOS QUE SIEMPRE SE CIERRA EL PREPAREDSTATEMENT Y EL RESULTSET
            cerrarEstados(stmt, rs);
        }
        return list;        
    };

    @Override
    public void insert(Clasificacion clasificacion) throws SQLException {
        PreparedStatement stmt = null;

        try{
            stmt = db.prepareStatement(INSERT);
            cargarDatos("insert", stmt, clasificacion);
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
    public void update(Clasificacion clasificacion) throws SQLException {
        PreparedStatement stmt = null;
        
        try{
            stmt = db.prepareStatement(UPDATE);
            cargarDatos("update", stmt, clasificacion);
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
  
}
