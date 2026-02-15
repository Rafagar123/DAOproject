package DAOs;

import DbManager.DbManager;
import Models.Reparto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartoDAO1 extends DAO<Reparto> {

    private Connection db;
    private static final String INSERT
            = "INSERT INTO reparto (id_filmografia, nombre_actor, papel) VALUES (?,?,?)";
    private static final String LISTALL
            = "SELECT * FROM reparto";

    private static final String LISTONE
            = "SELECT * FROM reparto WHERE id_reparto = ?";

    private static final String DELETE
            = "DELETE FROM reparto WHERE id_reparto = ?";

    private static final String UPDATE
            = "UPDATE reparto SET id_filmografia = ?, nombre_actor = ?, papel = ? WHERE id_reparto = ?";

    public RepartoDAO1(DbManager db) {
        this.db = db.getConnection();
        logger.info("Se ha establecido la conexión");
    }

    ;
    
    
    @Override
    protected void cargarDatos(String met, PreparedStatement stmt, Reparto reparto) throws SQLException {
        try {
            if (met == "insert") {
                // INSERT → solo datos (sin id_reparto)
                stmt.setInt(1, reparto.getId_filmografia());
                stmt.setString(2, reparto.getNombre_actor());
                stmt.setString(3, reparto.getPapel());
            } else if (met == "update") {
                // UPDATE → datos + id al final (WHERE)
                stmt.setInt(1, reparto.getId_filmografia());
                stmt.setString(2, reparto.getNombre_actor());
                stmt.setString(3, reparto.getPapel());
                stmt.setInt(4, reparto.getId_reparto());
            }
        } catch (SQLException e) {
            logger.error("Error cargando los datos");
            throw new SQLException("Error cargando los datos", e.getMessage());
        }
    }

    @Override
    protected Reparto crear(ResultSet rs) throws SQLException {
        try {
            return new Reparto(
                    rs.getInt("id_reparto"),
                    rs.getInt("id_filmografia"),
                    rs.getString("nombre_actor"),
                    rs.getString("papel"));
        } catch (SQLException e) {
            logger.error("Error al crear datos");
            throw new SQLException("Error al crear datos", e.getMessage());
        }
    }

    @Override
    public Reparto listOne(int id_reparto) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Reparto reparto = new Reparto();
        try {
            stmt = db.prepareStatement(LISTONE);
            stmt.setInt(1, id_reparto);
            rs = stmt.executeQuery();
            rs.next();
            db.commit();
            logger.info("Se ha hecho un ListOne");
            reparto = crear(rs);

        } catch (SQLException e) {
            hacerRollback(db);
            logger.error("Error al hacer ListOne");
            throw new SQLException("Error al hacer ListOne", e.getMessage());
        } finally {
            cerrarEstados(stmt, rs);
        }
        return reparto;
    }

        @Override
    public List<Reparto> listAll() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Reparto> listaReparto = new ArrayList<Reparto>();
        try {
            stmt =db.prepareStatement(LISTALL);
            rs =stmt.executeQuery();
            db.commit();
            while(rs.next()) {
                listaReparto.add(crear(rs));   
            }
            logger.info("Se ha hecho un ListAll");
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error listando reparto");
            throw new SQLException("Error listando repartos", e.getMessage());
        } finally { 
            cerrarEstados(stmt,rs);
        }
        return listaReparto;
    }

       @Override
    public void insert(Reparto reparto) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt =db.prepareStatement(INSERT);
            cargarDatos("INSERT", stmt, reparto);
            stmt.executeUpdate(); //NO USAR executeQuery en INSERT, UPDATE Y DELETE
            logger.info("Se ha hecho un Insert");
            db.commit();
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error insertando reparto");
            throw new SQLException("Error insertando elemento", e.getMessage());
            
        } finally {
            cerrarEstados(stmt, null);
        }
    }

     @Override
    public void update(Reparto reparto) throws SQLException {
            PreparedStatement stmt = null;
        try {
            stmt =db.prepareStatement(UPDATE);
            cargarDatos("UPDATE", stmt, reparto);
            stmt.executeUpdate(); //NO USAR executeQuery en INSERT, UPDATE Y DELETE
            logger.info("Se ha hecho un Update");
            db.commit();
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error actualizando reparto");
            throw new SQLException("Error actualizando elemento", e.getMessage());
            
        } finally {
            cerrarEstados(stmt, null);
        }
    }

     @Override
    public void delete(int id_reparto) throws SQLException {
        PreparedStatement stmt=db.prepareStatement(DELETE);
        try{
            stmt.setInt(1, id_reparto);
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
