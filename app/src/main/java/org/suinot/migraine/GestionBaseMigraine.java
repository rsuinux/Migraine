package org.suinot.migraine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by remi on 15/10/16.
 * Méthodes pour toute ma gestion de la base de données Migraine
 */

class GestionBaseMigraine {

    private MabaseMigraine MABASEMIGRAINE;
    private SQLiteDatabase DBMIGRAINE;

    /* Définition de la table Migraine */
    private static final String TABLE_MIGRAINES = "table_migraines";
    private static final String COL_ID = "ID";
    private static final int MIGRAINE_COL_ID = 0;
    private static final String COL_NOM = "NOM";
    private static final int MIGRAINE_COL_NOM = 1;
    private static final String COL_ID_DOULEUR = "DOULEUR";
    private static final int MIGRAINE_COL_IDDOULEUR = 2;
    private static final String COL_DATE = "DATE";
    private static final int MIGRAINE_COL_DATE = 3;
    private static final String COL_HEURE = "HEURE";
    private static final int MIGRAINE_COL_HEURE = 4;
    private static final String COL_DUREE = "DUREE";
    private static final int MIGRAINE_COL_DUREE = 5;
    private static final String COL_COMMENTAIRE = "COMMENTAIRE";
    private static final int MIGRAINE_COL_COMMENTAIRE = 6;

    /* Définition de la table Douleur */
    private static final String TABLE_DOULEURS = "douleurs";
    private static final String COL_DOULEUR_ID = "ID";
    private static final int DOULEUR_COL_ID = 0;
    private static final String COL_DOULEUR_NOMREF = "NOM_REF";
    private static final int DOULEUR_COL_NOMREF = 1;
    private static final String COL_ID_MEDICAMENT = "MEDICAMENT";
    private static final int DOULEUR_COL_ID_MEDICAMENT = 2;
    private static final String COL_INTENSITE = "INTENSITE";
    private static final int DOULEUR_COL_INTENSITE = 3;
    private static final String COL_DOULEUR_DUREE = "DUREE";
    private static final int DOULEUR_COL_DUREE = 4;
    private static final String COL_DOULEUR_DATE = "DATE";
    private static final int DOULEUR_COL_DATE = 5;
    private static final String COL_DOULEUR_HEURE = "HEURE";
    private static final int DOULEUR_COL_HEURE = 6;

/*---------------------------------------------------------------------*/

    GestionBaseMigraine(Context context) {
        //On crée la BDD et sa table
        Log.d ("GestionBaseMedicament()", "création de la base");
        MabaseMigraine.getInstance (context);
        MABASEMIGRAINE = MabaseMigraine.getInstance (context);
    }

/*---------------------------------------------------------------------*/

     void open() {
        //on ouvre la BDD en écriture
        Log.d ("open()", "ouverture de la base");
        DBMIGRAINE = MABASEMIGRAINE.getWritableDatabase ();
    }

     void close() {
        //on ferme l'accès à la BDD
        Log.d ("GestionBaseMedicament()", "close base");
        DBMIGRAINE.close ();
    }

/*-----------------------------------------------------------------------------------------------------------------*/
    /**
     * Insère une migraine en base de données
     *
     * @param m : la migraine à insérer
     * @return l'identifiant de la ligne insérée
     */
    long insertMigraine(Migraine m) {
        long i;
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues ();
        // on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put (COL_NOM, m.getnom_migraine ());
        values.put (COL_ID_DOULEUR, m.getdouleur_migraine ());
        values.put (COL_DATE, m.getdate_migraine ());
        values.put (COL_HEURE, m.getheure_migraine ());
        values.put (COL_DUREE, m.getduree_migraine ());
        values.put (COL_COMMENTAIRE, m.getcommentaire ());

        i = DBMIGRAINE.insert (TABLE_MIGRAINES, null, values);
        Log.d ("insertMigraine", "Retour de la base: " + i);
        return i;
    }

    /**
     * Insère une douleur en base de données
     *
     * @param m : la douleur à insérer
     * @return l'identifiant de la ligne insérée
     */
    long insertDouleur(Douleur m) {
        long i;
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues ();
        // on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put (COL_DOULEUR_NOMREF, m.getnomref ());
        values.put (COL_ID_MEDICAMENT, m.getmedic ());
        values.put (COL_INTENSITE, m.getintensite_douleur ());
        values.put (COL_DOULEUR_DUREE, m.getduree_douleur ());
        values.put (COL_DOULEUR_DATE, m.getdate_douleur ());
        values.put (COL_DOULEUR_HEURE, m.getheure_douleur ());

        i = DBMIGRAINE.insert (TABLE_DOULEURS, null, values);
        Log.d ("insertdouleur", "Retour de la base: " + i);
        return i;
    }

/*-----------------------------------------------------------------------------------------------------------------*/

