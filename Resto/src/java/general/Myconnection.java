/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author asus
 */
public class Myconnection {
    
    private final static String URL = "jdbc:postgresql://localhost/Resto" ;
    private final static String USER = "postgres" ;
    private final static String PASSWORD = "mdpprom13" ;
    
    public static Connection getConnection(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
            return connect;
        }
        catch(ClassNotFoundException | SQLException limit){
            limit.printStackTrace();
        }
        return null;
    }
    
}
