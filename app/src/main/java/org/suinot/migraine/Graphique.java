package org.suinot.migraine;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static android.R.string.cancel;
import static android.R.string.ok;
import static org.suinot.migraine.Constantes.constantes.RETOUR_GRAPHIQUE;
import static org.suinot.migraine.Constantes.constantes.RETOUR_HISTORIQUE;

/**
 * Created by remi on 27/12/16.
 */

public class Graphique extends Activity {

    private ListView listViewMigraine;
    CustomAdapter_migraine monAdapterMigraine = null;
    private ArrayList<Item_Migraine> dataMigraine;
    private int migraine_en_cours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.graphique);

        Intent intent = getIntent ();
        if (intent != null) {
            migraine_en_cours = this.getIntent ().getExtras ().getInt ("last");
        }

        Button close = (Button) findViewById (R.id.G_close);
        close.setOnClickListener (new View.OnClickListener () { // Notre classe anonyme
            public void onClick(View view) { // et sa méthode !

                setResult (RETOUR_GRAPHIQUE);
                finish ();
            }
        });
    }

}
