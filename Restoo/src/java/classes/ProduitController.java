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
import javax.servlet.http.HttpServletRequest;
import modele.Categorie;
import modele.Produit;
import traitement.ANNOT;
import traitement.ANNOTCLASS;
import traitement.ModelView;

@ANNOTCLASS(name="produit", classname="ProduitController", mapping="classes")
public class ProduitController {
    
    public String categorie;

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
   
    
    @ANNOT(name="listercat", classname="ProduitController", mapping="method")
    public ModelView listercat(HttpServletRequest request) throws Exception{
        HashMap retour = new HashMap();
        Myconnection conn=new Myconnection();
        Connection con=conn.getConnection();
        List lpro=new Produit().find("select * from Produit where idCategorie='"+request.getParameter("categorie")+"'", con);
              List lcat=new Categorie().find("select * from Categorie where idCategorie='"+request.getParameter("categorie")+"'", con);
      List lcats=new Categorie().find("select * from Categorie", con);
        retour.put("lpro",lpro);
            retour.put("lcat",lcat);
 retour.put("lcats",lcats);
        ModelView md=new ModelView(retour,"Pages/Produit/lister","");
        return md;
    }
    
    @ANNOT(name="lister", classname="ProduitController", mapping="method")
    public ModelView lister() throws Exception{
        HashMap retour = new HashMap();
        Myconnection conn=new Myconnection();
        Connection con=conn.getConnection();
            List lcats=new Categorie().find("select * from Categorie", con);
  List lpro=new Produit().find("select * from Produit", con);
        retour.put("lpro",lpro);
 retour.put("lcats",lcats);
        ModelView md=new ModelView(retour,"Pages/Produit/lister","");
        return md;
    }
}
