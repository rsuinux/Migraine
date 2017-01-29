package org.suinot.migraine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by remi on 27/12/16.
 */

public class Graphique extends Activity {

    private ListView listViewMigraine;
    CustomAdapter_migraine monAdapterMigraine = null;
    private ArrayList<Item_Migraine> dataMigraine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.historique);

        /*
Ouverture de la base de données en lecture avant de créer le listview
         */
        listViewMigraine = (ListView) findViewById (R.id.affiche_liste_migraines);
        monAdapterMigraine = new CustomAdapter_migraine (this, R.layout.item_migraine, this.dataMigraine);  //instantiation de l'adapter une seule fois

    }

}
