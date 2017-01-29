package org.suinot.migraine;

/**
 * Created by remi on 02/12/16.
 * Gestion de la table croisée des données douleurs/migraines
 */

public class croisee {
    private int id_croisee;
    private long croisee_douleur;
    private long croisee_migraine;
    private long croisee_medicament;

    croisee() {
    }

    croisee (int douleur, int migraine, int medicament ) {
        super ();
        this.croisee_douleur = douleur;
        this.croisee_migraine = migraine;
        this.croisee_medicament = medicament;
    }

    /*
    ---------- Demande de données ----------
     */
    public int get_Id_croisee() {
        return id_croisee;
    }

    long get_douleur_croisee() {
        return croisee_douleur;
    }

    long get_migraine_croisee() {
        return croisee_migraine;
    }

    long get_medicament_croisee() {
        return croisee_medicament;
    }

    /*
---------- Mise à jour de données ----------
 */

    public void set_Id_croisee(int id) {
        this.id_croisee = id;
    }

    void set_douleur_croisee(long douleur) {
        this.croisee_douleur = douleur;
    }

    void set_migraine_croisee(long migraine) { this.croisee_migraine = migraine; }

    void set_medicament_croisee(long medicament) { this.croisee_medicament = medicament; }

    /*
    ----------- Méthode globale ----------------
    */
    @Override
    public String toString() {
        return "[Croisée] id: " + id_croisee
                + "\nDouleur: " + croisee_douleur
                + "\nMigraine: " + croisee_migraine
                + "\nMedicament: " + croisee_medicament;
    }
}