    /**
     * Met à jour la table migraine en base de données
     *
     * @param id l'identifiant du médicament à modifier
     * @param m: la nouvelle migraine à associer à l'identifiant
     * @return le nombre de lignes modifiées
     */
    long updateMigraine(int id, Migraine m) {

        long i;
        ContentValues values = new ContentValues ();
        values.put (COL_NOM, m.getnom_migraine ());
        values.put (COL_ID_DOULEUR, m.getdouleur_migraine ());
        values.put (COL_DATE, m.getdate_migraine ());
        values.put (COL_HEURE, m.getheure_migraine ());
        values.put (COL_DUREE, m.getduree_migraine ());
        values.put (COL_COMMENTAIRE, m.getcommentaire ());
        i = DBMIGRAINE.update (TABLE_MIGRAINES, values, COL_ID + " = " + id, null);
        return i;
    }


    /**
     * Met à jour de la table douleur en base de données
     *
     * @param id l'identifiant de la douleur à modifier
     * @param m: la nouvelle douleur à associer à l'identifiant
     * @return le nombre de lignes modifiées
     */
    long updateDouleur(int id, Douleur m) {

        long i;
        ContentValues values = new ContentValues ();
        values.put (COL_DOULEUR_NOMREF, m.getnomref ());
        values.put (COL_ID_MEDICAMENT, m.getmedic ());
        values.put (COL_INTENSITE, m.getintensite_douleur ());
        values.put (COL_DOULEUR_DUREE, m.getduree_douleur ());
        values.put (COL_DOULEUR_DATE, m.getdate_douleur ());
        values.put (COL_HEURE, m.getheure_douleur ());
        i = DBMIGRAINE.update (TABLE_DOULEURS, values, COL_DOULEUR_ID + " = " + id, null);
        return i;
    }

/*-----------------------------------------------------------------------------------------------------------------*/

    /**
     * Supprime une migraine de la BDD (celle dont l'identifiant est passé en
     * paramètres)
     *
     * @param id l'identifiant de la migraine
     * @return le nombre d'élément(s) supprimé(s)
     */
     long removeMigraineWithID(long id) {
        long i;
        i = DBMIGRAINE.delete (TABLE_MIGRAINES, COL_ID + " = " + id, null);
        return i;
    }

    /**
     * Supprime une douleur de la BDD (celle dont l'identifiant est passé en
     * paramètres)
     *
     * @param id l'identifiant de la douleur
     * @return le nombre d'élément(s) supprimé(s)
     */
    long removeDouleurWithID(long id) {
        long i;
        i = DBMIGRAINE.delete (TABLE_DOULEURS, COL_DOULEUR_ID + " = " + id, null);
        return i;
    }

/*-----------------------------------------------------------------------------------------------------------------*/

    /**
     * Retourne la migraine dont le numéro correspond à
     * celle en paramètre
     *
     * @param id l'identifiant de la migraine
     * @return la migraine récupérée depuis la base de données
     */
    Migraine getMigraineWithId(int id) {
        //Récupère dans un Cursor les valeurs correspondants à une migraine contenu dans la BDD
        // (ici on sélectionne la migraine grâce à son id)
        String[] clauseSelect = new String[]{" * "};
        String clauseOu = COL_ID + " = ? ";
        String argsOu = Integer.toString (id);
        String orderBy = "";

        Cursor c = DBMIGRAINE.query (TABLE_MIGRAINES, clauseSelect, clauseOu, new String[]{argsOu}, null, null, orderBy);
        // Cursor c=bdd.query(TABLE_MEDIC, new String[]{COL_ID, COL_NOM, COL_DOSE }, null, null, COL_ID + " = " + id, null, null);
        return cursorToMigraine (c);
    }

