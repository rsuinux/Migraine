package org.suinot.migraine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by remi on 16/10/16.
 * Description de mes données pour la gestion de la listView migraine
 * Rappel: 2 lignes ->
 *   ligne1 = Nom - date - heure
 *   ligne2 = douleur - médicament - dose
 */

class Item_Migraine {
    public String NM_Nom;
    public String NM_Date;
    public String NM_Heure;
    public String NM_Douleur;
    public String NM_Medic;
    public String NM_Dose;

    // main constructor
    public Item_Migraine(String NM_Nom, String NM_Date, String NM_Heure, String NM_Douleur, String NM_Medic, String NM_Dose) {
        super ();
        this.NM_Nom = NM_Nom;
        this.NM_Date = NM_Date;
        this.NM_Heure = NM_Heure;
        this.NM_Douleur = NM_Douleur;
        this.NM_Medic = NM_Medic;
        this.NM_Dose = NM_Dose;
    }

    // String representation
    public String toString() {
        return this.NM_Nom + " : " + this.NM_Date + " : " + this.NM_Heure + " : " + this.NM_Douleur + " : " + this.NM_Medic + " : " + this.NM_Dose;
    }
    // Map interface classes

    // return a count of our members
    public int size() {
        return 6;
    }

    // set the values of the object to null
    public void clear() {
        this.NM_Nom = "";
        this.NM_Date = "";
        this.NM_Heure = "";
        this.NM_Douleur = "";
        this.NM_Medic = "";
        this.NM_Dose = "";
    }

    // return all of the values as a collection
    public ArrayList<String> values() {
        ArrayList<String> list = new ArrayList<String> ();

        list.add (NM_Nom);
        list.add (NM_Date);
        list.add (NM_Heure);
        list.add (NM_Douleur);
        list.add (NM_Medic);
        list.add (NM_Dose);

        return list;
    }

    // if the values of the members are null, return true
    public boolean isEmpty() {
        if (this.NM_Nom.equals ("")) {
            if (this.NM_Date.equals ("")) {
                if (this.NM_Heure.equals ("")) {
                    if (this.NM_Douleur.equals ("")) {
                        if (this.NM_Medic.equals ("")) {
                            if (this.NM_Dose.equals ("")) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // return a set of the members
    public Set<String> keySet() {
        Set<String> s = new HashSet<String> ();

        s.add ("nom");
        s.add ("date");
        s.add ("heure");
        s.add ("douleur");
        s.add ("medicament");
        s.add ("dose");

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
        s.add (this.NM_Dose);

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
        if (key.equals ("dose")) {
            return this.NM_Dose;
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
         if (key.equals ("dose")) {
            this.NM_Dose = value;
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
        if (key.equals ("dose")) {
            value = this.NM_Dose;
            this.NM_Dose = "";
        }
        return value;
    }

    // return boolean if we have a member
    public boolean containsKey(Object key) {
        if (key.equals ("nom")) {
            return true;
        } else if (key.equals ("date")) {
            return true;
        } else if (key.equals ("heure")) {
            return true;
        } else if (key.equals ("douleur")) {
            return true;
        } else if (key.equals ("medicament")) {
            return true;
        } else if (key.equals ("dose")) {
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
        } else if (value.equals (this.NM_Dose)) {
            return true;
        }
        return false;
    }

    // set the values of this map to that of another
    public void putAll(Map<? extends String, ? extends String> arg0) {
        // we only need the stub.
    }

}
