package org.suinot.migraine;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static org.suinot.migraine.Constantes.constantes.RETOUR_HISTORIQUE;
import static org.suinot.migraine.Constantes.constantes.RETOUR_NOUVELLE_MIGRAINE;
import static org.suinot.migraine.R.id.medicament_par_jour;

/**
 * Created by remi on 14/11/16.
 */

public class Historique extends AppCompatActivity {

    private Spinner listViewMedicament_par_jour;
    private Spinner listViewMigraine_par_jour;
    Adapter adapterMedicament_par_jour;
    Adapter adapterMigraine_par_jour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.historique);

        /*
Ouverture de la base de données en lecture avant de créer le listview
         */
        List<String> values = new ArrayList<String> ();
        values.add ("heure");
        values.add ("jour");
        values.add ("semaine");
        values.add ("mois");
        values.add ("année");

        listViewMedicament_par_jour = (Spinner) findViewById (medicament_par_jour);
        listViewMigraine_par_jour = (Spinner) findViewById (R.id.migraine_par_jour);
        // Defined Array values to show in ListView
        ArrayAdapter<String> adapterMedicament_par_jour = new ArrayAdapter<String> (this,
                android.R.layout.simple_spinner_item, values);

        adapterMedicament_par_jour.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        listViewMedicament_par_jour.setAdapter (adapterMedicament_par_jour);

        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection ();

        // Button click Listener
        addListenerOnButton ();
    }

    public void addListenerOnSpinnerItemSelection() {

        listViewMedicament_par_jour.setOnItemSelectedListener (new CustomOnItemSelectedListener ());
    }

    private void addListenerOnButton() {
        Button close = (Button) findViewById(R.id.H_close);

        close.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                setResult (RETOUR_HISTORIQUE);
                finish ();
            }
        });
    }
}

class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {

        Toast.makeText (parent.getContext (),
                "On Item Select : \n" + parent.getItemAtPosition (pos).toString (),
                Toast.LENGTH_LONG).show ();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}