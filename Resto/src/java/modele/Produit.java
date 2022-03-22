/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import general.Myclass;

/**
 *
 * @author asus
 */

public class Produit extends Myclass {
    String idProduit;
    String nom;
    String idCategorie;
    double prixUnitaire;

    public Produit() {
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Produit(String idProduit, String nom, String idCategorie, double prixUnitaire) {
        this.idProduit = idProduit;
        this.nom = nom;
        this.idCategorie = idCategorie;
        this.prixUnitaire = prixUnitaire;
    }
 
    
}
