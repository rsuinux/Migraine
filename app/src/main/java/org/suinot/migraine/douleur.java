package org.suinot.migraine;

/**
 * Created by remi on 16/10/16.
 * base de données douleur de la migraine
 * id
 * id_ref  = id de la migraine de référence (permet de noter plusieurs douleurs pour une migraine de ref)
 * nom_ref   -> nom de la migraine
 * id_medicament -> id de la base du médicament
 * intensité
 * date
 * heure
 */

public class douleur {
    private int id;
    private int id_ref;
    private int nom_ref;
    private int id_medicament;
    private int intensite;
    private String date;
    private String heure;

    public douleur(int id_ref, int nom_ref, int id_medicament, int intensite, String date, String heure) {
        super ();
        this.id_ref = id_ref;
        this.nom_ref = nom_ref;
        this.id_medicament = id_medicament;
        this.intensite = intensite;
        this.date = date;
        this.heure = heure;
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

    public int getid_ref() {
        return id_ref;
    }

    public int getnom_ref() {
        return nom_ref;
    }

    public int getid_medicament() {
        return id_medicament;
    }

    public int getintensite() {
        return intensite;
    }

    public String getdate() {
        return date;
    }

/*
---------- Mise à jour de données ----------
 */

    public String getheure() {
        return heure;
    }

    public void setid_ref(int id_ref) {
        this.id_ref = id_ref;
    }

    public void setnom_ref(int nom_ref) {
        this.nom_ref = nom_ref;
    }

    public void setmedicament(int medicamnet) {
        this.id_medicament = medicamnet;
    }

    public void setIntensite(int intensite) {
        this.intensite = intensite;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public void setheure(String heure) {
        this.heure = heure;
    }

    /*
    ----------- Méthode globale ----------------
    */
    @Override
    public String toString() {
        return "[Migraine] id: " + id
                + "\nid ref: " + id_ref
                + "\nid_nom: " + nom_ref
                + "\nmedicament (id)" + id_medicament
                + "\nIntensité: " + intensite
                + "\nDate: " + date
                + "\nHeure: " + heure;
    }

}
