package org.suinot.migraine;

/**
 * Created by remi on 02/12/16.
 * Gestion de la table croisée des données douleurs/migraines
 */

public class croisee {
    private int id_croisee;
    private long do_croisee_douleur;
    private long do_croisee_migraine;

    croisee() {
    }

    croisee (long douleur, int migraine) {
        super ();
        this.do_croisee_douleur = douleur;
        this.do_croisee_migraine = migraine;
    }

    /*
    ---------- Demande de données ----------
     */
    public int get_Id_croisee() {
        return id_croisee;
    }

    long get_douleur_croisee() {
        return do_croisee_douleur;
    }

    long get_migraine_croisee() {
        return do_croisee_migraine;
    }

    /*
---------- Mise à jour de données ----------
 */

    public void set_Id_croisee(int id) {
        this.id_croisee = id;
    }

    void set_douleur_croisee(long douleur) {
        this.do_croisee_douleur = douleur;
    }

    void set_migraine_croisee(long migraine) { this.do_croisee_migraine = migraine; }

    /*
    ----------- Méthode globale ----------------
    */
    @Override
    public String toString() {
        return "[Croisée] id: " + id_croisee
                + "\nDouleur: " + do_croisee_douleur
                + "\nMigraine: " + do_croisee_migraine;
    }

}
