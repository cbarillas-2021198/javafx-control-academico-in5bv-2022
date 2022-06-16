package org.in5bv.edyletona.db;

/**
 *
 * @author Edy Leonel Letona Argueta
 * 
 */


import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

public class Conexion {
    
    private final String IP_SERVER = "localhost"; 
    private final String PORT = "3306"; 
    private final String DB = "db_control_academico_in5bv"; 
    private final String USER = "kinal"; 
    private final String PASSWORD = "admin";
    private final String URL; 
    private Connection conexion;
    
    private static Conexion instancia; 
    
    public static Conexion getInstance(){
        
        if (instancia == null){
            instancia = new Conexion(); 
        }
        
        return instancia; 
    }
    
    private Conexion (){
        URL = "jdbc:mysql://" + IP_SERVER + ":" + PORT + "/" + DB;  
        
        try {
            
            //Class.forName("com.mysql.jdbc.Driver").newInstance(); 
            
            //Class.forName("com.mysql.jdbc.Driver"); 
            
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa");
            
            DatabaseMetaData dma = conexion.getMetaData(); 
            System.out.println("\nConnected to: " + dma.getURL());
            System.out.println("Driver: " + dma.getDriverName());
            System.out.println("Version: " + dma.getDriverVersion());
        /*
        }catch (InstantiationException ex){
            System.err.println("No se puede crear una instancia del objeto");
            ex.printStackTrace();
        } catch (IllegalAccessException ex){
            System.err.println("No se tiene los permisos para acceder al paquete");
            ex.printStackTrace();
        */
        }catch (ClassNotFoundException ex){
            System.err.println("No se encuentra ninguna definición para la clase");
            ex.printStackTrace();
        }catch (CommunicationsException ex){
            System.err.println("ERROR: No se puede establecer comunicaión con el servidor de MySQL"
                    + "MySQL"
                    + "\nRecomendación"
                    + "\nVerifique que el nombre del HOST o la IP_SERVER sea correcta" 
                    + "\nVerifique que el servicio de MYSQL esté en ejecución");    
            ex.printStackTrace(); 
        } catch (SQLException ex) {
            System.err.println("Se prudujo un error de tipo SQLException");
            System.err.println("Message:" + ex.getMessage());
            System.err.println("Error code:" + ex.getErrorCode());
            System.out.println("SQLState:" + ex.getSQLState());
            ex.printStackTrace(); 
        } catch (Exception ex) {
            System.err.println("Se produjo un error al establecer una conexión en esta base de datos");
            ex.printStackTrace(); 
        }
    }
    
    public Connection getConexion(){
        return conexion; 
    }
}
