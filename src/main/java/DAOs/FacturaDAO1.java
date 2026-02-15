
package DAOs;

import DbManager.DbManager;
import Models.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;
import java.util.ArrayList;



public class FacturaDAO1 extends DAO<Factura>{

    private Connection db;
    
    private static final String INSERT =
            "INSERT INTO factura (num_factura, suscripcion_id,cuenta_id,importe_pvp,metodo_pago,fecha_factura) VALUES (?,?,?,?,?,?)";
    private static final String LISTALL =
        "SELECT * FROM factura";

    private static final String LISTONE =
        "SELECT * FROM factura WHERE num_factura = ?";

    private static final String DELETE =
        "DELETE FROM factura WHERE num_factura = ?";

    private static final String UPDATE =
        "UPDATE factura SET suscripcion_id=?, cuenta_id = ?, importe_pvp=?, metodo_pago = ?, fecha_factura = ? WHERE num_factura = ?";
    
    //constructor
    public FacturaDAO1(DbManager db) {
        this.db = db.getConnection();
        logger.info("Se ha establecido la conexión");
    };

    @Override
    protected void cargarDatos(String met, PreparedStatement stmt, Factura factura) throws SQLException {
        try {
            if (met == "insert"){
                stmt.setInt(1, factura.getNum_factura());            
                stmt.setInt(2, factura.getSuscripcion_id());
                stmt.setInt(3, factura.getCuenta_id());
                stmt.setFloat(4, factura.getImporte_pvp());
                stmt.setString(5, factura.getMetodo_pago());
                stmt.setDate(6,factura.getFecha_factura());
               
            } else if (met == "update"){
                 stmt.setInt(6, factura.getNum_factura());            
                stmt.setInt(1, factura.getSuscripcion_id());
                stmt.setInt(2, factura.getCuenta_id());
                stmt.setFloat(3, factura.getImporte_pvp());
                stmt.setString(4, factura.getMetodo_pago());
                stmt.setDate(5,factura.getFecha_factura());
            }
     }catch (SQLException e){
            logger.error("Error cargando los datos");
            throw new SQLException ("Error cargando los datos", e.getMessage());
        }    
    }

    @Override
    protected Factura crear(ResultSet rs) throws SQLException {
          try{
            return new Factura(
                rs.getInt("num_factura"),
                rs.getInt("suscripcion_id"),
                rs.getInt("cuenta_id"),
                rs.getFloat("importe_pvp"),//no me deja poner double porque creo que en la clase Facturas esta como float
                rs.getString("metodo_pago"),
                rs.getDate("fecha_factura")
            );
                
                
                
        }catch (SQLException e){
            logger.error("Error creando elemento");
            throw new SQLException ("Error creando elemento", e.getMessage());
        }    
    }
    

    @Override
    public Factura listOne(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Factura factura = new Factura();
        
        try{
            stmt = db.prepareStatement(LISTONE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            db.commit();
            logger.info("Se ha hecho un ListOne");
            factura = crear(rs);
        }catch(SQLException e){
            hacerRollback(db);
            logger.error("Error listando elemento");
            throw new SQLException("Error listando elemento", e.getMessage());
        }finally {
            cerrarEstados(stmt, rs);
        }
        return factura;
    }
    

    @Override
    public List<Factura> listAll() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Factura> listaFactura = new ArrayList<Factura>();
        try {
            stmt =db.prepareStatement(LISTALL);
            rs =stmt.executeQuery();
            db.commit();
            while(rs.next()) {
                listaFactura.add(crear(rs));   
            }
            logger.info("Se ha hecho un ListAll");
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error listando facturas");
            throw new SQLException("Error listando facturas", e.getMessage());
        } finally { 
            cerrarEstados(stmt,rs);
        }
        return listaFactura;
    }

    @Override
    public void insert(Factura factura) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt =db.prepareStatement(INSERT);
            cargarDatos("INSERT", stmt, factura);
            stmt.executeUpdate(); //NO USAR executeQuery en INSERT, UPDATE Y DELETE
            logger.info("Se ha hecho un Insert");
            db.commit();
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error insertando factura");
            throw new SQLException("Error insertando elemento", e.getMessage());
            
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    public void update(Factura factura) throws SQLException {
            PreparedStatement stmt = null;
        try {
            stmt =db.prepareStatement(UPDATE);
            cargarDatos("UPDATE", stmt, factura);
            stmt.executeUpdate(); //NO USAR executeQuery en INSERT, UPDATE Y DELETE
            logger.info("Se ha hecho un Update");
            db.commit();
            
        } catch(SQLException e) {
            hacerRollback(db);
            logger.error("Error actualizando factura");
            throw new SQLException("Error actualizando elemento", e.getMessage());
            
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    public void delete(int num_factura) throws SQLException {
        PreparedStatement stmt=db.prepareStatement(DELETE);
        try{
            stmt.setInt(1,num_factura);
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