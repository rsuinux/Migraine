package org.suinot.migraine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by remi on 01/09/16.
   Gestion de la base de données des médicaments géré par l'utilisateur
 */

class GestionBaseMedicament implements Constantes.constantes {

    private MabaseMedicament MaBase;
    private SQLiteDatabase bdd;

    GestionBaseMedicament(Context context) {
        //On crée la BDD et sa table
        MaBase = MabaseMedicament.getInstance (context);
    }

    void open() throws SQLiteException {
        //on ouvre la BDD en écriture
        try {
            bdd = MaBase.getWritableDatabase ();
        }    catch (SQLiteException ex) {
            bdd = MaBase.getReadableDatabase ();
        }
    }

    void close() {
        //on ferme l'accès à la BDD
        bdd.close ();
    }

//    public SQLiteDatabase getMaBase() {
//Log.d ("getMaBase()", "renvoi la bdd");
//        return MaBase;
//    }

    /**
     * Insère un médicament en base de données
     *
     * @param medic le médic à insérer
     * @return l'identifiant de la ligne insérée
     */
    long insertMedicament(Medicament medic) {
        long i;
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues ();
        // on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put (COL_MEDIC_NOM, medic.getMedicament ());
        values.put (COL_MEDIC_DOSE, medic.getDose ());
        values.put (COL_MEDIC_INVALIDE, medic.getInvalide ());
        i = bdd.insert (TABLE_MEDIC, null, values);
        return i;
    }

    /**
     * Met à jour le médicament en base de données
     *
     * @param id    l'identifiant du médicament à modifier
     * @param medic le nouveau médicament à associer à l'identifiant
     * @return le nombre de lignes modifiées
     */
    public long updateMedicament(int id, Medicament medic) {
        // à utiliser en cas d'appui court?
        long i;
        ContentValues values = new ContentValues ();
        values.put (COL_MEDIC_NOM, medic.getMedicament ());
        values.put (COL_MEDIC_DOSE, medic.getDose ());
        values.put (COL_MEDIC_INVALIDE, medic.getInvalide ());
        i = bdd.update (TABLE_MEDIC, values, COL_MEDIC_ID + " = " + id, null);
        return i;
    }


    /**
     * Supprime un médicament de la BDD (celui dont l'identifiant est passé en
     * paramètres)
     *
     * @param id l'identifiant du medic
     * @return le nombre de medic supprimés
     */
    long removeMedicamentWithID(long id) {
        //Suppression d'un médicament de la BDD grâce à l'ID avec un appui long
        long i;
        i = bdd.delete (TABLE_MEDIC, COL_MEDIC_ID + " = " + id, null);
        return i;
    }

    /**
     * Retourne le médicament qui correspond à celui en paramètre
     *
     * @param id l'identifiant du médic
     * @return le medic récupéré depuis la base de données
     */
    public String getMedicamentWithId(int id) {
        //Récupère dans un Cursor les valeurs correspondants à un medicament contenu dans la BDD
        // (ici on sélectionne le medicament grâce à son id)
        String[] clauseSelect = new String[]{" * "};
        String clauseOu = COL_MEDIC_ID + " = ? ";
        String argsOu = Integer.toString (id);

        Cursor c = bdd.query (TABLE_MEDIC, clauseSelect, clauseOu, new String[]{argsOu}, null, null, null);
        if ( c.getCount () == 0 )
            return "";
        Medicament m=cursorToMedicament(c);
        return m.getMedicament ();
    }

    /**
     * Retourne le médicament qui correspond à celui en paramètre
     *
     * @param nom le nom du médic
     *            dose
     *            la dose du médic
     * @return le medic récupéré depuis la base de données
     */
    Medicament getMedicamentWithNom(String nom, String dose) {
        //Récupère dans un Cursor les valeurs correspondants à un medicament contenu dans la BDD
        // (ici on sélectionne le medicament grâce à son nom et sa dose)

        String[] clauseSelect = new String[]{" * "};
        String clauseOu = COL_MEDIC_NOM + " = ? AND " + COL_MEDIC_DOSE + " = ? ";
        String[] argsOu = new String[]{nom, dose};
        String orderBy = "";

        Cursor c = bdd.query (TABLE_MEDIC, clauseSelect, clauseOu, argsOu, null, null, orderBy);

        return cursorToMedicament (c);
    }

