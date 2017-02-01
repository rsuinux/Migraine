package org.suinot.migraine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by remi on 18/08/16.
 * Class pour la gestion d'une nouvelle migraine (ou d'une en cours?)
 */
public class Nouvelle_Migraine extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener, Constantes.constantes {

    SeekBar seekBar;
    RadioGroup rg;
    Random random;
    Spinner liste_deroulante;
    String nom_actuel = "";
    private String date_actuel;
    private String heure_actuel;
    private String douleur_actuel;
    private int medicament_actuel;

    Context context;
    private EditText nouveau_nom;
    private EditText commentaire;
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView etat_migraine;
    private int migraine_en_cours = 0;
    GestionBaseMigraine baseMigraine;
    GestionBaseMedicament baseMedicament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        Intent intent = getIntent ();
        if (intent != null) {
            migraine_en_cours = this.getIntent ().getExtras ().getInt ("last");
        }

        context = (this);
        random = new Random ();
        int nb_de_migraines;
        douleur_actuel = "0";
        Migraine migraine = new Migraine ();
        baseMigraine = new GestionBaseMigraine (this);
        baseMigraine.open ();
        baseMedicament = new GestionBaseMedicament (this);
        baseMedicament.open ();

        setContentView (R.layout.nouvelle_migraine);

        /* ------------------------------------------------------------------------------------------
        Gestion des boutons et des champs texte
        Récupération et gestion de l'event par onclick en fin de class
        ------------------------------------------------------------------------------------------ */

        // Rechercher l'instance du radio Group, nous en aurons besoin pour poser un listener
        rg = (RadioGroup) findViewById (R.id.Radio_Douleur);
        // on met le premier bouton à "true"
        ((RadioButton) rg.getChildAt (0)).setChecked (true);
        // On pose notre listener sur le radio group
        setRadioButton ();

        seekBar = (SeekBar) findViewById (R.id.Seek_Douleur);
        // gestion de la seekbarr de la douleur
        seekBar.setOnSeekBarChangeListener (this);

    /* ------------------------------------------------------------------------------------------------------
            Gestion de la listView migraine
             affichage des donnees en cours:
             ligne 1: noms date heure
             ligne 2: médicament dose
     ------------------------------------------------------------------------------------------------------ */

        /* ------------------------------------------------------------------------------------------
         on demande à modifier la date et/ou heure de la nouvelle migraine:
          ------------------------------------------------------------------------------------------- */
        //noinspection RedundantStringConstructorCall
        final Calendar c = Calendar.getInstance ();
        java.text.DateFormat formatter = java.text.DateFormat.getDateInstance (
                DateFormat.SHORT); // type de format (court ou non: SHORT MEDIUM LONG) pour la date
        formatter.setTimeZone (c.getTimeZone ());
        date_actuel = formatter.format (c.getTime ());
        dateTextView = (TextView) findViewById (R.id.Actuelle_Date);
        dateTextView.setText (date_actuel);

