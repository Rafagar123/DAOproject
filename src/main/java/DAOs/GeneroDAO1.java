
package DAOs;

import DbManager.DbManager;
import Models.Genero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GeneroDAO1 extends DAO<Genero> {
    private Connection db;
    
    private static final String INSERT =
            "INSERT INTO genero (id, nombre) VALUES (?,?)";
    private static final String LISTALL =
        "SELECT * FROM genero";

    private static final String LISTONE =
        "SELECT * FROM genero WHERE id = ?";

    private static final String DELETE =
        "DELETE FROM genero WHERE id = ?";

    private static final String UPDATE =
        "UPDATE genero SET nombre = ? WHERE id = ?";
    
    //constructor
    public GeneroDAO1(DbManager db) {
        this.db = db.getConnection();
        logger.info("Se ha establecido la conexión");
    };

    @Override
    protected void cargarDatos(String met, PreparedStatement stmt, Genero gene) throws SQLException {
        try {
            if (met == "insert"){
                stmt.setInt(1, gene.getId());            
                stmt.setString(2, gene.getNombre());
               
            } else if (met == "update"){
                stmt.setInt(2, gene.getId());            
                stmt.setString(1, gene.getNombre());
            }
     }catch (SQLException e){
            logger.error("Error cargando los datos");
            throw new SQLException ("Error cargando los datos", e.getMessage());
        }    
    };

    @Override
    protected Genero crear(ResultSet rs) throws SQLException {
          try{
            return new Genero(
                rs.getInt("id"),
                rs.getString("nombre"));
                
        }catch (SQLException e){
            logger.error("Error creando elemento");
            throw new SQLException ("Error creando elemento", e.getMessage());
        }    
    };
    

    @Override
    public Genero listOne(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Genero gene = new Genero();
        
        try{
            stmt = db.prepareStatement(LISTONE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            db.commit();
            logger.info("Se ha hecho un ListOne");
            gene = crear(rs);
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error listando elemento");
            throw new SQLException("Error listando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, rs);
        }
        return gene;
    };
    

    @Override
    public List<Genero> listAll() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Genero> listaGenero = new ArrayList<Genero>();
        try {
            stmt =db.prepareStatement(LISTALL);
            rs =stmt.executeQuery();
            db.commit();
            while(rs.next()) {
                listaGenero.add(crear(rs));   
            }
            logger.info("Se ha hecho un ListAll");
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error listando generos");
            throw new SQLException("Error listando generos", e.getMessage());
        } finally { 
            cerrarEstados(stmt,rs);
        }
        return listaGenero;
    }

    @Override
    public void insert(Genero gene) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt =db.prepareStatement(INSERT);
            cargarDatos("INSERT", stmt, gene);
            stmt.executeUpdate(); //NO USAR executeQuery en INSERT, UPDATE Y DELETE
            logger.info("Se ha hecho un Insert");
            db.commit();
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error insertando genero");
            throw new SQLException("Error insertando elemento", e.getMessage());
            
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    public void update(Genero gene) throws SQLException {
            PreparedStatement stmt = null;
        try {
            stmt =db.prepareStatement(UPDATE);
            cargarDatos("UPDATE", stmt, gene);
            stmt.executeUpdate(); //NO USAR executeQuery en INSERT, UPDATE Y DELETE
            logger.info("Se ha hecho un Update");
            db.commit();
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error actualizando genero");
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
