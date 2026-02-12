/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DbManager;

public class ManagerMain {
        public static void main(String[] args) {
            DbManager manager = new DbManager();
            manager.conectar();
            manager.desconectar();  
        }
}