    /**
     * Convertit le cursor en médicament
     *
     * @param c le cursor à convertir
     * @return le médic créé à partir du Cursor
     */
    private Medicament cursorToMedicament(Cursor c) {
        //Cette méthode permet de convertir un cursor en un medicament
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount () == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst ();

        //On créé un medicament
        Medicament medic = new Medicament ();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        medic.setId (c.getInt (NUM_COL_MEDIC_ID));
        medic.setDose (c.getString (NUM_COL_MEDIC_DOSE));
        medic.setMedicament (c.getString (NUM_COL_MEDIC_NOM));
        medic.setInvalide (c.getInt (NUM_COL_MEDIC_NOM));
        //On ferme le cursor
        c.close ();

        //On retourne le médicament
        return medic;
    }

    // doit renvoyer le dernier element de la base
    int NombreMedicament() {
        String[] clauseSelect = new String[]{" * "};
        //String clauseOu = null;
        //String[] argsOu = null;
        //String orderBy = null;

        Cursor c = bdd.query (TABLE_MEDIC, clauseSelect, null, null, null, null, null);

        if (c.getCount () == 0)
            return 0;

        c.moveToLast ();
        //On créé un medicament
        Medicament medic = new Medicament ();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        medic.setId (c.getInt (NUM_COL_MEDIC_ID));
        medic.setDose (c.getString (NUM_COL_MEDIC_DOSE));
        medic.setMedicament (c.getString (NUM_COL_MEDIC_NOM));
        medic.setInvalide (c.getInt (NUM_COL_MEDIC_INVALIDE));
        //On ferme le cursor
        c.close ();
        //On retourne le médicament
        return medic.getId ();
    }

    //retourne tous les médicaments de la bdd dans un arraylist pour afficher le listview
    ArrayList<Item_Medicament> getAllMedicaments() {
        long i;
        ArrayList<Item_Medicament> medicList = new ArrayList<> ();
        String[] clauseSelect = new String[]{" * "};
        //String clauseOu = null;
        //String[] argsOu = null;
        //String orderBy = null;
//        Cursor c = bdd.query (TABLE_MEDIC, clauseSelect, clauseOu, argsOu, null, null, orderBy);

        Cursor c = bdd.query (TABLE_MEDIC, clauseSelect, null, null, null, null, null);
        if (c.moveToFirst ()) {
            do {
                if ( c.getInt (NUM_COL_MEDIC_INVALIDE) == 0 ) {
                    Item_Medicament item1 = new Item_Medicament (c.getString (NUM_COL_MEDIC_NOM), c.getString (NUM_COL_MEDIC_DOSE), c.getInt (NUM_COL_MEDIC_INVALIDE));
                    medicList.add (item1);
                }
            } while (c.moveToNext ());
        }
        c.close ();
        return medicList;
    }

    // retoure en "list" tous les médicaments de la base de données
    List<String> getAllLabels() {
        List<String> labels = new ArrayList<String> ();

        String[] clauseSelect = new String[]{" * "};
        //String clauseOu = null;
        //String[] argsOu = null;
        //String orderBy = null;
//        Cursor c = bdd.query (TABLE_MEDIC, clauseSelect, clauseOu, argsOu, null, null, orderBy);

        Cursor c = bdd.query (TABLE_MEDIC, clauseSelect, null, null, null, null, null);
        if (c.moveToFirst ()) {
            do {
                if ( c.getString (NUM_COL_MEDIC_INVALIDE).equals ("0")) {
                    labels.add (c.getString (1) + " - " + c.getString (2));
                }
            } while (c.moveToNext ());
        }
        c.close ();
        return labels;
    }

}