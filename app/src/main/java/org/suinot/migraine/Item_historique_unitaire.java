package org.suinot.migraine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by remi on 21/01/17.
 */

class Item_historique_unitaire {
    int valeur_douleur;
    String nom_medicament;
    String commentaire;

    // main constructor
    public Item_historique_unitaire(int douleur, String medicament, String commentaire) {
        super ();
        this.valeur_douleur = douleur;
        this.nom_medicament = medicament;
        this.commentaire = commentaire;
    }

    // String representation
    String _item_toString() {
        return this.valeur_douleur + " - " + this.nom_medicament + " : " + this.commentaire;
    }
    // Map interface classes

    // return a count of our members
    int size() {
        return 3;
    }

    // set the values of the object to null
    void clear() {
        this.valeur_douleur = 0;
        this.nom_medicament = null;
        this.commentaire = null;
    }

    // return all of the values as a collection
    ArrayList<?> values() {
        ArrayList<Object> list = new ArrayList<Object> ();

        list.add (valeur_douleur);
        list.add (nom_medicament);
        list.add (commentaire);
        return list;
    }

    // if the values of the members are null, return true
    boolean isEmpty() {
        if ( (this.valeur_douleur == 0 ) && (this.nom_medicament == null) && (this.commentaire == null) ) {
            return true;
        } else {
            return false;
        }
    }

    // return a set of the members
    Set<String> keySet() {
        Set<String> s = new HashSet<String> ();

        s.add ("douleur");
        s.add ("medicament");
        s.add ("commentaire");
        return s;
    }

    int get_valeur_douleur() {
        return valeur_douleur;
    }

    void set_valeur_douleur(int douleur) {
        this.valeur_douleur = douleur;
    }

    String get_nom_medicament() {
        return nom_medicament;
    }

    void set_nom_medicament(String medicament) {
        this.nom_medicament = medicament;
    }

    String get_item_commentaire() { return this.commentaire; }

    void set_item_commentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
