package org.suinot.migraine;

import android.util.Log;

/**
 * Created by remi on 15/10/16.
 * <p>
 * base de données Migraine
 */

class Migraine {

    private int id_migraine;
    private String nom;
    private String date;
    private String heure;
    private String duree;
    private String commentaire;
    private int etat;  // 0 = base/index vide. 1 = migraine en cours / 2 = migraine terminée

    Migraine() {
    }

    public Migraine (String nom, String date, String heure, String duree, String comm, int etat){
        super ();
        this.nom = nom;
        this.date = date;
        this.heure = heure;
        this.duree = duree;
        this.commentaire = comm;
        this.etat = etat;
    }

    /*
    ---------- Demande de données ----------
     */

    public int getId() {
        return id_migraine;
    }

    String getnom_migraine() {
        return nom;
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

    int getetat () { return etat; }

    /*
      ---------- Mise à jour de données ----------
    */

    public void setId(int id) {
        this.id_migraine = id;
    }

    void setnom_migraine(String nom) { this.nom = nom; }

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

    void setetat (int etat) { this.etat=etat;}
    /*
    ----------- Méthode globale ----------------
    */
    @Override
    public String toString() {
        return "[Migraine] id: " + id_migraine
                + "\nNom: " + nom
                + "\nDate: " + date
                + "\nHeure: " + heure
                + "\nDurée: " + duree
                + "\nCommentaire" + commentaire
                + "\nEtat: " + etat;
    }
}
