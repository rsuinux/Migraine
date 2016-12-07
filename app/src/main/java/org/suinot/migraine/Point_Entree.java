package org.suinot.migraine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by remi on 18/08/16.
 * Activity principale de l'application
 */
public class Point_Entree extends Activity {

    GestionBaseMigraine baseMigraine;
    /* En attente
        private Button graphique;
    */
    private Button historique;
    private Button en_cours;
    private Button nouveau;
    private static final int CODE_RETOUR = 1;
    //    Douleur douleur;
    TextView tdate;
    TextView theure;
    TextView tnombreevnt;

    private int migraine_en_cours = 0;
    // A faire:
    //   1: rechercher les bases de données -> base médicaments -> fait
    //                                      -> base migraines -> fait
    //   2: si existe pas -> la créer
    //   3: l'ouvrir
    //   4: afficher sur la surface première les info:
    //       nombre de données ---> fait


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        setContentView (R.layout.point_entree);

        en_cours = (Button) findViewById (R.id.B_en_cours);

        tdate = (TextView) findViewById (R.id.t_date);
        theure = (TextView) findViewById (R.id.t_heure);

        Migraine donnees = new Migraine ();
        Integer nb_de_migraines;
        String valeur;

            /* --------------------------------------------------------------------------------
            Mise en place des diverses informations dans les champs textview
            ---------------------------------------------------------------------------------- */

        baseMigraine = new GestionBaseMigraine (this);
        baseMigraine.open ();
        nb_de_migraines = baseMigraine.NombreMigraine ();
        Log.d ("onCreate", "nb_de_migraines" + nb_de_migraines);
        tnombreevnt = (TextView) findViewById (R.id.T_nombre_evnt);
        tnombreevnt.setText (String.format ("%d", nb_de_migraines));
        if (nb_de_migraines > 0) {
            donnees = baseMigraine.getMigraineWithId (nb_de_migraines);
            valeur = donnees.getdate_migraine ();
            Log.d ("onCreate", "DATE last" + valeur);
            tdate.setText (valeur);
            valeur = donnees.getheure_migraine ();
            Log.d ("onCreate", "TIME last" + valeur);
            theure.setText (valeur);
            Log.d ("donnees.getetat", "valeur: " + donnees.getetat ());
            if (donnees.getetat () != 0) {
                en_cours.setEnabled (true);
                en_cours.setClickable (true);
                migraine_en_cours = 1;
            } else {
                en_cours.setEnabled (false);
                en_cours.setClickable (false);
            }
        }
        /* --------------------------------------------------------------------------------
            Gestion des boutons
        --------------------------------------------------------------------------------- */
        nouveau = (Button) findViewById (R.id.B_new_event);
        nouveau.setOnClickListener (new OnClickListener () {
            @Override
            public void onClick(View view) {
                //On créé l'Intent qui va nous permettre d'afficher l'autre Activity
                //Mettez le nom de l'Activity dans la quelle vous êtes actuellement
                Intent Nouvelle_migraine = new Intent (getApplicationContext (), Nouvelle_Migraine.class);
                Nouvelle_migraine.putExtra ("last", migraine_en_cours);
                startActivityForResult (Nouvelle_migraine, CODE_RETOUR);
            }
        });

        historique = (Button) findViewById (R.id.B_Historique);
        historique.setOnClickListener ((new OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent Vue_Historique = new Intent (getApplicationContext (), Historique.class);
                startActivity (Vue_Historique);
            }
        }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected (item);
        switch (item.getItemId ()) {
            case R.id.M_Config:
                startActivity (new Intent (getApplicationContext (), Configuration.class));
                return true;
            case R.id.M_Quit:
                finish ();
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //on regarde quelle Activity a répondu
        String valeur;
        int nb_de_migraines;
        switch (requestCode) {
            case CODE_RETOUR:

                //On regarde qu'elle est la réponse envoyée et en fonction de la réponse on affiche un message différent.
                switch (resultCode) {
                    case 1:
                        baseMigraine.open ();
                        nb_de_migraines = baseMigraine.NombreMigraine ();
                        tnombreevnt.setText (Long.toString (nb_de_migraines));
                        if (nb_de_migraines > 0) {
                            Migraine donnees = baseMigraine.getMigraineWithId (nb_de_migraines);
                            valeur = donnees.getdate_migraine ();
                            tdate.setText (valeur);
                            valeur = donnees.getheure_migraine ();
                            theure.setText (valeur);
                        }
                        return;
                }
        }
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume ();
        //Refresh your stuff here
    }
}
