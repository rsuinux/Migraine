package org.suinot.migraine;

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
    private String date;
    private String heure;
    private String duree;
    private String commentaire;

    Migraine() {
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

    public void setId(int id) {
        this.id = id;
    }

    String getnom() {
        return nom;
    }

    String getdate() {
        return date;
    }

    String getheure() {
        return heure;
    }

    String getduree() {
        return duree;
    }
/*
---------- Mise à jour de données ----------
 */

    String getcommentaire() {
        return commentaire;
    }

    void setnom(String nom) {
        this.nom = nom;
    }

    void setdate(String date) {
        this.date = date;
    }

    void setheure(String heure) {
        this.heure = heure;
    }

    void setduree(String duree) {
        this.duree = duree;
    }

    void setcommentaire(String com) {
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
