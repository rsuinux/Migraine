package org.suinot.migraine;

/**
 * Created by remi on 15/10/16.

 base de données Migraine
    id
    nom -> unique ou non, si pas de nom, alors app le donne?
    date
    heure
    duree
    commentaire(s)

 */

public class Migraine {

    private int id;
    private String nom;
    private String date;
    private String heure;
    private String duree;
    private String commentaire;

    public Migraine() {
        super ();
        this.nom = nom;
        this.date = date;
        this.heure = heure;
        this.duree = duree;
        this.commentaire = commentaire;
    }
/*
---------- Demande de données ----------
 */
    public int getId() {
        return id;
    }

    public String getnom() {
        return nom;
    }

    public String getdate() {
        return date;
    }

    public String getheure() {
        return heure;
    }

    public String getduree() {
        return duree ;
    }

    public String getcommentaire() {
        return commentaire;
    }
/*
---------- Mise à jour de données ----------
 */

    public void setId(int id) {
        this.id = id;
    }

    public void setnom(String nom) {
        this.nom = nom;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public void setheure(String heure) {
        this.heure = heure;
    }

    public void setduree(String duree) {
        this.duree = duree;
    }

    public void setcommentaire(String com) {
        this.commentaire = com;
    }

/*
----------- Méthode globale ----------------
*/
    @Override
    public String toString() {
        return "[Migraine] id: " + id
                + "\nNom: " + nom
                + "\nDate: " + date
                + "\nHeure: " + heure
                + "\nDurée: " + duree
                + "\nCommentaire" + commentaire;
    }

}
