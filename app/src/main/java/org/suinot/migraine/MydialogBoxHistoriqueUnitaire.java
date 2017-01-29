package org.suinot.migraine;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by remi on 21/01/17.
 */

public class MydialogBoxHistoriqueUnitaire extends DialogFragment {
    Button mButton;

    onSubmitListener mListener;
    GestionBaseMigraine baseMigraine;
    Migraine donnees = new Migraine ();
    int ID = 0;
    private ArrayList<Item_historique_unitaire> arraylist_h_u=null;

    interface onSubmitListener {
        void setOnSubmitListener(String arg);
    }

    // on recupère l'id dans la base de données, pour la Migraine à afficher
    void get_id_base_de_donnees(int id) {
        ID = id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context ctx = getActivity ();
        baseMigraine = new GestionBaseMigraine (ctx);
        baseMigraine.open ();
        donnees = baseMigraine.getMigraineWithId (ID);

        final Dialog dialog = new Dialog (ctx);
        dialog.getWindow ().requestFeature (Window.FEATURE_NO_TITLE);
        dialog.getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView (R.layout.dialog_historique_unitaire);
        dialog.getWindow ().setBackgroundDrawable (
                new ColorDrawable (getResources ().getColor (R.color.fond_general)));
        dialog.show ();

        TextView nom = (TextView) dialog.findViewById (R.id.Hist_Unit_nom);
        nom.setText (donnees.getnom_migraine ());

        TextView douleur = (TextView) dialog.findViewById (R.id.Hist_Unit_nombre_douleurs);
        douleur.setText (baseMigraine.getnombre_douleur (ID));
        // douleur.setText ("15");

        TextView medicament = (TextView) dialog.findViewById (R.id.Hist_Unit_nombre_medicaments);
        medicament.setText (baseMigraine.getnombre_medicament (ID));
        // medicament.setText ("25");

        CustomAdapter_historique_unitaire arrayadapteurlist = null;
        ListView listview_h_u = null;
        listview_h_u = (ListView) dialog.findViewById (R.id.Hist_Unit_listview);
        arraylist_h_u = new ArrayList<> ();
        arraylist_h_u = baseMigraine.get_migraine_all_elements_historique( ctx, donnees );
        arrayadapteurlist = new CustomAdapter_historique_unitaire (
                ctx,
                R.layout.item_historique_unitaire,
                this.arraylist_h_u);
        listview_h_u.setAdapter (arrayadapteurlist);

        listview_h_u.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
Log.d ("set on click", "click court");
                // Click court
                // l'ID reçu est le n° dans le listview et pas dnas la base de donnée!!! 'ordre inversé!!!)
                Toast.makeText (ctx ,
                        "Démonstration : id= " + id,
                        Toast.LENGTH_LONG);
            }
        });


        mButton = (Button) dialog.findViewById (R.id.bouton_fin_historique_unitaire);
        mButton.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                dismiss ();
            }
        });
        return dialog;
    }

    private Context getApplicationContext() { return getApplicationContext (); }
}
