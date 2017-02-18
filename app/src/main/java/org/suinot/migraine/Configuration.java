package org.suinot.migraine;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.R.string.cancel;
import static android.R.string.ok;
import static org.suinot.migraine.R.layout.configuration;

/**
 * Created by remi on 27/08/16.
 * Activity pour la configuration de la base de données du médicament (visu, ajout, suppression)
 */

public class Configuration extends Activity implements Constantes.constantes {

    Activity parentActivity=this;
    GestionBaseMedicament medicBdd;
    CustomAdapter_medic monAdapter = null;
    private ArrayList<Item_Medicament> data;
    private long derniere_donnees_initiale;
    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        final Context context = this;

        setContentView (configuration);

        medicBdd = new GestionBaseMedicament (this);
        // On ouvre la base de données pour écrire dedans
        medicBdd.open ();

        // copieBase = medicBdd;
        derniere_donnees_initiale = medicBdd.NombreMedicament ();

        // setup the data source
        data = new ArrayList<> ();
        data = medicBdd.getAllMedicaments ();

        ListView listViewMedicaments = (ListView) findViewById (R.id.C_affiche_bdd);
        monAdapter = new CustomAdapter_medic (
                this,
                R.layout.template_item,
                this.data);  //instantiation de l'adapter une seule fois

        listViewMedicaments.setAdapter (monAdapter);
        final LayoutInflater inflater = LayoutInflater.from (this);

