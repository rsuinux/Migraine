package org.suinot.migraine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by remi on 16/10/16.
Description de mes données dans la base Migraine
 */

public class Item_Migraine {
    private String NM_Nom;
    private String NM_Date;
    private String NM_Heure;
    private String NM_Douleur;
    private String NM_Medic;

    // main constructor
    public Item_Migraine(String NM_Nom, String NM_Date, String NM_Heure, String NM_Douleur, String NM_Medic) {
        super ();
        this.NM_Nom = NM_Nom;
        this.NM_Date = NM_Date;
        this.NM_Heure = NM_Heure;
        this.NM_Douleur = NM_Douleur;
        this.NM_Medic = NM_Medic;
    }

    // String representation
    public String toString() {
        return this.NM_Nom + " : " + this.NM_Date + " : " + this.NM_Heure + " : " + this.NM_Douleur + " : " + this.NM_Medic;
    }
    // Map interface classes

    // return a count of our members
    public int size() {
        return 5;
    }

    // set the values of the object to null
    public void clear() {
        this.NM_Nom = "";
        this.NM_Date = "";
        this.NM_Heure = "";
        this.NM_Douleur = "";
        this.NM_Medic = "";
    }

    // return all of the values as a collection
    public ArrayList<String> values() {
        ArrayList<String> list = new ArrayList<String> ();

        list.add (NM_Nom);
        list.add (NM_Date);
        list.add (NM_Heure);
        list.add (NM_Douleur);
        list.add (NM_Medic);

        return list;
    }

    // if the values of the members are null, return true
    public boolean isEmpty() {
        if (this.NM_Medic.equals (""))
            if (this.NM_Douleur.equals (""))
                if (this.NM_Heure.equals (""))
                    if (this.NM_Date.equals ("")) {
                        if (this.NM_Nom.equals ("")) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    else {
                        return false;
                    }
                else {
                    return false;
                }
            else {
                return false;
            }
        else {
            return false;
        }
    }

    // return a set of the members
    public Set<String> keySet() {
        Set<String> s = new HashSet<String> ();

        s.add ("nom");
        s.add ("date");
        s.add ("heure");
        s.add ("douleur");
        s.add ("medicament");

        return s;
    }

    // return a set of the member values
    public Set entrySet() {
        Set<String> s = new HashSet<String> ();

        s.add (this.NM_Nom);
        s.add (this.NM_Date);
        s.add (this.NM_Heure);
        s.add (this.NM_Douleur);
        s.add (this.NM_Medic);

        return s;
    }

    // return the value of the given key
    public String get(Object key) {
        if (key.equals ("nom")) {
            return this.NM_Nom;
        }
        if (key.equals ("date")) {
            return this.NM_Date;
        }
        if (key.equals ("heure")) {
            return this.NM_Heure;
        }
        if (key.equals ("douleur")) {
            return this.NM_Douleur;
        }
        if (key.equals ("medicament")) {
            return this.NM_Medic;
        }
        // if we can't return a value, throw the exception
        throw new ClassCastException ();
    }

    // set the value of a given key
    public String put(String key, String value) {
        if (key.equals ("nom")) {
            this.NM_Nom = value;
        }
        if (key.equals ("date")) {
            this.NM_Date = value;
        }
        if (key.equals ("heure")) {
            this.NM_Heure = value;
        }
        if (key.equals ("douleur")) {
            this.NM_Douleur = value;
        }
        if (key.equals ("medicament")) {
            this.NM_Medic = value;
        }
        return value;
    }

    // remove a key (nullify)
    public String remove(Object key) {
        String value = null;
        if (key.equals ("nom")) {
            value = this.NM_Nom;
            this.NM_Nom = "";
        }
        if (key.equals ("date")) {
            value = this.NM_Date;
            this.NM_Date = "";
        }
        if (key.equals ("heure")) {
            value = this.NM_Heure;
            this.NM_Heure = "";
        }
        if (key.equals ("douleur")) {
            value = this.NM_Douleur;
            this.NM_Douleur = "";
        }
        if (key.equals ("medicament")) {
            value = this.NM_Medic;
            this.NM_Medic = "";
        }
        return value;
    }

    // return boolean if we have a member
    public boolean containsKey(Object key) {
        if (key.equals ("nom")) {
            return true;
        }else if (key.equals ("date")) {
            return true;
        }else if (key.equals ("heure")) {
            return true;
        } else if (key.equals ("douleur")) {
            return true;
        }else if (key.equals ("medicament")) {
            return true;
        }
        return false;
    }

    // return boolean if we have a member's value
    public boolean containsValue(Object value) {
        if (value.equals (this.NM_Nom)) {
            return true;
        } else if (value.equals (this.NM_Date)) {
            return true;
        } else if (value.equals (this.NM_Heure)) {
            return true;
        } else if (value.equals (this.NM_Douleur)) {
            return true;
        } else if (value.equals (this.NM_Medic)) {
            return true;
        }
        return false;
    }

    // set the values of this map to that of another
    public void putAll(Map<? extends String, ? extends String> arg0) {
        // we only need the stub.
    }


}
