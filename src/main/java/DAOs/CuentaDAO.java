package DAOs;

import DbManager.DbManager;
import Models.Cuenta;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DbManager.Logger;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO extends DAO<Cuenta>{
    private Connection db;
    private static final String INSERT = "INSERT INTO cuenta (id_cuenta, tipo_cuenta, "
            + "nombre, password_hash) VALUES (?,?,?,?)";
    private static final String LISTALL = "SELECT * FROM cuenta";
    private static final String LISTONE = "SELECT * FROM cuenta WHERE id_cuenta = ?";
    private static final String DELETE = "DELETE FROM cuenta WHERE id_cuenta = ?";
    private static final String UPDATE = "UPDATE cuenta SET tipo_cuenta = ?,  "
            + "nombre = ?, password_hash = ? WHERE id_cuenta = ?";

    public CuentaDAO(DbManager db) {
        this.db = db.getConnection();
        logger.info("Se ha establecido la conexión");
    };

    @Override
    protected void cargarDatos(String met, PreparedStatement stmt, Cuenta cuenta) throws SQLException {
        try{
            if (met == "insert"){
                stmt.setInt(1, cuenta.getId_cuenta());            
                stmt.setString(2, cuenta.getTipo_cuenta());
                stmt.setString(3, cuenta.getNombre());
                stmt.setString(4, cuenta.getPassword_hash());
            } else if (met == "update"){
                stmt.setInt(4, cuenta.getId_cuenta());            
                stmt.setString(1, cuenta.getTipo_cuenta());
                stmt.setString(2, cuenta.getNombre());
                stmt.setString(3, cuenta.getPassword_hash());
            }
        }catch (SQLException e){
            logger.error("Error cargando los datos");
            throw new SQLException ("Error cargando los datos", e.getMessage());
        }    
    };    

    @Override
    protected Cuenta crear(ResultSet rs) throws SQLException {
        try{
            return new Cuenta(
                rs.getInt("id_cuenta"),
                rs.getString("tipo_cuenta"),
                rs.getString("nombre"),
                rs.getString("password_hash"));
        }catch (SQLException e){
            logger.error("Error creando elemento");
            throw new SQLException ("Error creando elemento", e.getMessage());
        }      
    };

    @Override
    public Cuenta listOne(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cuenta cuenta = new Cuenta();
        
        try{
            stmt = db.prepareStatement(LISTONE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            db.commit();
            logger.info("Se ha hecho un ListOne");
            cuenta = crear(rs);
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error listando elemento");
            throw new SQLException("Error listando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, rs);
        }
        return cuenta;
    };
    
    @Override
    public List<Cuenta> listAll() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Cuenta> list = new ArrayList<Cuenta>();
        
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
    public void insert(Cuenta cuenta) throws SQLException {
        PreparedStatement stmt = null;

        try{
            stmt = db.prepareStatement(INSERT);
            cargarDatos("insert", stmt, cuenta);
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
    public void update(Cuenta cuenta) throws SQLException {
        PreparedStatement stmt = null;
        
        try{
            stmt = db.prepareStatement(UPDATE);
            cargarDatos("update", stmt, cuenta);
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