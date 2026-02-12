package DAOs;

import DbManager.DbManager;
import Models.Factura;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacturaDAO {
    private Connection db;
    private static final String INSERT = "INSERT INTO factura (num_factura, suscripcion_id, "
            + "cuenta_id, importe_pvp, metodo_pago, fecha_factura) VALUES (?,?,?,?,?,?)";
    private static final String LISTALL = "SELECT * FROM factura";
    private static final String LISTONE = "SELECT * FROM factura WHERE num_factura = ?";
    private static final String DELETE = "DELETE FROM factura WHERE num_factura = ?";
    private static final String UPDATE = "UPDATE acceso SET suscripcion_id = ?, cuenta_id = ?, "
            + "importe_pvp = ?, metodo_pago = ?, fecha_factura = ? WHERE num_Factura = ?";

    public FacturaDAO(DbManager db) {
        this.db = db.getConnection();
    }
    
    public void factura_listall () throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTALL);
        ResultSet rs = stmt.executeQuery();
        
        try{
            while(rs.next()){
                Factura factura = new Factura
                (rs.getInt("num_factura"),
                rs.getInt("suscripcion_id"),
                rs.getInt("cuenta_id"),
                rs.getFloat("importe_pvp"),
                rs.getString("metodo_pago"),
                rs.getDate("fecha_factura"));
                System.out.println(factura.toString());    
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
            
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
        }
    };
    
    public void factura_listone (int num_factura) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(LISTONE);
        stmt.setInt(1, num_factura);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        try{
            Factura factura = new Factura
            (rs.getInt("num_factura"),
            rs.getInt("suscripcion_id"),
            rs.getInt("cuenta_id"),
            rs.getFloat("importe_pvp"),
            rs.getString("metodo_pago"),
            rs.getDate("fecha_factura"));
            System.out.println(factura.toString());       
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
            
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
        }
    };

    
    public void factura_delete (int num_factura) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(DELETE);
        try{
            stmt.setInt(1, num_factura);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
        }
    };
    
    public void factura_insert (int num_factura, int suscripcion_id, int cuenta_id, 
            float importe_pvp, String metodo_pago, Date fecha_factura) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(INSERT);
        try{
            stmt.setInt(1, num_factura);
            stmt.setInt(2, suscripcion_id);
            stmt.setInt(3, cuenta_id);
            stmt.setFloat(4, importe_pvp);
            stmt.setString(5, metodo_pago);
            stmt.setDate(6, fecha_factura); 
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
        }
    };
    
    public void factura_update (int num_factura, int suscripcion_id, int cuenta_id, 
            float importe_pvp, String metodo_pago, Date fecha_factura) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(UPDATE);
        try{
            stmt.setInt(6, num_factura);
            stmt.setInt(1, suscripcion_id);
            stmt.setInt(2, cuenta_id);
            stmt.setFloat(3, importe_pvp);
            stmt.setString(4, metodo_pago);
            stmt.setDate(5, fecha_factura);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                throw new SQLException("Error cerrando statement", e);
            }
        }
    };
  
}
