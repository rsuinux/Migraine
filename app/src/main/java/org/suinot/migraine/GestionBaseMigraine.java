package org.suinot.migraine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by remi on 15/10/16.
 * Méthodes pour toute ma gestion de la base de données Migraine
 */

class GestionBaseMigraine implements Constantes.constantes {
    private SQLiteDatabase DBMIGRAINE;
    private MabaseMigraine MABASEMIGRAINE;

/*---------------------------------------------------------------------*/

    GestionBaseMigraine(Context context) {
        //On crée la BDD et sa table
        MabaseMigraine.getInstance (context);
        MABASEMIGRAINE = MabaseMigraine.getInstance (context);
    }

/*---------------------------------------------------------------------*/

    void open() throws SQLiteException {
        //on ouvre la BDD en écriture

        try {
            DBMIGRAINE = MABASEMIGRAINE.getWritableDatabase ();
        } catch (SQLiteException ex) {
            DBMIGRAINE = MABASEMIGRAINE.getReadableDatabase ();
        }
    }

    void close() {
        //on ferme l'accès à la BDD
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
        values.put (COL_DATE, m.getdate_migraine ());
        values.put (COL_HEURE, m.getheure_migraine ());
        values.put (COL_DUREE, m.getduree_migraine ());
        values.put (COL_COMMENTAIRE, m.getcommentaire ());
        values.put (COL_ETAT, m.getetat ());
        i = DBMIGRAINE.insert (TABLE_MIGRAINES, null, values);
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
        values.put (COL_INTENSITE, m.getintensite_douleur ());
        values.put (COL_DOULEUR_DUREE, m.getduree_douleur ());
        values.put (COL_DOULEUR_DATE, m.getdate_douleur ());
        values.put (COL_DOULEUR_HEURE, m.getheure_douleur ());

        i = DBMIGRAINE.insert (TABLE_DOULEURS, null, values);
        return i;
    }

