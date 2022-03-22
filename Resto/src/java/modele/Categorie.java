/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import general.Myclass;

/**
 *
 * @author RAKOTONDRAINIBE
 */
public class Categorie extends Myclass{
    String idCategorie;
    String nom;

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categorie(String idCategorie, String nom) {
        this.idCategorie = idCategorie;
        this.nom = nom;
    }
    
}
