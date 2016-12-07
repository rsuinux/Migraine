package org.suinot.migraine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private Button fin_de_Migraine;
    private int etat_migraine_actuelle;

    Context context;
    private EditText commentaire;
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView etat_migraine;
    private int migraine_en_cours = 0;

    GestionBaseMigraine baseMigraine;

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
        GestionBaseMedicament baseMedicament = new GestionBaseMedicament (this);
        baseMedicament.open ();

        setContentView (R.layout.nouvelle_migraine);
        fin_de_Migraine = (Button) findViewById (R.id.B_Fin_de_Migraine);
        etat_migraine = (TextView) findViewById (R.id.Etat_Evenement);
        if (migraine_en_cours == 0) {
            // c'est une nouvelle migraine demandée
            fin_de_Migraine.setEnabled (false);
            fin_de_Migraine.setClickable (false);
            etat_migraine.setText (R.string.Etat_evenement_1);
        } else {
            // on demande la suite d'une migraine en cours -> la plus récente
            fin_de_Migraine.setEnabled (true);
            fin_de_Migraine.setClickable (true);
            etat_migraine.setText (R.string.Etat_evenement_2);
        }

        /* ------------------------------------------------------------------------------------------
        Gestion des boutons et des champs texte
        Récupération et gestion de l'event par onclick en fin de class
        ------------------------------------------------------------------------------------------ */
        findViewById (R.id.B_Enregistrer).setOnClickListener (this);

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
        /* ------------------------------------------------------------------------------------------
         on demande à modifier la date et/ou heure de la nouvelle migraine:
          ------------------------------------------------------------------------------------------- */
        //noinspection RedundantStringConstructorCall
        String date;
        final Calendar c = Calendar.getInstance ();

        java.text.DateFormat formatter = java.text.DateFormat.getDateInstance (
                java.text.DateFormat.SHORT); // one of SHORT, MEDIUM, LONG, FULL, or DEFAULT
        formatter.setTimeZone (c.getTimeZone ());
        date = formatter.format (c.getTime ());
        SimpleDateFormat mHeureFormat = new SimpleDateFormat ("HH:mm", Locale.FRANCE);

        String heure = mHeureFormat.format (new Date ());

        timeTextView = (TextView) findViewById (R.id.Actuelle_Heure);
        timeTextView.setText (heure, TextView.BufferType.EDITABLE);

        dateTextView = (TextView) findViewById (R.id.Actuelle_Date);
        dateTextView.setText (date, TextView.BufferType.EDITABLE);

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

        commentaire = (EditText) findViewById (R.id.B_Commentaire);
    }

    private void set_liste_deroulante() {
        liste_deroulante.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> a, View v, int position, long id) {
                // String item= liste_deroulente.getItemAtPosition(position);
                String item = liste_deroulante.getSelectedItem ().toString ();
                Log.d ("Log: Spinner ", item + "a=" + a + "id=" + id);
                /* ici, ajouter à la listview mListView litem sélectionnée */

                Log.d ("ajout de medicament ", "item=" + item);
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
        String date = dayOfMonth + "/" + monthOfYear + "/" + year;
        dateTextView.setText (date);
        date_actuel = date;
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

    // gestion de l'ecran de choix de l'heure
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        // Log.d ("TimePicker", "ok");
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = hourString + "h" + minuteString;
        timeTextView.setText (time);
        heure_actuel = time;
    }
    /* ------------------------------------------------------------------------------------------------------
      Fin de  Fonction de geston du datapicker et timepicker ensuite
     ------------------------------------------------------------------------------------------------------ */


    // gestion de la seekbar
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // à partir de la on change l'état des radio bouton quand la seekbar change
        Log.d ("seekbar", "id= " + progress);
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

                Log.d ("radiobouton", "rb=" + r1 + ".");
                douleur_actuel = r1;
                Log.d ("radiobouton", "douleur_actuel=" + douleur_actuel + ".");
                seekBar.setProgress (Integer.decode (r1));
//                Toast.makeText (context, String.valueOf (selectedRadioButton[0].getText ().toString ()), Toast.LENGTH_SHORT).show ();
            }
        });
    }

    // gestion des boutons
    public void onClick(View v) {
        // que déclanche ton quand on clique?
        long i = 0;
        long j;
        byte[] txt = new byte[9];
//        String txt=new String ();
        Migraine migraine = new Migraine ();
        Douleur douleur = new Douleur ();
        switch (v.getId ()) {

            case R.id.B_Enregistrer:
                //Ce que tu veux faire lorsque tu cliques sur le bouton 2
                if (nom_actuel.equals ("")) {
                    j = random.nextLong (); // 64 bit -> 8 caractères
                    do {
                        txt[(int) i] = (byte) (0xFF & (j >> 8 * i));
                        if (txt[(int) i] < 32) {
                            txt[(int) i] += 32;
                        }
                        i += 1;
                    } while (i < 9);
                    nom_actuel = String.valueOf (txt);
                }
                Log.d ("onClick", "bouton enregistrer");
                Log.d ("OnClick", " nom " + nom_actuel);
                Log.d ("OnClick", "date " + date_actuel);
                Log.d ("OnClick", "heure " + heure_actuel);
                Log.d ("OnClick", "douleur " + douleur_actuel);
                Log.d ("OnClick", "medicament " + medicament_actuel);
                migraine.setnom_migraine (nom_actuel);
                migraine.setdate_migraine (date_actuel);
                migraine.setheure_migraine (heure_actuel);
                migraine.setduree_migraine ("");
                migraine.setcommentaire (commentaire.getText ().toString ());
                migraine.setetat (1);

                // Ecriture dans la base de données -> table migraines
                j = baseMigraine.insertMigraine (migraine);
                if (j != -1) {
                    douleur.setmedic (medicament_actuel);
                    i = Long.parseLong (douleur_actuel);
                    douleur.setintensite_douleur ((int) i);
                    douleur.setdate_douleur (date_actuel);
                    douleur.setheure_douleur (heure_actuel);
                    // Ecriture dans la base de données -> table douleurs
                    i = baseMigraine.insertDouleur (douleur);
                    if (i != -1) {
                        // Tout c'est bien passé jusqu'ici.
                        // récupération des numéros d'entrée dans les tables
                        // pour les placer dans table_croisee
                        i = baseMigraine.insertCroisee (i, j);
                        if (i == -1) {
                            Toast.makeText (getApplicationContext (), "Attention\nProblème d'insertion dans la base 'table de données croisées'\nNe sera pas fait.",
                                    Toast.LENGTH_LONG).show ();
                        }
                    } else {
                        Toast.makeText (getApplicationContext (), "Attention\nProblème d'insertion dans la base 'table Douleur'\nNe sera pas fait.",
                                Toast.LENGTH_LONG).show ();
                    }
                    baseMigraine.close ();
                } else {
                    Toast.makeText (getApplicationContext (), "Attention\nProblème d'insertion dans la base 'table Migraine'\nNe sera pas fait.",
                            Toast.LENGTH_LONG).show ();
                }
                setResult (1);
                finish ();
                break;
        }
    }
}
