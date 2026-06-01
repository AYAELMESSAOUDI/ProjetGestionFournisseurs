package classes.beans;

public class CategorieFournisseurs implements java.io.Serializable {

    private int id;
    private String libelle;

    // 1. LE CONSTRUCTEUR VIDE (Obligatoire pour Hibernate)
    public CategorieFournisseurs() {
    }

    // 2. LE CONSTRUCTEUR AVEC PARAMÈTRE
    public CategorieFournisseurs(String libelle) {
        this.libelle = libelle;
    }

    // 3. GETTERS & SETTERS
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "CategorieFournisseurs{id=" + id + ", libelle=" + libelle + "}";
    }
}