/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classes.beans;
import java.math.BigDecimal;
/**
 *
 * @author ADMIN
 */
public class Produit {
    
    private int id;
    
    private String code;
    
    private String nom;

    private String type;
    
    private BigDecimal prix_unitaire;
    
     private int stock;
     
         private boolean disponible;
         
             private int fournisseur_id;

    /**
     * Get the value of fournisseur_id
     *
     * @return the value of fournisseur_id
     */
    public int getFournisseur_id() {
        return fournisseur_id;
    }

    /**
     * Set the value of fournisseur_id
     *
     * @param fournisseur_id new value of fournisseur_id
     */
    public void setFournisseur_id(int fournisseur_id) {
        this.fournisseur_id = fournisseur_id;
    }


    /**
     * Get the value of disponible
     *
     * @return the value of disponible
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Set the value of disponible
     *
     * @param disponible new value of disponible
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

        

    /**
     * Get the value of stock
     *
     * @return the value of stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Set the value of stock
     *
     * @param stock new value of stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }


    /**
     * Get the value of prix_unitaire
     *
     * @return the value of prix_unitaire
     */
    public BigDecimal getPrix_unitaire() {
        return prix_unitaire;
    }

    /**
     * Set the value of prix_unitaire
     *
     * @param prix_unitaire new value of prix_unitaire
     */
    public void setPrix_unitaire(BigDecimal prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }


    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the value of nom
     *
     * @return the value of nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set the value of nom
     *
     * @param nom new value of nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the value of code
     *
     * @param code new value of code
     */
    public void setCode(String code) {
        this.code = code;
    }


    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }
 public Produit(){
 
 
 }
    
    
    
    public Produit(String code, String nom, String type, BigDecimal prix_unitaire, int stock, boolean disponible, int fournisseur_id) {
        this.code = code;
        this.nom = nom;
        this.type = type;
        this.prix_unitaire = prix_unitaire;
        this.stock = stock;
        this.disponible = disponible;
        this.fournisseur_id = fournisseur_id;
    }

    @Override
    public String toString() {
        return "Produit{" + "code=" + code + ", nom=" + nom + ", type=" + type + ", prix_unitaire=" + prix_unitaire + ", stock=" + stock + ", disponible=" + disponible + ", fournisseur_id=" + fournisseur_id + '}';
    }

    
}
