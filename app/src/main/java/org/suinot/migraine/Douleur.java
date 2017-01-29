package org.suinot.migraine;


/**
 * Created by remi on 17/11/16.
 /**
 * base de données Douleur;
 * id
 * nomref  -> ref au nom unique de la migraine en cours
 * medicament -> médicament de la base du médic
 * intensite
 * duree
 * date
 * heure
  */

class Douleur {
    private int id_douleur;
    private int intensite;
    private String duree;
    private String date;
    private String heure;

    Douleur() {
    }

    Douleur ( int intensite, String duree, String date, String heure ) {
        super ();
        this.intensite = intensite;
        this.duree = duree;
        this.date = date;
        this.heure = heure;
    }

    /*
    ---------- Demande de données ----------
     */
    public int getId() {
        return id_douleur;
    }

    int getintensite_douleur() {
        return intensite;
    }

    String getduree_douleur() {
        return duree;
    }

    String getdate_douleur() {
        return date;
    }

    String getheure_douleur() {
        return heure;
    }

/*
---------- Mise à jour de données ----------
 */

    public void setId(int id) {
        this.id_douleur = id;
    }

    void setintensite_douleur(int intensite) { this.intensite = intensite; }

    void setduree_douleur(String duree) {
        this.duree = duree;
    }

    void setdate_douleur(String date) {
        this.date = date;
    }

    void setheure_douleur(String heure) {
        this.heure = heure;
    }

    /*
    ----------- Méthode globale ----------------
    */
    @Override
    public String toString() {
        return "[Migraine] id: " + id_douleur
                + "\nIntensite: " + intensite
                + "\nDurée: " + duree
                + "\nDate: " + date
                + "\nHeure: " + heure;
    }
}
