package DAOs;

import DbManager.DbManager;
import java.sql.SQLException;
import java.sql.Date;

public class MainTest {
public static void main(String[] args) {
    
  DbManager db= new DbManager();  
  db.conectar();
    try {
        db.getConnection().setAutoCommit(false);
    } catch (SQLException ex) {
        System.getLogger(MainTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }
  AccesoDAO acceso = new AccesoDAO(db);
  
    try{
    //    acceso.insert(8, 6, 5, Date.valueOf("2022-02-17"), 5);
    //    film.listall();
    //    film.update(8, "Johny Glamour", Date.valueOf("2001-07-28"), "Aventura hipnótica", 4, 1);
    //    film.filmografia_listall();
    //    film.filmografia_delete(8);
       acceso.listAll();

    }catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    
  db.desconectar();
 }   
}
