package org.suinot.migraine;

import android.view.ViewDebug;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by remi on 26/12/16.
 */

class Item_liste_d_evenement {
    String nom;
    String date_debut;
    String heure_debut;
    String nombre_medicaments;
    int image;

    // main constructor
    public Item_liste_d_evenement(String nom, String date, String heure, String medicament, int id_image) {
        super ();
        this.nom = nom;
        this.date_debut = date;
        this.heure_debut = heure;
        this.nombre_medicaments = medicament;
        this.image = id_image;
    }

    // String representation
    String _item_toString() {
        return this.nom + " - " + this.date_debut + " : " + this.heure_debut + " : " + this.nombre_medicaments + " - " + this.image;
    }
    // Map interface classes

    // return a count of our members
    int size() {
        return 5;
    }

    // set the values of the object to null
    void clear() {
        this.nom = null;
        this.date_debut = null;
        this.heure_debut = null;
        this.nombre_medicaments = null;
        this.image = 0;
    }

    // return all of the values as a collection
    ArrayList<String> values() {
        ArrayList<String> list = new ArrayList<String> ();

        list.add (nom);
        list.add (date_debut);
        list.add (heure_debut);
        list.add (nombre_medicaments);
        list.add (String.valueOf (image));
        return list;
    }

    // if the values of the members are null, return true
    boolean isEmpty() {
        if ( (this.nom == null ) && (this.date_debut == null) && (this.heure_debut == null) && (this.nombre_medicaments == null) && (this.image == 0) ) {
            return true;
        } else {
            return false;
        }
    }

    // return a set of the members
    Set<String> keySet() {
        Set<String> s = new HashSet<String> ();

        s.add ("nom");
        s.add ("date");
        s.add ("heure");
        s.add ("medicaments");
        s.add ("image");
        return s;
    }

    String get_item_Nom() {
        return nom;
    }

    void set_item_nom(String nom) {
        this.nom = nom;
    }

    String get_item_date_debut() { return date_debut; }

    void set_item_Date_debut(String date) {
        this.date_debut = date;
    }

    String get_item_heure_debut() {return heure_debut; }

    void set_item_Heure_debut(String heure) {
        this.heure_debut = heure;
    }

    String get_item_medicament() {
        return nombre_medicaments;
    }

    void set_item_medicament(String nombre) {
        this.nombre_medicaments = nombre;
    }

    int get_item_ImageId() {
        return image;
    }

    void set_item_ImageId(int imageId) {
        this.image = imageId;
    }

}

