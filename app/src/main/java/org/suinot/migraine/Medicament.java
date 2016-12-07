package org.suinot.migraine;

/**
 * Created by remi on 01/09/16.
 */
class Medicament {

    private int id;
    private String medicament;
    private String dosage;
    private int invalide;

    Medicament() {
    }

    public Medicament(String nom, String dose, int invalide) {
        super ();
        this.medicament = nom;
        this.dosage = dose;
        this.invalide = invalide;
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

    String getMedicament() {
        return medicament;
    }

    String getDose() {
        return dosage;
    }

    int getInvalide() {
        return invalide;
    }

    /*
    ---------- Demande de données ----------
    */

    void setMedicament(String nom) {
        this.medicament = nom;
    }

    void setDose(String dose) { this.dosage = dose; }

    void setInvalide(int invalide) { this.invalide = invalide; }

    /*
    ---------- Demande de données ----------
    */

    @Override
    public String toString() {
        return "[Medicament] id: " + id + "\nNom: " + medicament + "\ndose: " + dosage + "\nInvalide: " + invalide;
    }
}
