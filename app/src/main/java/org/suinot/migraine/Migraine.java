package org.suinot.migraine;

import android.util.Log;

/**
 * Created by remi on 15/10/16.
 * <p>
 * base de données Migraine
 * id
 * nom -> unique ou non, si pas de nom, alors app le donne?
 * date
 * heure
 * duree
 * commentaire(s)
 */

class Migraine {

    private int id;
    private String nom;
    private Integer id_douleur;
    private String date;
    private String heure;
    private String duree;
    private String commentaire;

    Migraine() {
    }

    public Migraine (String nom, Integer douleur, String date, String heure, String duree, String comm){
        super ();
        this.nom = nom;
        this.id_douleur = douleur;
        this.date = date;
        this.heure = heure;
        this.duree = duree;
        this.commentaire = comm;
    }

    /*
    ---------- Demande de données ----------
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getnom_migraine() {
        return nom;
    }

    Integer getdouleur_migraine() {
        return id_douleur;
    }

    String getdate_migraine() {
        return date;
    }

    String getheure_migraine() {
        return heure;
    }

    String getduree_migraine() {
        return duree;
    }

    String getcommentaire() {
        return commentaire;
    }

    /*
      ---------- Mise à jour de données ----------
    */

    void setnom_migraine(String nom) { this.nom = nom; }

    void setdouleur_migraine(Integer douleur) { this.id_douleur = douleur; }

    void setdate_migraine(String date) {
        this.date = date;
    }

    void setheure_migraine(String heure) {
        this.heure = heure;
    }

    void setduree_migraine(String duree) { this.duree = duree; }

    void setcommentaire(String comm) {
        this.commentaire = comm;
    }

    /*
    ----------- Méthode globale ----------------
    */
    @Override
    public String toString() {
        return "[Migraine] id: " + id
                + "\nNom: " + nom
                + "\nId_douleur: " + id_douleur
                + "\nDate: " + date
                + "\nHeure: " + heure
                + "\nDurée: " + duree
                + "\nCommentaire" + commentaire;
    }
}
