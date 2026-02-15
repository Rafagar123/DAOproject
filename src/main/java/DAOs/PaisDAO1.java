
package DAOs;

import DbManager.DbManager;

import Models.Pais;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PaisDAO1 extends DAO<Pais> {
    private Connection db;
    
    private static final String INSERT =
            "INSERT INTO pais (id, nombre) VALUES (?,?)";
    private static final String LISTALL =
        "SELECT * FROM pais";

    private static final String LISTONE =
        "SELECT * FROM pais WHERE id = ?";

    private static final String DELETE =
        "DELETE FROM pais WHERE id = ?";

    private static final String UPDATE =
        "UPDATE pais SET nombre = ? WHERE id = ?";
    
    //constructor
    public PaisDAO1(DbManager db) {
        this.db = db.getConnection();
        logger.info("Se ha establecido la conexión");
    };

    @Override
    protected void cargarDatos(String met, PreparedStatement stmt, Pais pais) throws SQLException {
        try {
            if (met == "insert"){
                stmt.setInt(1, pais.getId());            
                stmt.setString(2, pais.getNombre());
               
            } else if (met == "update"){
                stmt.setInt(2, pais.getId());            
                stmt.setString(1, pais.getNombre());
            }
     }catch (SQLException e){
            logger.error("Error cargando los datos");
            throw new SQLException ("Error cargando los datos", e.getMessage());
        }    
    };

    @Override
    protected Pais crear(ResultSet rs) throws SQLException {
          try{
            return new Pais(
                rs.getInt("id"),
                rs.getString("nombre"));
                
        }catch (SQLException e){
            logger.error("Error creando elemento");
            throw new SQLException ("Error creando elemento", e.getMessage());
        }    
    }
    

    @Override
    public Pais listOne(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pais pais = new Pais();
        
        try{
            stmt = db.prepareStatement(LISTONE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            db.commit();
            logger.info("Se ha hecho un ListOne");
            pais = crear(rs);
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error listando elemento");
            throw new SQLException("Error listando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, rs);
        }
        return pais;
    }
    

    @Override
    public List<Pais> listAll() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Pais> listaPais = new ArrayList<Pais>();
        try {
            stmt =db.prepareStatement(LISTALL);
            rs =stmt.executeQuery();
            db.commit();
            while(rs.next()) {
                listaPais.add(crear(rs));   
            }
            logger.info("Se ha hecho un ListAll");
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error listando generos");
            throw new SQLException("Error listando generos", e.getMessage());
        } finally { 
            cerrarEstados(stmt,rs);
        }
        return listaPais;
    }

    @Override
    public void insert(Pais pais) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt =db.prepareStatement(INSERT);
            cargarDatos("INSERT", stmt, pais);
            stmt.executeUpdate(); //NO USAR executeQuery en INSERT, UPDATE Y DELETE
            logger.info("Se ha hecho un Insert");
            db.commit();
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error insertando pais");
            throw new SQLException("Error insertando elemento", e.getMessage());
            
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    public void update(Pais pais) throws SQLException {
            PreparedStatement stmt = null;
        try {
            stmt =db.prepareStatement(UPDATE);
            cargarDatos("UPDATE", stmt, pais);
            stmt.executeUpdate(); //NO USAR executeQuery en INSERT, UPDATE Y DELETE
            logger.info("Se ha hecho un Update");
            db.commit();
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error actualizando pais");
            throw new SQLException("Error actualizando elemento", e.getMessage());
            
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement stmt=db.prepareStatement(DELETE);
        try{
            stmt.setInt(1, id);
            stmt.executeUpdate();
            db.commit();
            logger.info("Se ha hecho un Delete");
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.info("Error haciendo Delete");
            throw new SQLException("Error haciendo delete en elemento", e.getMessage());
        } finally {
            cerrarEstados(stmt, null);
        }
    }
    
}