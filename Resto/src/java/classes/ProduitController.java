/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import general.Myclass;
import general.Myconnection;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import modele.Produit;
import traitement.ANNOT;
import traitement.ANNOTCLASS;
import traitement.ModelView;

@ANNOTCLASS(name="produit", classname="ProduitController", mapping="controller")
public class ProduitController {
    
    public double numero;
    public String intitule;
    
   
    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }
    
    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
    
    @ANNOT(name="lister", classname="ProduitController", mapping="method")
    public ModelView lister() throws Exception{
        HashMap retour = new HashMap();
        Myconnection conn=new Myconnection();
        Connection con=conn.getConnection();
        List lpro=new Myclass().find("select * from Produit", con);
        retour.put("listpro",lpro);
        ModelView md=new ModelView(retour,"Pages/liste","");
        return md;
    }
    
    @ANNOT(name="list", classname="AdminController", mapping="controller")
    public ModelView indexinsert() throws Exception{
        HashMap map = new HashMap();  
        Myconnection conn=new Myconnection();
        Connection con=conn.getConnection();
        List lpro=new Myclass().find("select * from Produit", con);
        map.put("listpro",lpro);
        map.put("view", "insertutilisateur.jsp");
        return new ModelView(map,"pages/liste","");
    }
}
