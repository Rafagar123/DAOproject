package DAOs;

import DbManager.DbManager;
import Models.Cuenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CuentaDAO extends DAO<Cuenta>{
    private Connection db;
    private static final String INSERT = "INSERT INTO cuenta (id_cuenta, tipo_cuenta, "
            + "nombre, password_hash) VALUES (?,?,?,?)";
    private static final String LISTALL = "SELECT * FROM cuenta";
    private static final String LISTONE = "SELECT * FROM cuenta WHERE id_cuenta = ?";
    private static final String DELETE = "DELETE FROM clasificacion WHERE id_cuenta = ?";
    private static final String UPDATE = "UPDATE clasificacion SET tipo_cuenta = ?,  "
            + "nombre = ?, password_hash = ? WHERE id_cuenta = ?";

    public CuentaDAO(DbManager db) {
        this.db = db.getConnection();
    }

    @Override
    protected void cargarDatos(String met, PreparedStatement stmt, Cuenta obj) throws SQLException {
    }

    @Override
    protected Cuenta crear(ResultSet rs) throws SQLException {
    }

    @Override
    public Cuenta listOne(int id) throws SQLException {
    }

    @Override
    public void listAll() throws SQLException {
    }

    @Override
    public void insert(Cuenta obj) throws SQLException {
    }

    @Override
    public void update(Cuenta obj) throws SQLException {
    }

    @Override
    public void delete(int id) throws SQLException {
    }
    
    
}