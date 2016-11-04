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

class MabaseMigraine extends SQLiteOpenHelper {

    private static final int VERSION_DB = 1;
    private static final String NOM_DB = "Migraines.db";
    private static final String TABLE_MIGRAINES = "table_migraines";
    private static final String COL_ID = "ID";
    private static final String COL_NOM = "NOM";
    private static final String COL_DATE = "DATE";
    private static final String COL_HEURE = "HEURE";
    private static final String COL_DUREE = "DUREE";
    private static final String COL_COMMENTAIRE = "COMMENTAIRE";
    private static final String CREATE_DBMIGRAINE = "CREATE TABLE " + TABLE_MIGRAINES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DATE + " TEXT NOT NULL, "
            + COL_DUREE + " TEXT NOT NULL, "
            + COL_DUREE + " TEXT, "
            + COL_COMMENTAIRE + " TEXT NOT NULL);";
    private static MabaseMigraine sInstance;
    private final Context context;
    private String DB_PATH; // chemin défini dans le constructeur

    // Constructeur
    private MabaseMigraine(Context context) {
        super (context, NOM_DB, null, VERSION_DB);
        this.context = context;
        String filesDir = context.getFilesDir ().getPath (); // /data/data/com.package.nom/files/
        DB_PATH = filesDir.substring (0, filesDir.lastIndexOf ("/")) + "/databases/"; // /data/data/com.package.nom/databases/
        Log.d ("ConstR DBMigraine", "création de la base");

        // Si la bdd n'existe pas dans le dossier de l'app
        if (!checkdbmigraine ()) {
            // copy db de 'assets' vers DATABASE_PATH
            copydbmigraine ();
        }
    }

    static synchronized MabaseMigraine getInstance(Context context) {
        Log.d ("getInstance", "création de la base");

        if (sInstance == null) {
            sInstance = new MabaseMigraine (context);
        }
        return sInstance;
    }

    private boolean checkdbmigraine() {
        // retourne true/false si la bdd existe dans le dossier de l'app
        File dbfile = new File (DB_PATH + NOM_DB);
        Log.d ("checkdbmigraine", "verif si base existe");
        Log.d ("checkdbmigraine", "chemin + fichier=" + DB_PATH + NOM_DB);
        return dbfile.exists ();
    }

    // On copie la base de "assets" vers "/data/data/com.package.nom/databases"
    // ceci est fait au premier lancement de l'application
    private boolean copydbmigraine() {

        final String outFileName = DB_PATH + NOM_DB;
        Log.d ("copydbmigraine", "outFileName: " + outFileName);

        InputStream myInput;
        try {
            // Ouvre la bdd de 'assets' en lecture
            myInput = context.getAssets ().open (NOM_DB);

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
            SQLiteDatabase checkdb = SQLiteDatabase.openDatabase (DB_PATH + NOM_DB, null, SQLiteDatabase.OPEN_READWRITE);
            checkdb.setVersion (VERSION_DB);
        } catch (SQLiteException e) {
            // bdd n'existe pas
            return false;
        }

        return true;
    } // copydatabase()


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        Log.d ("onCreate", "pas de bdd existante");
        sqLiteDatabase.execSQL (CREATE_DBMIGRAINE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        sqLiteDatabase.execSQL ("DROP TABLE " + TABLE_MIGRAINES + ";");
        onCreate (sqLiteDatabase);

    }
}
