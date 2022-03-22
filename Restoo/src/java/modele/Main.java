/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;
import general.Myclass;
import general.Myconnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author RAKOTONDRAINIBE
 */
public class Main {
    public static void main(String[] ftft)
    {
        Myconnection conn=new Myconnection();
        Connection con=conn.getConnection();
        try {
            List lp=new Produit().find("select * from Produit", con);
            for(int i=0;i<lp.size();i++){
                Produit enc=(Produit) lp.get(i);
                    System.out.println("dfdf"+enc.getNom());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