    /**
     * Retourne la douleur dont le numéro correspond à
     * celle en paramètre
     *
     * @param id l'identifiant de la douleur
     * @return la douleur récupérée depuis la base de données
     */
    Douleur getDouleurWithId(int id) {
        //Récupère dans un Cursor les valeurs correspondants à une douleur contenu dans la BDD
        // (ici on sélectionne la migraine grâce à son id)
        String[] clauseSelect = new String[]{" * "};
        String clauseOu = COL_DOULEUR_ID + " = ? ";
        String argsOu = Integer.toString (id);
        String orderBy = "";

        Cursor c = DBMIGRAINE.query (TABLE_DOULEURS, clauseSelect, clauseOu, new String[]{argsOu}, null, null, orderBy);
        // Cursor c=bdd.query(TABLE_MEDIC, new String[]{COL_ID, COL_NOM, COL_DOSE }, null, null, COL_ID + " = " + id, null, null);
        return cursorToDouleur (c);
    }
/*-----------------------------------------------------------------------------------------------------------------*/

    /**
     * Retourne la migraine dont le numéro correspond à
     * celui en paramètre
     *
     * @param nom le nom de la migraine
     *            dose
     *            la dose du médic
     * @return la migraine récupérée depuis la base de données
     */
    Migraine getMigraineWithNom(String nom) {
        //Récupère dans un Cursor les valeurs correspondants à un medicament contenu dans la BDD
        // (ici on sélectionne le medicament grâce à son nom et sa dose)

        String[] clauseSelect = new String[]{" * "};
        String clauseOu = COL_NOM + " = ? ";
        String[] argsOu = new String[]{nom};
        String orderBy = "";

        Cursor c = DBMIGRAINE.query (TABLE_MIGRAINES, clauseSelect, clauseOu, argsOu, null, null, orderBy);

        return cursorToMigraine (c);
    }

    /**
     * Retourne la douleur dont le numéro correspond à
     * celui en paramètre
     *
     * @param nom le nom de la migraine correspondnat à la douleur (nomref)
     *
     * @return la douleur récupérée depuis la base de données
     */
    Douleur getDouleurWithNom(String nom) {
        //Récupère dans un Cursor les valeurs correspondants à un medicament contenu dans la BDD
        // (ici on sélectionne le medicament grâce à son nom et sa dose)

        String[] clauseSelect = new String[]{" * "};
        String clauseOu = DOULEUR_COL_NOMREF + " = ? ";
        String[] argsOu = new String[]{nom};
        String orderBy = "";

        Cursor c = DBMIGRAINE.query (TABLE_DOULEURS, clauseSelect, clauseOu, argsOu, null, null, orderBy);

        return cursorToDouleur (c);
    }

/*-----------------------------------------------------------------------------------------------------------------*/

    /**
     * Convertit le cursor en migraine
     *
     * @param c le cursor à convertir
     * @return le médic créé à partir du Cursor
     */
    private Migraine cursorToMigraine(Cursor c) {
        //Cette méthode permet de convertir un cursor en une migraine
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount () == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst ();

        //On créé une migraine
        Migraine m = new Migraine ();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        m.setId (c.getInt (MIGRAINE_COL_ID));
        m.setnom_migraine (c.getString (MIGRAINE_COL_NOM));
        m.setdouleur_migraine (c.getInt (MIGRAINE_COL_IDDOULEUR));
        m.setdate_migraine (c.getString (MIGRAINE_COL_DATE));
        m.setheure_migraine (c.getString (MIGRAINE_COL_HEURE));
        m.setduree_migraine (c.getString (MIGRAINE_COL_DUREE));
        m.setcommentaire (c.getString (MIGRAINE_COL_COMMENTAIRE));
        //On ferme le cursor
        c.close ();

        //On retourne la migraine
        return m;
    }

    /**
     * Convertit le cursor en douleur
     *
     * @param c le cursor à convertir
     * @return la douleur créé à partir du Cursor
     */
    private Douleur cursorToDouleur(Cursor c) {
        //Cette méthode permet de convertir un cursor en une douleur
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount () == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst ();

        //On créé une douleur
        Douleur m = new Douleur ();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        m.setId (c.getInt (DOULEUR_COL_ID));
        m.setmedic (c.getString (DOULEUR_COL_ID_MEDICAMENT));
        m.setintensite_douleur (c.getInt (DOULEUR_COL_INTENSITE));
        m.setduree_douleur (c.getString (DOULEUR_COL_DUREE));
        m.setdate_douleur (c.getString (DOULEUR_COL_DATE));
        m.setheure_douleur (c.getString (DOULEUR_COL_HEURE));
        //On ferme le cursor
        c.close ();

        //On retourne le médicament
        return m;
    }

/*-----------------------------------------------------------------------------------------------------------------*/