        // final View dialogView = inflater.inflate (R.layout.nouveau_medic, null);
        // ajout d'un mémdicament
        Button ajouter = (Button) findViewById (R.id.C_ajout);
        ajouter.setOnClickListener (new View.OnClickListener () { // Notre classe anonyme
            public void onClick(View view) { // et sa méthode !
                // ici, on ouvre une boite avec deux champs: medicament et dose

                AlertDialog.Builder dialog = new AlertDialog.Builder (context);
                dialog.setView (inflater.inflate (R.layout.nouveau_medic, null));
                dialog.setTitle (R.string.configuration_nouveau_medicament);
                final AlertDialog alertDialog = dialog.create ();
                final AlertDialog.Builder builder = dialog.setPositiveButton (ok, new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog, int which) {
                        Medicament medic = new Medicament ();
                        EditText et1 = (EditText) ((AlertDialog) dialog).findViewById (R.id.Nouveau_Medic);
                        EditText et2 = (EditText) ((AlertDialog) dialog).findViewById (R.id.Nouveau_Dose);
                        String s1 = et1 != null ? et1.getText ().toString () : null;
                        String s2 = et2 != null ? et2.getText ().toString () : null;
                        medic.setMedicament (s1);
                        medic.setDose (s2);
                        if (medicBdd.insertMedicament (medic) >= 1) {
                            //Enregistrement réussi, ajouter le nouveau médicament dans la liste
                            Item_Medicament item1 = new Item_Medicament (s1, s2, 0);
                            data.add (item1);
                            //Ensuite rafraîchir l'adaptateur
                            monAdapter.notifyDataSetChanged ();
                        }
                    }
                });
                dialog.setNegativeButton (cancel, new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss ();
                    }
                });
                dialog.show ();
            }
        });

        listViewMedicaments.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                clique court
                    pour l'instant, juste un toast
                    Todo: stat sur le nombre d'utilisation du médicament
                 */
                Toast.makeText (getApplicationContext (),
                        "Click court: "
                        + parent.getItemAtPosition (position).toString ()
                        + "(" + position + ")",
                        Toast.LENGTH_SHORT).show ();
            }
        });
        listViewMedicaments.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener () {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                demande de suppression dans la base de données
                  1/ confirmation avec une boite d'alerte
                  2/ si oui
                     3/ appel GestionBaseMedicament -> removeMedicamentWithID (position)
                     4/ update de l'affichage
                 */
                Item_Medicament obj = (Item_Medicament) parent.getItemAtPosition (position);
                Suppression (position, obj, view);
                return true;
            }
        });

        Button sauver = (Button) findViewById (R.id.C_sauver);
        sauver.setOnClickListener (new View.OnClickListener () { // Notre classe anonyme
            public void onClick(View view) { // et sa méthode !

                Toast.makeText (getApplicationContext (), R.string.configuration_sauvegarde_medicaments, Toast.LENGTH_SHORT).show ();
                medicBdd.close ();

                setResult (RETOUR_CONFIGURATION);
                finish ();
            }
        });

        // Annulation de nos ajouts de médicament
        Button annuler = (Button) findViewById (R.id.C_annuler);
        annuler.setOnClickListener (new View.OnClickListener () { // Notre classe anonyme
            public void onClick(View view) { // et sa méthode !
                // on a dans derniere_donnees_initiale la dfin des données de la basse, on supprime toutes les autres
                long last;
                last = medicBdd.NombreMedicament ();
                while (last > derniere_donnees_initiale) {
                    medicBdd.removeMedicamentWithID (last);
                    last = medicBdd.NombreMedicament ();
                }

                Toast.makeText (getApplicationContext (), R.string.configuration_annulation, Toast.LENGTH_SHORT).show ();
                medicBdd.close ();

                setResult (RETOUR_CONFIGURATION);
                finish ();
            }
        });


        Button exporter = (Button) findViewById (R.id.C_export);
        exporter.setOnClickListener (new View.OnClickListener () { // Notre classe anonyme
            public void onClick(View view) { // et sa méthode !
                try {
                    Export_db ();
                } catch ( Exception e) {
                    e.printStackTrace ();
                }
            }
        });
    }

    public void Suppression(final int listeitemId, Item_Medicament liste, View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder (this);
        Medicament medic = new Medicament ();
        medic = medicBdd.getMedicamentWithNom (liste.get ("medicament"), liste.get ("dosage"));
        final int id = medic.getId ();
        alertDialogBuilder.setMessage (R.string.configuration_confirme_suppression_medicament + medic.getMedicament ());

        final Medicament finalMedic = medic;
        alertDialogBuilder.setPositiveButton ("yes", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // Ici suppression du médicament et demande de réaffichage
                finalMedic.setInvalide (1);
                if (medicBdd.updateMedicament (id, finalMedic) != 0) {
                    monAdapter.notifyDataSetChanged ();
                } else {
                    Toast.makeText (getApplicationContext (), R.string.configuration_erreur_sup_medicament, Toast.LENGTH_LONG).show ();
                }
            }
        }).setNegativeButton ("No", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create ();
        alertDialog.show ();
    }

    void Export_db() throws IOException {
        // Recherche si l'on a les autorisation (appel de méthode plus bas si besoin)

        if (! (ContextCompat.checkSelfPermission (Configuration.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) ) {
            // nous n'avons pas la permission d'écriture, il faut la demander
            ActivityCompat.requestPermissions ( Configuration.this ,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
            // apres ça, il faut mettre un écouteur sur ActivityCompat.requestPermissions

        }
        String etat = Environment.getExternalStorageState ();
        if (Environment.MEDIA_MOUNTED.equals (etat)) {
            copie_donnee();
        } else {
            Toast.makeText (getApplicationContext (),
                    R.string.configuration_erreur_media,
                    Toast.LENGTH_LONG)
                    .show ();
        }

    }

    void copie_donnee(){
        Date date = new Date ( System.currentTimeMillis() );
        SimpleDateFormat sdf = new SimpleDateFormat ( "dd/MM/yyyy hh:mm:ss", Locale.FRANCE );
        String txt = sdf.format (date);

        txt=txt.replace("/", "_");
        txt=txt.replace(":", "_");
        txt=txt.replace(" ", "_");
        //creating a new folder for the database to be backuped to
        File Root = Environment.getExternalStorageDirectory ();
        File folder = new File (Root.getAbsolutePath () + File.separator + "migraine" + File.separator);
        folder.mkdirs ();
        if (!folder.exists ()) {
            Log.d ("Export_db", "folder n'existe toujours pas");
            folder.mkdirs ();
        }
        File medic = new File (folder, "medicaments-" + txt + ".db");
        try {
            medic.createNewFile ();
        } catch ( Exception ex1) {
            ex1.printStackTrace ();
        }
        File migraines = new File (folder, "migraine-" + txt + ".db");
        try {
            migraines.createNewFile();
        } catch ( Exception ex1) {
            ex1.printStackTrace ();
        }
        try {
            exportDB (medic, migraines);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
    //Export de la base de données
    private void exportDB(File medic, File migraines) throws FileNotFoundException {
        // context, NOM_DB_MIGRAINES, null, VERSION_DB_MEDICAMENTS);
        String filesDir = getApplicationContext ().getFilesDir ().getPath (); // /data/data/com.package.nom/files/
        String DB_MEDIC = filesDir.substring (0, filesDir.lastIndexOf ("/")) + "/databases/" + NOM_BD_MEDICAMENTS; // /data/data/com.package.nom/databases/
        String DB_MIGRAINES = filesDir.substring (0, filesDir.lastIndexOf ("/")) + "/databases/" + NOM_DB_MIGRAINES; // /data/data/com.package.nom/databases/
        //BufferedInputStream(new FileInputStream  = null;
        BufferedInputStream fis;
        BufferedOutputStream fos;
        byte[] buf = new byte[8];

        try {
            if (medic.canWrite ()) {
                fis = new BufferedInputStream (new FileInputStream (DB_MEDIC));
                fos = new BufferedOutputStream (new FileOutputStream (medic));
                while (fis.read (buf) != -1) {
                    fos.write (buf);
                }
                // Fermeture
                fis.close ();
                fos.close ();
            } else {
                Log.d("export_bd", "impossible de copier base medic");
            }

            if (migraines.canWrite ()) {
                fis = new BufferedInputStream (new FileInputStream (DB_MIGRAINES));
                fos = new BufferedOutputStream (new FileOutputStream (migraines));
                while (fis.read (buf) != -1) {
                    fos.write (buf);
                }
                // Fermeture
                fis.close ();
                fos.close ();
            } else {
                Toast.makeText (getApplicationContext (),
                        R.string.configuration_sauvegarde_medicaments_erreur,
                        Toast.LENGTH_LONG)
                        .show ();
                Log.d("export_bd", "impossible de copier base migraines");
            }
        } catch (Exception e) {
            Toast.makeText (getApplicationContext (),
                    e.toString (),
                    Toast.LENGTH_LONG)
                    .show ();
        }
        Toast.makeText (getApplicationContext (),
                R.string.configuration_copie_termine,
                Toast.LENGTH_LONG)
                .show ();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //nous avons reçu l'autorisation d'écriture, on fait la copie :)
                    copie_donnee ();
                } else {
                    Toast.makeText (
                            parentActivity,
                            "L'application n'est pas autorisée à écrire sur l'espace de stockage externe.\n" +
                                    "Par conséquent, il se peut que l'export des données ne fonctionne pas correctement.\n" +
                                    "Pourriez vous envisager l'octroi de cette permission? Merci.",
                            Toast.LENGTH_LONG)
                            .show ();
                }
            }
        }

    }
}