        SimpleDateFormat mHeureFormat = new SimpleDateFormat ("HH:mm", Locale.FRANCE);
        heure_actuel = mHeureFormat.format (new Date ());
        timeTextView = (TextView) findViewById (R.id.Actuelle_Heure);
        timeTextView.setText (heure_actuel);
        ImageButton Nouvelle_date;
        Nouvelle_date = (ImageButton) findViewById (R.id.New_Date);
        Nouvelle_date.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance ();
                DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance (
                        Nouvelle_Migraine.this,
                        now.get (Calendar.YEAR),
                        now.get (Calendar.MONTH),
                        now.get (Calendar.DAY_OF_MONTH)
                );
                dpd.show (getFragmentManager (), "Datepickerdialog");
            }
        });

        nouveau_nom = (EditText) findViewById (R.id.Nouveau_Nom);
        commentaire = (EditText) findViewById (R.id.B_Commentaire);

        /* ------------------------------------------------------------------------------------------------------
           // création du tableau dynamique et affichage de type spinner de la base des médicaments
        ------------------------------------------------------------------------------------------------------ */
        // Mise à jour de la liste des médicaments à partir de la base de données du médicament
        // et gestion de la liste déroulante
        List<String> list_medicaments = baseMedicament.getAllLabels ();
        ArrayAdapter adapter_spinner = new ArrayAdapter (this, android.R.layout.simple_spinner_item, list_medicaments);
        /* On definit une présentation du spinner quand il est déroulé (android.R.layout.simple_spinner_dropdown_item) */
        adapter_spinner.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);

        liste_deroulante = (Spinner) findViewById (R.id.Medicaments);
        liste_deroulante.setAdapter (adapter_spinner);
        set_liste_deroulante ();
        medicament_actuel = 0;

        findViewById (R.id.B_Enregistrer).setOnClickListener (this);

    }

    private void set_liste_deroulante() {
        liste_deroulante.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> a, View v, int position, long id) {
                // String item= liste_deroulente.getItemAtPosition(position);
                String item = liste_deroulante.getSelectedItem ().toString ();
                /* ici, ajouter à la listview mListView litem sélectionnée */

                if (!item.isEmpty ()) {
                    medicament_actuel = (int) id;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /* ------------------------------------------------------------------------------------------------------
      Fonction de geston du datapicker et timepicker ensuite
     ------------------------------------------------------------------------------------------------------ */
    // gestion de l'ecran de choix de l'heure
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // a partir d'ici, je doit gérer le retour des valeurs, puis lancer timepicker
        // Log.d ("DatePicker", "ok");
        monthOfYear += 1;
        date_actuel = dayOfMonth + "/" + monthOfYear + "/" + year;
        dateTextView.setText (date_actuel);

        Calendar now = Calendar.getInstance ();
        TimePickerDialog tpd = TimePickerDialog.newInstance (
                Nouvelle_Migraine.this,
                now.get (Calendar.HOUR_OF_DAY),
                now.get (Calendar.MINUTE),
                false
        );
        tpd.vibrate (true);
        tpd.show (getFragmentManager (), "Timepickerdialog");
    }

    /* ------------------------------------------------------------------------------------------------------
      Fin de  Fonction de geston du datepicker
      timepicker ensuite pour le choix de l'heure
     ------------------------------------------------------------------------------------------------------ */
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        // Log.d ("TimePicker", "ok");
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        heure_actuel = hourString + "h" + minuteString;
        timeTextView.setText (heure_actuel);
    }

    /* ------------------------------------------------------------------------------------------------------
        Fin de  Fonction de geston du timepicker

        // gestion de la seekbar
     ------------------------------------------------------------------------------------------------------ */
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // à partir de la on change l'état des radio bouton quand la seekbar change
        douleur_actuel = Integer.toString (progress);
        ((RadioButton) rg.getChildAt (progress)).setChecked (true);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    // gestion des radio boutons
    public void setRadioButton() {
        //Récupérer le Radio Button qui est sélectionné
        // int selectedId = rg.getCheckedRadioButtonId ();
//        final String selectedRadioButton;
        // selectedRadioButton = (RadioButton) findViewById (selectedId);

        //Listener
        rg.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final RadioButton rb = (RadioButton) findViewById (checkedId);
                String r1 = (String) rb.getText ();

                douleur_actuel = r1;
                seekBar.setProgress (Integer.decode (r1));
//                Toast.makeText (context, String.valueOf (selectedRadioButton[0].getText ().toString ()), Toast.LENGTH_SHORT).show ();
            }
        });
    }

    // gestion de tous les boutons
    public void onClick(View v) {
        // que déclanche ton quand on clique?
        long i = 0;
        int j;
        long k;
        Migraine migraine = new Migraine ();
        Douleur douleur = new Douleur ();
        long date_time_non_formate = System.currentTimeMillis ();
        switch (v.getId ()) {

            case R.id.B_Enregistrer:
                //Ce que tu veux faire lorsque tu cliques sur le bouton 2
                nom_actuel = nouveau_nom.getText ().toString ();
                if (nom_actuel.equals ("")) {
                    nom_actuel = nom_actuel.valueOf (date_time_non_formate);
                }
                migraine.setnom_migraine (nom_actuel);
                migraine.setdate_migraine (date_actuel);
                migraine.setheure_migraine (heure_actuel);
                migraine.setduree_migraine (0);
                migraine.setetat (1);

                // Ecriture dans la base de données -> table migraines
                k = baseMigraine.insertMigraine (migraine);
                if (k != -1) {
                    i = Long.parseLong (douleur_actuel);
                    douleur.setintensite_douleur ((int) i);
                    douleur.setdate_douleur (date_actuel);
                    douleur.setheure_douleur (heure_actuel);
                    douleur.setcommentaire_douleur (commentaire.getText ().toString ());
                    // Ecriture dans la base de données -> table douleurs
                    i = baseMigraine.insertDouleur (douleur);
                    if (i != -1) {
                        // Tout c'est bien passé jusqu'ici.
                        // insertion dans la table_croisee de l'id douleur, l'id medicament et l'id migraine
                        i = baseMigraine.insertCroisee (i, medicament_actuel, k);
                        if (i == -1) {
                            Toast.makeText (getApplicationContext (),
                                    R.string.nouvelle_migraine_pb_insertion_croisee,
                                    Toast.LENGTH_LONG)
                                    .show ();
                        }
                    } else {
                        Toast.makeText (getApplicationContext (),
                                R.string.nouvelle_migraine_pb_insertion_douleur,
                                Toast.LENGTH_LONG)
                                .show ();
                    }
                    baseMigraine.close ();
                } else {
                    Toast.makeText (getApplicationContext (),
                            R.string.nouvelle_migraine_pb_insertion_migraine,
                            Toast.LENGTH_LONG)
                            .show ();
                }
                setResult (RETOUR_NOUVELLE_MIGRAINE);
                finish ();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume ();
        baseMedicament.open ();
        baseMigraine.open ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        baseMedicament.close ();
        baseMigraine.close ();
    }

}
