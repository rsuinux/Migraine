package org.suinot.migraine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* import pour date et time picker */

/**
 * Created by remi on 18/08/16.
 * Class pour la gestion d'une nouvelle migraine (ou d'une en cours?)
 */
public class Nouvelle_Migraine extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener, View.OnKeyListener {

    SeekBar seekBar;
    RadioGroup rg;

    Spinner liste_deroulante;
    EditText nouveaunom;
    private String nom_actuel;
    private String date_actuel;
    private String heure_actuel;
    private String douleur_actuel;
    private String medicament_actuel;
    private String dose_actuel;

    Button resultat;

    private ArrayList<Item_Migraine> migraine;
    private ListView listeMigraine;
    // CustomAdapter_migraine adapter;

    Context context;
    private TextView dateTextView;
    private TextView timeTextView;
    private int liste_en_cours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);

        GestionBaseMedicament baseMedicament;
        baseMedicament = new GestionBaseMedicament (this);
        baseMedicament.open ();
        context = (this);
        setContentView (R.layout.nouvelle_migraine);


        /* ------------------------------------------------------------------------------------------
        Gestion des boutons et des champs texte
        Récupération et gestion de l'event par onclick en fin de class
        ------------------------------------------------------------------------------------------ */
        findViewById (R.id.nouveau_nom).setOnKeyListener (this);

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

    }

    private void set_liste_deroulante() {
        liste_deroulante.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> a, View v, int position, long id) {
                // String item= liste_deroulente.getItemAtPosition(position);
                String item = liste_deroulante.getSelectedItem ().toString ();
                Log.d ("Log: Spinner ", item + "a=" + a + "id=" + id);
                /* ici, ajouter à la listview mListView litem sélectionnée */
                ajout_de_medicament (item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void ajout_de_medicament(String item) {
//        int i = adapter.getCount ();

        Log.d ("ajout de medicament ", "item=" + item);
        if (!item.isEmpty ()) {
            medicament_actuel = item;
        }
    }

    /* ------------------------------------------------------------------------------------------------------
      Fonction de geston du datapicker et timepicker ensuite
     ------------------------------------------------------------------------------------------------------ */
    // gestion de l'ecran de choix de l'heure
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // a partir d'ici, je doit gérer le retour des valeurs, puis lancer timepicker
        Log.d ("DatePicker", "ok");
        ++monthOfYear;
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
        Log.d ("TimePicker", "ok");
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
        int selectedId = rg.getCheckedRadioButtonId ();
        final String selectedRadioButton;
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
        switch (v.getId ()) {

            case R.id.B_Enregistrer:
                //Ce que tu veux faire lorsque tu cliques sur le bouton 2
                /*
                ToDo: Enregistrer en base de donnée la nouvelle migraine
                 */
                Log.d ("onClick", "bouton enregistrer");
                finish ();
                break;
        }
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d ("setOnKey", "entree");

            if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                    keyCode == EditorInfo.IME_ACTION_DONE ||
                    event.getAction () == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode () == KeyEvent.KEYCODE_ENTER) {

                if (!event.isShiftPressed ()) {
                    Log.v ("AndroidEnterKeyActivity", "Enter Key Pressed!");
                    return true;
                }
            }

        return false; // pass on to other listeners.
    }
}



/* fin d'import */