    /**
     * Insère une douleur en base de données
     *
     * @param id_Douleur  l'id de la migraine
     * @param id_Migraine l'id de la douleur associée
     * @return l'identifiant de la ligne insérée
     */
    long insertCroisee(long id_Douleur, long id_Medicament, long id_Migraine) {
        long i;
        ContentValues values = new ContentValues ();
        // on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put (COL_CROISEE_DOULEUR, id_Douleur);
        values.put (COL_CROISEE_MEDICAMENT, id_Medicament);
        values.put (COL_CROISEE_MIGRAINE, id_Migraine);

        i = DBMIGRAINE.insert (TABLE_CROISEE, null, values);
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
    long updateMigraine(long id, Migraine m) {

        long i;
        ContentValues values = new ContentValues ();
        values.put (COL_NOM, m.getnom_migraine ());
        values.put (COL_DATE, m.getdate_migraine ());
        values.put (COL_HEURE, m.getheure_migraine ());
        values.put (COL_DUREE, m.getduree_migraine ());
        values.put (COL_COMMENTAIRE, m.getcommentaire ());
        values.put (COL_ETAT, m.getetat ());
        values.put (COL_DATE_FIN, m.getdate_fin_migraine ());
        values.put (COL_HEURE_FIN, m.getheure_fin_migraine ());
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
    long updateDouleur(long id, Douleur m) {

        long i;
        ContentValues values = new ContentValues ();
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
        m.setdate_migraine (c.getString (MIGRAINE_COL_DATE));
        m.setheure_migraine (c.getString (MIGRAINE_COL_HEURE));
        m.setduree_migraine (c.getInt (MIGRAINE_COL_DUREE));
        m.setcommentaire (c.getString (MIGRAINE_COL_COMMENTAIRE));
        m.setetat (c.getInt (MIGRAINE_COL_ETAT));
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
        Log.d ("nombreMigraine", "m.getId()= " + m.getId ());
        m.setnom_migraine (c.getString (MIGRAINE_COL_NOM));
        m.setdate_migraine (c.getString (MIGRAINE_COL_DATE));
        m.setheure_migraine (c.getString (MIGRAINE_COL_HEURE));
        m.setduree_migraine (c.getInt (MIGRAINE_COL_DUREE));
        m.setcommentaire (c.getString (MIGRAINE_COL_COMMENTAIRE));
        m.setetat (c.getInt (MIGRAINE_COL_ETAT));
        //On ferme le cursor
        c.close ();
        //On retourne l'id de la dernière migraine
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
        m.setintensite_douleur (c.getInt (DOULEUR_COL_INTENSITE));
        m.setduree_douleur (c.getString (DOULEUR_COL_DUREE));
        m.setdate_douleur (c.getString (DOULEUR_COL_DATE));
        m.setheure_douleur (c.getString (DOULEUR_COL_HEURE));
        //On ferme le cursor
        c.close ();
        //On retourne le médicament
        return m.getId ();
    }


    /**
     * insert dans la table croisee les migraines et les douleurs associées
     *
     * @param migraine l'id de la migraine
     * @param douleur  l'id de la douleur associée
     * @return l'id de l'insertion , sinon, -1
     */
    int croise_migraine_douleur(long migraine, long douleur) {
        return 0;
    }

    /*-----------------------------------------------------------------------------------------------------------------*/
    //retourne les éléments de la bdd dans un arraylist pour afficher le listview
    ArrayList<Item_liste_d_evenement> getAllMigraine() {
        int i = 0;
        String txt = new String ();
        ArrayList<Item_liste_d_evenement> listevent = new ArrayList<Item_liste_d_evenement> ();
        String[] clauseSelect = new String[]{" * "};
        int item_image_no_action = R.mipmap.no_action;
        int item_image_config = R.mipmap.config;
        int item_image = 0;
        // String clauseOu = null;
        // String[] argsOu = null;
        // String orderBy = " DESC ";

        Cursor c = DBMIGRAINE.query (TABLE_MIGRAINES, clauseSelect, null, null, null, null, null);
        if (c.moveToLast ()) {
            do {
                txt = c.getString (MIGRAINE_COL_NOM);
                if (c.getInt (MIGRAINE_COL_ETAT) == 1) {
                    item_image = item_image_config;
                } else {
                    item_image = item_image_no_action;
                }
                Item_liste_d_evenement item = new Item_liste_d_evenement (
                        txt,
                        c.getString (MIGRAINE_COL_DATE),
                        c.getString (MIGRAINE_COL_HEURE),
                        getnombre_medicament (c.getInt (MIGRAINE_COL_ID)),
                        item_image
                );
                Log.d ("creation lisview", " date: " + c.getString (MIGRAINE_COL_DATE));
                Log.d ("creation lisview", " heure: " + c.getString (MIGRAINE_COL_HEURE));
                Log.d ("creation lisview", " nom: " + txt);

                listevent.add (item);
            } while (c.moveToPrevious ());
        }
        c.close ();
        return listevent;
    }

    /*
    * Recherche le nombre de médicament pris pour un évenement
    * Dans la table Croisee:
    *   A chaque nouvel ID de la table, on a
    *           -> l'id de la migraine d'origine (param2) dans la table migraine,
    *           -> l'id dans la table douleur (param1) qui peut nous donner (on non) un médicament mais au moins une douleur
    *
    * @param id l'id de l'evenement
    * @return le nombre de médicament
    */
    String getnombre_medicament(int ID) {
        int total = 0;

        String[] clauseSelect = new String[]{" * "};

        Cursor c = DBMIGRAINE.query (TABLE_CROISEE, null, null, null, null, null, null);

        if (c.getCount () != 0) {
            if (c.moveToFirst ()) {
                do {
                    if (c.getInt (NUM_COL_CROISEE_MIGRAINE) == ID) {
                        if (c.getInt (NUM_COL_CROISEE_MEDICAMENT) != 0) {
                            total += 1;
                        }
                    }
                } while (c.moveToNext ());
            }
        }
        c.close ();

        return String.valueOf (total);
    }

    /*
        Je demande si j'ai pris un médicament (oui/non) pour la douleur ID passé en parametre
         ID fait référence à la table Douleur
         pour savoœir si médicament associé ou non, on interroge la table Douleur, colonne Medicament
    */
    int medicament_ou_non(int ID) {
        int result = 0;
        /*
        String[] clauseSelect = new String[]{" * "};

        Cursor c = DBMIGRAINE.query (TABLE_DOULEURS, clauseSelect, null, null, null, null, null);

        if (c.getCount () == 0)
            return 0;
        if (c.moveToFirst ()) {
            do {
                if ((c.getInt (DOULEUR_COL_ID) == ID) && (c.getInt (DOULEUR_COL_ID_MEDICAMENT) != 0)) {
                    result = 1;
                }
            } while (c.moveToNext ());
        }
        c.close ();
        */
        return result;
    }

    /*
        Retourne (en texte) le nombre de douleur(s) pour la migraine ID
        il faut interroger la table "croisee" et demander le nombre de correspondance "ID" dans la colonne Migraine
     */
    String getnombre_douleur(int ID) {
        int total = 0;

        String[] clauseSelect = new String[]{" * "};
        //String clauseOu = null;
        //String[] argsOu = null;
        //String orderBy = null;

        Cursor c = DBMIGRAINE.query (TABLE_CROISEE, clauseSelect, null, null, null, null, null);

        if (c.getCount () == 0)
            return "0";
        if (c.moveToFirst ()) {
            do {
                if (c.getInt (NUM_COL_CROISEE_MIGRAINE) == ID) {
                    total += 1;
                }
            } while (c.moveToNext ());
        }
        c.close ();

        return String.valueOf (total);
    }

    /*
        Construction de l'arraylist pour le dialog HistoriqueUnitaire
        on cherche à peupler le listviex avec les donnée faisant référence à la mograine passée en parametre
     */
    ArrayList<Item_historique_unitaire> get_migraine_all_elements_historique(Context ctx, Migraine donnees) {
        int douleur = 0;
        String medicament = null;
        String commentaire = null;
        ArrayList<Item_historique_unitaire> listen = null;
        listen = new ArrayList<> ();
        String[] clauseSelect = new String[]{" * "};
        int ID = donnees.getId ();
        Log.d ("all_element", "donnees.getID()=" + ID);
        Item_historique_unitaire item1 = null;
        Cursor c = DBMIGRAINE.query (TABLE_CROISEE, clauseSelect, null, null, null, null, null);

        if (c.getCount () != 0) {
           /*
                Format de l'item:
                int [valeur_item_historique_unitaire_douleur] / String [valeur_item_historique_unitaire_medicament]
                String [valeur_item_historique_unitaire_commentaire]
                donnees.getID() donne l'ID de la migraine dans la table migraine et de la va déclancher tous le reste
             */

            if (c.moveToFirst ()) {
                do
                    if (c.getInt (MIGRAINE_COL_ID) == ID) { // nous avons une correspndance dans la base douleur
                        Log.d ("construct array", "migraine_colonne id= " + ID);
                        douleur = Recherche_douleur (c.getInt (NUM_COL_CROISEE_DOULEUR));
                        Log.d ("construct array", "douleur= " + douleur);
                        Log.d ("medic:", "NUM_COL_CROISEE_MEDICAMENT = " + NUM_COL_CROISEE_MEDICAMENT + " - cgetint()= " + c.getInt (NUM_COL_CROISEE_MEDICAMENT));
                        if (c.getInt (NUM_COL_CROISEE_MEDICAMENT) == 0) {
                            medicament = ctx.getString (R.string.pas_de_medicament);
                        } else {
                            medicament = Recherche_medicament (ctx, c.getInt (NUM_COL_CROISEE_MEDICAMENT));
                        }
                        Log.d ("construct array", "medicament= " + medicament);
                        commentaire = donnees.getcommentaire ();
                        // c.getInt (NUM_COL_MEDIC_INVALIDE);
                        item1 = new Item_historique_unitaire (douleur, medicament, commentaire);
                        // assert listen != null;
                        listen.add (item1);
                    } while (c.moveToNext ());
            }
        }
        c.close ();
        return listen;
    }


    int Recherche_douleur(int ID) {
        int valeur = 0;
        String[] clauseSelect = new String[]{" * "};
        Cursor d = DBMIGRAINE.query (TABLE_DOULEURS, clauseSelect, null, null, null, null, null);

        if (d.getCount () != 0) {
            if (d.moveToFirst ()) {
                do {
                    if (d.getInt (DOULEUR_COL_ID) == ID) {
                        valeur = d.getInt (DOULEUR_COL_INTENSITE);
                    }
                } while (d.moveToNext ());
            }
        }
        d.close ();
        return valeur;
    }

    String Recherche_medicament(Context ctx, int ID) {
        GestionBaseMedicament base_medicament;
        base_medicament = new GestionBaseMedicament (ctx);
        base_medicament.open ();
        return base_medicament.getMedicamentWithId (ID);
    }
}