    // doit renvoyer le dernier element de la base
    int NombreMigraine() {
        String[] clauseSelect = new String[]{" * "};
        //String clauseOu = null;
        //String[] argsOu = null;
        //String orderBy = null;

        Cursor c = DBMIGRAINE.query (TABLE_MIGRAINES, clauseSelect, null, null, null, null, null);

        if (c.getCount () == 0)
            return 0;

        c.moveToLast ();
        //On créé une migraine
        Migraine m = new Migraine ();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        m.setId (c.getInt (MIGRAINE_COL_ID));
        m.setnom_migraine (c.getString (MIGRAINE_COL_NOM));
        m.setdouleur_migraine (c.getInt (MIGRAINE_COL_IDDOULEUR));
        m.setdate_migraine (c.getString (MIGRAINE_COL_DATE));
        m.setheure_migraine (c.getString (MIGRAINE_COL_HEURE));
        m.setduree_migraine (c.getString (MIGRAINE_COL_DUREE));
        m.setcommentaire (c.getString (MIGRAINE_COL_COMMENTAIRE));
        //On ferme le cursor
        c.close ();
        //On retourne la migraine
        return m.getId ();
    }

    // doit renvoyer le dernier element de la base
    int NombreDouleur() {
        String[] clauseSelect = new String[]{" * "};
        //String clauseOu = null;
        //String[] argsOu = null;
        //String orderBy = null;

        Cursor c = DBMIGRAINE.query (TABLE_DOULEURS, clauseSelect, null, null, null, null, null);

        if (c.getCount () == 0)
            return 0;

        c.moveToLast ();
        //On créé un medicament
        Douleur m = new Douleur ();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        m.setId (c.getInt (DOULEUR_COL_ID));
        m.setmedic (c.getString (DOULEUR_COL_ID_MEDICAMENT));
        m.setintensite_douleur (c.getInt (DOULEUR_COL_INTENSITE));
        m.setduree_douleur (c.getString (DOULEUR_COL_DUREE));
        m.setdate_douleur (c.getString (DOULEUR_COL_DATE));
        m.setheure_douleur (c.getString (DOULEUR_COL_HEURE));
        //On ferme le cursor
        c.close ();
        //On retourne le médicament
        return m.getId ();
    }

    // doit renvoyer le nombre d'élement de la table douleur
    // à partir du nomref -> toutes les douleurs pour la même migraine.
    int NombreDouleurWithNomref() {
        String[] clauseSelect = new String[]{" * "};
        //String clauseOu = null;
        //String[] argsOu = null;
        //String orderBy = null;

        Cursor c = DBMIGRAINE.query (TABLE_DOULEURS, clauseSelect, null, null, null, null, null);

        if (c.getCount () == 0)
            return 0;

        c.moveToLast ();
        //On créé un medicament
        Douleur m = new Douleur ();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        m.setId (c.getInt (DOULEUR_COL_ID));
        m.setmedic (c.getString (DOULEUR_COL_ID_MEDICAMENT));
        m.setintensite_douleur (c.getInt (DOULEUR_COL_INTENSITE));
        m.setduree_douleur (c.getString (DOULEUR_COL_DUREE));
        m.setdate_douleur (c.getString (DOULEUR_COL_DATE));
        m.setheure_douleur (c.getString (DOULEUR_COL_HEURE));
        //On ferme le cursor
        c.close ();
        //On retourne le médicament
        return m.getId ();
    }

/*-----------------------------------------------------------------------------------------------------------------*/

/*    //retourne tous les médicaments de la bdd dans un arraylist pour afficher le listview
    ArrayList<Item_Medicament> getAllMedicaments() {
        long i;
        Log.d ("ici getAll", "lecture base de donnée");
        ArrayList<Item_Medicament> medicList = new ArrayList<> ();
        String[] clauseSelect = new String[]{" * "};
        //String clauseOu = null;
        //String[] argsOu = null;
        //String orderBy = null;
//        Cursor c = bdd.query (TABLE_MEDIC, clauseSelect, clauseOu, argsOu, null, null, orderBy);

        Cursor c = bdd.query (TABLE_MEDIC, clauseSelect, null, null, null, null, null);
        if (c.moveToFirst ()) {
            do {
                Log.d ("1 - getallmedic", c.getString (1));
                Log.d ("2 - getallmedic", c.getString (2));

                Item_Medicament item1 = new Item_Medicament (c.getString (1), c.getString (2));
                medicList.add (item1);
            } while (c.moveToNext ());
        }
        c.close ();
        return medicList;
    }
*/
}
