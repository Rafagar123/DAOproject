package DAOs;

import DbManager.DbManager;
import Models.Acceso;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DbManager.Logger;
import java.util.ArrayList;
import java.util.List;


public class AccesoDAO extends DAO<Acceso>{
    private Connection db;
    private static final String INSERT = "INSERT INTO acceso"
            + "(id_acceso, id_filmografia, id_cuenta, fecha_acceso, tipo_suscripcion_id) VALUES (?,?,?,?,?)";
    private static final String LISTALL = "SELECT * FROM acceso";
    private static final String LISTONE = "SELECT * FROM acceso WHERE id_acceso = ?";
    private static final String DELETE = "DELETE FROM acceso WHERE id_acceso = ?";
    private static final String UPDATE = "UPDATE acceso SET id_filmografia= ?, "
            + "id_cuenta= ?, fecha_acceso = ?, tipo_suscripcion_id= ? WHERE id_acceso = ?";

    
    public AccesoDAO(DbManager db) {
        this.db = db.getConnection();
        logger.info("Se ha establecido la conexión");
    };

    @Override
    protected void cargarDatos(String met, PreparedStatement stmt, Acceso acceso) throws SQLException {
        try{
            if (met == "insert"){
                stmt.setInt(1, acceso.getId_acceso());            
                stmt.setInt(2, acceso.getId_filmografia());
                stmt.setInt(3, acceso.getId_cuenta());
                stmt.setDate(4, acceso.getFecha_acceso());
                stmt.setInt(5, acceso.getTipo_suscripcion_id());
            } else if (met == "update"){
                stmt.setInt(5, acceso.getId_acceso());            
                stmt.setInt(1, acceso.getId_filmografia());
                stmt.setInt(2, acceso.getId_cuenta());
                stmt.setDate(3, acceso.getFecha_acceso());
                stmt.setInt(4, acceso.getTipo_suscripcion_id());
            }
        }catch (SQLException e){
            logger.error("Error cargando los datos");
            throw new SQLException ("Error cargando los datos", e.getMessage());
        }    
    };

    @Override
    protected Acceso crear(ResultSet rs) throws SQLException {
        try{
            return new Acceso(
                rs.getInt("id_acceso"),
                rs.getInt("id_filmografia"),
                rs.getInt("id_cuenta"),
                rs.getDate("fecha_acceso"),
                rs.getInt("tipo_suscripcion_id"));
        }catch (SQLException e){
            logger.error("Error creando elemento");
            throw new SQLException ("Error creando elemento", e.getMessage());
        }     
    };

    @Override
    public Acceso listOne(int id) throws SQLException {
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
    public List<Acceso> listAll() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Acceso> list = new ArrayList<Acceso>();
        
        try{
            stmt = db.prepareStatement(LISTALL);
            rs = stmt.executeQuery();
            db.commit();
            while(rs.next()){
                list.add(crear(rs));
            }
            System.out.println(list);
            logger.info("Se ha hecho un ListAll");
            return list; 
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error listando elementos");
            throw new SQLException("Error listando elementos", e.getMessage());
        }finally { // TEN EN CUENTA QUE EN UN TRY CATCH, EL FINALLY SIEMPRE SE EJECUTA AL FINAL. POR ESO MISMO, QUEREMOS QUE SIEMPRE SE CIERRA EL PREPAREDSTATEMENT Y EL RESULTSET
            cerrarEstados(stmt, rs);
        }
    };

    @Override
    public void insert(Acceso acceso) throws SQLException {
        PreparedStatement stmt = null;

        try{
            stmt = db.prepareStatement(INSERT);
            cargarDatos("insert", stmt, acceso);
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
    public void update(Acceso acceso) throws SQLException {
        PreparedStatement stmt = null;
        
        try{
            stmt = db.prepareStatement(INSERT);
            cargarDatos("update", stmt, acceso);
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