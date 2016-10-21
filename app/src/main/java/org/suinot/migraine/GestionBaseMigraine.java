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

public class GestionBaseMigraine {
    private static final String COL_ID = "ID";
    private static final int MIGRAINE_COL_ID = 0;
    private static final String COL_NOM = "NOM";
    private static final int MIGRAINE_COL_NOM = 1;
    private static final String COL_DATE = "DATE";
    private static final int MIGRAINE_COL_DATE = 2;
    private static final String COL_HEURE = "HEURE";
    private static final int MIGRAINE_COL_HEURE = 3;
    private static final String COL_DUREE = "DUREE";
    private static final int MIGRAINE_COL_DUREE = 4;
    private static final String COL_COMMENTAIRE = "COMMENTAIRE";
    private static final int MIGRAINE_COL_COMMENTAIRE = 5;

    private static final String TABLE_MIGRAINES = "table_migraines";
    private static final String NOM_DB = "Migraines.db";
    private String DB_PATH; // chemin défini dans le constructeur
    private MabaseMigraine mabaseMigraine;
    private SQLiteDatabase dbMigraine;

    public GestionBaseMigraine(Context context) {
        //On crée la BDD et sa table
        Log.d ("GestionBaseMedicament()", "création de la base");
        MabaseMigraine.getInstance (context);
        mabaseMigraine = MabaseMigraine.getInstance (context);
    }

    public void open() {
        //on ouvre la BDD en écriture
        Log.d ("open()", "ouverture de la base");
        dbMigraine = mabaseMigraine.getWritableDatabase ();
    }

    public void close() {
        //on ferme l'accès à la BDD
        Log.d ("GestionBaseMedicament()", "close base");
        dbMigraine.close ();
    }

    /**
     * Insère un médicament en base de données
     *
     * @param m : la migraine à insérer
     * @return l'identifiant de la ligne insérée
     */
    public long insertMigraine(Migraine m) {
        long i;
// Log.d ("insertMedic", "nom= " + medic.getMedicament ());
// Log.d ("insertMedic", "dose= " + medic.getDose ());
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues ();
        // on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put (COL_NOM, m.getnom ());
        values.put (COL_DATE, m.getdate ());
        values.put (COL_HEURE, m.getheure ());
        values.put (COL_DUREE, m.getduree ());
        values.put (COL_COMMENTAIRE, m.getcommentaire ());

        i = dbMigraine.insert (TABLE_MIGRAINES, null, values);
        Log.d ("insertMigraine", "Retour de la base: " + i);
        return i;
    }

    /**
     * Met à jour le médicament en base de données
     *
     * @param id l'identifiant du médicament à modifier
     * @param m: la nouvelle migraine à associer à l'identifiant
     * @return le nombre de lignes modifiées
     */
    public long updateMigraine(int id, Migraine m) {

        long i;
        ContentValues values = new ContentValues ();
        values.put (COL_NOM, m.getnom ());
        values.put (COL_DATE, m.getdate ());
        values.put (COL_HEURE, m.getheure ());
        values.put (COL_DUREE, m.getduree ());
        values.put (COL_COMMENTAIRE, m.getcommentaire ());
        i = dbMigraine.update (TABLE_MIGRAINES, values, COL_ID + " = " + id, null);
        return i;
    }

    /**
     * Supprime une migraine de la BDD (celle dont l'identifiant est passé en
     * paramètres)
     *
     * @param id l'identifiant de la migraine
     * @return le nombre d'élément(s) supprimé(s)
     */
    public long removeMigraineWithID(long id) {
        long i;
        i = dbMigraine.delete (TABLE_MIGRAINES, COL_ID + " = " + id, null);
        return i;
    }

    /**
     * Retourne la migraine dont le numéro correspond à
     * celle en paramètre
     *
     * @param id l'identifiant de la migraine
     * @return la migraine récupérée depuis la base de données
     */
    public Migraine getMigraineWithId(int id) {
        //Récupère dans un Cursor les valeurs correspondants à une migraine contenu dans la BDD
        // (ici on sélectionne la migraine grâce à son id)
        String[] clauseSelect = new String[]{" * "};
        String clauseOu = COL_ID + " = ? ";
        String argsOu = Integer.toString (id);
        String orderBy = "";

        Cursor c = dbMigraine.query (TABLE_MIGRAINES, clauseSelect, clauseOu, new String[]{argsOu}, null, null, orderBy);
        // Cursor c=bdd.query(TABLE_MEDIC, new String[]{COL_ID, COL_NOM, COL_DOSE }, null, null, COL_ID + " = " + id, null, null);
        return cursorToMigraine (c);
    }

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

        Cursor c = dbMigraine.query (TABLE_MIGRAINES, clauseSelect, clauseOu, argsOu, null, null, orderBy);

        return cursorToMigraine (c);
    }

    /**
     * Convertit le cursor en migraine
     *
     * @param c le cursor à convertir
     * @return le médic créé à partir du Cursor
     */
    private Migraine cursorToMigraine(Cursor c) {
        //Cette méthode permet de convertir un cursor en un medicament
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount () == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst ();

        //On créé un medicament
        Migraine m = new Migraine ();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        m.setId (c.getInt (MIGRAINE_COL_ID));
        m.setnom (c.getString (MIGRAINE_COL_NOM));
        m.setdate (c.getString (MIGRAINE_COL_DATE));
        m.setheure (c.getString (MIGRAINE_COL_HEURE));
        m.setduree (c.getString (MIGRAINE_COL_DUREE));
        m.setcommentaire (c.getString (MIGRAINE_COL_COMMENTAIRE));
        //On ferme le cursor
        c.close ();

        //On retourne le médicament
        return m;
    }

    // doit renvoyer le dernier element de la base
    long NombreMigraine() {
        String[] clauseSelect = new String[]{" * "};
        //String clauseOu = null;
        //String[] argsOu = null;
        //String orderBy = null;

        Cursor c = dbMigraine.query (TABLE_MIGRAINES, clauseSelect, null, null, null, null, null);

        if (c.getCount () == 0)
            return 0;

        c.moveToLast ();
        //On créé un medicament
        Migraine m = new Migraine ();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        m.setId (c.getInt (MIGRAINE_COL_ID));
        m.setnom (c.getString (MIGRAINE_COL_NOM));
        m.setdate (c.getString (MIGRAINE_COL_DATE));
        m.setheure (c.getString (MIGRAINE_COL_HEURE));
        m.setduree (c.getString (MIGRAINE_COL_DUREE));
        m.setcommentaire (c.getString (MIGRAINE_COL_COMMENTAIRE));
        //On ferme le cursor
        c.close ();
        //On retourne le médicament
        return m.getId ();
    }

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
