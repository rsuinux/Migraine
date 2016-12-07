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
    private long do_medic;
    private int do_intensite;
    private String do_duree;
    private String do_date;
    private String do_heure;

    Douleur() {
    }

    Douleur (long medic, int intensite, String duree, String date, String heure ) {
        super ();
        this.do_medic = medic;
        this.do_intensite = intensite;
        this.do_duree = duree;
        this.do_date = date;
        this.do_heure = heure;
    }

    /*
    ---------- Demande de données ----------
     */
    public int getId() {
        return id_douleur;
    }

    long getmedic() {
        return do_medic;
    }

    int getintensite_douleur() {
        return do_intensite;
    }

    String getduree_douleur() {
        return do_duree;
    }

    String getdate_douleur() {
        return do_date;
    }

    String getheure_douleur() {
        return do_heure;
    }

/*
---------- Mise à jour de données ----------
 */

    public void setId(int id) {
        this.id_douleur = id;
    }

    void setmedic(long medic) {
        this.do_medic = medic;
    }

    void setintensite_douleur(int intensite) { this.do_intensite = intensite; }

    void setduree_douleur(String duree) {
        this.do_duree = duree;
    }

    void setdate_douleur(String date) {
        this.do_date = date;
    }

    void setheure_douleur(String heure) {
        this.do_heure = heure;
    }

    /*
    ----------- Méthode globale ----------------
    */
    @Override
    public String toString() {
        return "[Migraine] id: " + id_douleur
                + "\nMedic: " + do_medic
                + "\nIntensite: " + do_intensite
                + "\nDurée: " + do_duree
                + "\nDate: " + do_date
                + "\nHeure: " + do_heure;
    }
}
