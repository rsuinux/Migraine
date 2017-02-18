package org.suinot.migraine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by remi on 15/10/16.
 * Gestion de la bdd Migraine
 */

class MabaseMigraine extends SQLiteOpenHelper implements Constantes.constantes {

    private static MabaseMigraine sInstance;
    private final Context context;
    private String DB_PATH; // chemin défini dans le constructeur


    /*  Nom de la table des migraines et creation */
    /*  déclarations des variables dans Constantes.java*/

    private static final String CREATE_TABLE_MIGRAINES = "CREATE TABLE " + TABLE_MIGRAINES + " ("
            + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
            + COL_NOM + "TEXT NOT NUL"
            + COL_DATE + " TEXT, "
            + COL_HEURE + " TEXT, "
            + COL_DUREE + " INTEGER, "
            + COL_ETAT + " INTEGER "
            + COL_DATE_FIN + " TEXT "
            + COL_HEURE_FIN + " TEXT );";

    /* Nom de la table des douleurs et creation */
    /*  déclarations des variables dans Constantes.java*/

    private static final String CREATE_TABLE_DOULEURS = "CREATE TABLE " + TABLE_DOULEURS + " ("
            + COL_DOULEUR_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
            + COL_INTENSITE + " INTEGER, "
            + COL_DOULEUR_DUREE + " TEXT, "
            + COL_DOULEUR_DATE + " TEXT, "
            + COL_DOULEUR_HEURE + " TEXT, "
            + COL_DOULEUR_COMMENTAIRE + " TEXT);";

    /* Nom de la table CROISEE et creation */
    /*  déclarations des variables dans Constantes.java*/

    private static final String CREATE_TABLE_CROISEE = "CREATE TABLE " + TABLE_CROISEE + " ("
            + COL_CROISEE_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
            + COL_CROISEE_DOULEUR + " INTEGER, "
            + COL_CROISEE_MEDICAMENT + " INTEGER, "
            + COL_CROISEE_MIGRAINE+ " INTEGER);";
    /* Fin des déclarations des tables */

    // Constructeur
    public MabaseMigraine(Context context) {
        super (context, NOM_DB_MIGRAINES, null, VERSION_DB_MEDICAMENTS);
        this.context = context;
        String filesDir = context.getFilesDir ().getPath (); // /data/data/com.package.nom/files/
        DB_PATH = filesDir.substring (0, filesDir.lastIndexOf ("/")) + "/databases/"; // /data/data/com.package.nom/databases/
        // Si la bdd n'existe pas dans le dossier de l'app
        if (!checkdbmigraine ()) {
            // copy db de 'assets' vers DATABASE_PATH
            copydbmigraine ();
        }
    }

    static synchronized MabaseMigraine getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new MabaseMigraine (context);
        }
        return sInstance;
    }

    private boolean checkdbmigraine() {
        // retourne true/false si la bdd existe dans le dossier de l'app
        File dbfile = new File (DB_PATH + NOM_DB_MIGRAINES);
        return dbfile.exists ();
    }

    // On copie la base de "assets" vers "/data/data/com.package.nom/databases"
    // ceci est fait au premier lancement de l'application
    private boolean copydbmigraine() {

        final String outFileName = DB_PATH + NOM_DB_MIGRAINES;

        InputStream myInput;
        try {
            // Ouvre la bdd de 'assets' en lecture
            myInput = context.getAssets ().open (NOM_DB_MIGRAINES);

            // dossier de destination
            File pathFile = new File (DB_PATH);
            if (!pathFile.exists ()) {
                if (!pathFile.mkdirs ()) {
                    Toast.makeText (context, "Erreur : copydatabase(), pathFile.mkdirs()", Toast.LENGTH_SHORT).show ();
                    return false;
                }
            }

            // Ouverture en écriture du fichier bdd de destination
            OutputStream myOutput = new FileOutputStream (outFileName);

            // transfert de inputfile vers outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read (buffer)) > 0) {
                myOutput.write (buffer, 0, length);
            }

            // Fermeture
            myOutput.flush ();
            myOutput.close ();
            myInput.close ();
        } catch (IOException e) {
            e.printStackTrace ();
            Toast.makeText (context, "Erreur : copydbmigraine()", Toast.LENGTH_SHORT).show ();
            return false;
        }

        // on greffe le numéro de version
        try {
            SQLiteDatabase checkdb = SQLiteDatabase.openDatabase (DB_PATH + NOM_DB_MIGRAINES, null, SQLiteDatabase.OPEN_READWRITE);
            checkdb.setVersion (VERSION_DB_MEDICAMENTS);
        } catch (SQLiteException e) {
            // bdd n'existe pas
            return false;
        }

        return true;
    } // copydatabase()


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        sqLiteDatabase.execSQL (CREATE_TABLE_MIGRAINES);
        sqLiteDatabase.execSQL (CREATE_TABLE_DOULEURS);
        sqLiteDatabase.execSQL (CREATE_TABLE_CROISEE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        sqLiteDatabase.execSQL ("DROP TABLE " + TABLE_MIGRAINES + ";");
        sqLiteDatabase.execSQL ("DROP TABLE " + TABLE_DOULEURS + ";");
        sqLiteDatabase.execSQL ("DROP TABLE " + TABLE_CROISEE + ";");
        onCreate (sqLiteDatabase);

    }
}
