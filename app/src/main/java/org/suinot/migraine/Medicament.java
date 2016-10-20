package org.suinot.migraine;

/**
 * Created by remi on 01/09/16.
 */
class Medicament {

    private int id;
    private String medicament;
    private String dosage;

    Medicament() {
    }

    public Medicament(String nom, String dose) {
        super ();
        this.medicament = nom;
        this.dosage = dose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getMedicament() {
        return medicament;
    }

    void setMedicament(String nom) {
        this.medicament = nom;
    }

    String getDose() {
        return dosage;
    }

    void setDose(String dose) {
        this.dosage = dose;
    }

    @Override
    public String toString() {
        return "[Medicament] id: " + id + "\nNom: " + medicament + "\ndose: " + dosage;
    }
}
