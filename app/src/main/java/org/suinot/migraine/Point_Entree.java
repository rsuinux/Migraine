package org.suinot.migraine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by remi on 18/08/16.
  Activity principale de l'application
 */
public class Point_Entree extends Activity {

    GestionBaseMedicament medicBdd;
    private Button bouton;
    private Button config;
    private Button historique;
    private Button nouveau;

    // A faire:
    //   1: rechercher les bases de données -> base médicaments -> fait
    //                                      -> base miigraines -> à faire
    //   2: si existe pas -> la créer
    //   3: l'ouvrir
    //   4: afficher sur la surface première les info:
    //       nombre de données, .... (à voir)
    // Format base de données: migraine: id	date	heure	medic  duree
    //                         medic: id2	nom	dci	quantite

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.point_entree);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

            /* --------------------------------------------------------------------------------
            Mise en place des diverses informations dans les champs textview
            ---------------------------------------------------------------------------------- */

            /* --------------------------------------------------------------------------------
            Gestion des boutons
             --------------------------------------------------------------------------------- */
        // nouvel evenement ou fin d'evenement
        nouveau = (Button) findViewById (R.id.B_new_event);
        OnClickListener pagesuivante;
        nouveau.setOnClickListener ((new OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent Nouvelle_migraine = new Intent (getApplicationContext (), Nouvelle_Migraine.class);
                startActivity (Nouvelle_migraine);
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
}
