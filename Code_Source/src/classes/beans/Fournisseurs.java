/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.beans;

/**
 *
 * @author ADMIN
 */
public class Fournisseurs {
    
    private int id;
    
        private String code;
        
        
     private String nom;
     
      
         private String telephone;
         
             private String email;
          
    private String ville;
    
    private boolean actif;
    
    private int categorie_id;

    /**
     * Get the value of categorie_id
     *
     * @return the value of categorie_id
     */
    public int getCategorie_id() {
        return categorie_id;
    }

    /**
     * Set the value of categorie_id
     *
     * @param categorie_id new value of categorie_id
     */
    public void setCategorie_id(int categorie_id) {
        this.categorie_id = categorie_id;
    }


    /**
     * Get the value of actif
     *
     * @return the value of actif
     */
    public boolean isActif() {
        return actif;
    }

    /**
     * Set the value of actif
     *
     * @param actif new value of actif
     */
    public void setActif(boolean actif) {
        this.actif = actif;
    }


    /**
     * Get the value of ville
     *
     * @return the value of ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * Set the value of ville
     *
     * @param ville new value of ville
     */
    public void setVille(String ville) {
        this.ville = ville;
    }


    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Get the value of telephone
     *
     * @return the value of telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Set the value of telephone
     *
     * @param telephone new value of telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    
    public Fournisseurs() {
    }

    public Fournisseurs(String code, String nom, String telephone, String email, String ville, boolean actif, int categorie_id) {
        this.code = code;
        this.nom = nom;
        this.telephone = telephone;
        this.email = email;
        this.ville = ville;
        this.actif = actif;
        this.categorie_id = categorie_id;
    }

    @Override
    public String toString() {
        return "Fournisseurs{" + "code=" + code + ", nom=" + nom + ", telephone=" + telephone + ", email=" + email + ", ville=" + ville + ", actif=" + actif + ", categorie_id=" + categorie_id + '}';
    }
    
    

    
}
