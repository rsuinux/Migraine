package org.suinot.migraine;

import android.content.Context;
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

import com.orhanobut.simplelistview.SimpleListView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* import pour date et time picker */
/**
 * Created by remi on 18/08/16.
 */
public class Nouvelle_Migraine extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    SeekBar seekBar;
    RadioGroup rg;
    RadioButton selectedRadioButton;
    Spinner liste_deroulante;
    private TextView dateTextView;
    private TextView timeTextView;
    private int liste_en_cours = 0;
    Migraine nouvelle_migraine;
    EditText nouveaunom;
    Button resultat;
    StringBuffer[] itemsText = new StringBuffer[3];
    ArrayAdapter<StringBuffer> adapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);

        GestionBaseMedicament baseMedicament;
        baseMedicament = new GestionBaseMedicament (this);
        baseMedicament.open ();
        context=(this);
        setContentView (R.layout.nouvelle_migraine);


        /* ------------------------------------------------------------------------------------------
        Gestion des boutons et du chaamp texte
        Récupération et gestion de l'event par onclick en fin de class
        ------------------------------------------------------------------------------------------ */
        nouveaunom = (EditText) findViewById (R.id.nouveau_nom);
        resultat = (Button) findViewById (R.id.nom_ok);
        resultat.setOnClickListener ((View.OnClickListener) this);

        Button enregistrer;
        enregistrer = (Button) findViewById (R.id.B_Enregistrer);


        // Rechercher l'instance du radio Group, nous en aurons besoin pour poser un listener
        rg = (RadioGroup) findViewById (R.id.Radio_Douleur);
        // on met le premier bouton à "true"
        ((RadioButton) rg.getChildAt (0)).setChecked (true);
        // On pose notre listener sur le radio group
        setRadioButton();

        seekBar = (SeekBar) findViewById (R.id.Seek_Douleur);
        // gestion de la seekbarr de la douleur
        seekBar.setOnSeekBarChangeListener (this);

    /* ------------------------------------------------------------------------------------------------------
            Gestion de la liste view:
                Dès que le spinner est utilisé, on ajoute la donnée dedans
     ------------------------------------------------------------------------------------------------------ */
        SimpleListView listView = (SimpleListView) findViewById (R.id.Elements_selectionnes);

        itemsText[0] = new StringBuffer (20);
        itemsText[0].append ("111111111111111111111");

        itemsText[1] = new StringBuffer (20);
        itemsText[1].append ("222222222222222222222");

        itemsText[2] = new StringBuffer (20);
        itemsText[2].append ("33333333333333333333");

        adapter = new ArrayAdapter<StringBuffer> (
                this,
                android.R.layout.simple_list_item_1,
                itemsText
        );

        listView.setHeaderView (R.layout.header);
        listView.setFooterView (R.layout.footer);
        listView.setDividerView (R.layout.divider);
        listView.setOnItemClickListener (new SimpleListView.OnItemClickListener ()

                                         {
                                             @Override
                                             public void onItemClick(Object item, View view, int position) {
                                                 Log.d ("log: SimpleView ", " Selection " + position);
                                             }
                                         }

        );
        listView.setAdapter (adapter);

        //It will refresh the listview
        adapter.notifyDataSetChanged ();

    /* ------------------------------------------------------------------------------------------------------
           // création du tableau dynamique  et affichage de type spinner
     ------------------------------------------------------------------------------------------------------ */
        // Mise à jour de la liste des médicaments à partir de la base de données du médicament
        // et gestion de la liste déroulante
        List<String> list_medicaments = baseMedicament.getAllLabels ();
        ArrayAdapter adapter_spinner = new ArrayAdapter (this, android.R.layout.simple_spinner_item, list_medicaments);
        /* On definit une présentation du spinner quand il est déroulé (android.R.layout.simple_spinner_dropdown_item) */
        adapter_spinner.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout

        liste_deroulante = (Spinner) findViewById (R.id.Medicaments);
        liste_deroulante.setAdapter (adapter_spinner);
        set_liste_deroulante();

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
                Log.d ("Log: Spinner ", item);
                /* ici, ajouter à la listview mListView litem sélectionnée */
                ajout_de_medicament (item);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void ajout_de_medicament(String item) {
        int i = adapter.getCount ();

        Log.d ("Log: ", "item=" + item + "i= " + i + "liste_en_cours= " + liste_en_cours);
        if (!item.isEmpty ()) {
            if (i == liste_en_cours) {
                Toast.makeText (getApplicationContext (), "Attention, vous atteingnez la dose maxi", Toast.LENGTH_LONG).show ();
            } else {
                liste_en_cours++;
//                        StringBuffer sbReplaced = sb.toString().replaceAll("le mot a remplacer", "un autre mot");
                itemsText[liste_en_cours].replace (0, 19, item);
                adapter.notifyDataSetChanged ();
            }
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
        String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        dateTextView.setText (date);
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
    public void setRadioButton()
    {
        //Récupérer le Radio Button qui est sélectionné
        int selectedId = rg.getCheckedRadioButtonId();
        selectedRadioButton = (RadioButton) findViewById(selectedId);

        //Listener
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedRadioButton = (RadioButton) findViewById(checkedId);
                seekBar.setProgress(checkedId);
                Log.d ("radiobouton", "id= " + checkedId);
                Toast.makeText(context, String.valueOf(selectedRadioButton.getText().toString()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // gestion des boutons
    public void onClick(View v) {
        // que déclanche ton quand on clique?
        switch (v.getId ()) {
            case R.id.nom_ok:
                String chaine = nouveaunom.getText ().toString ();
                Log.v ("Essai", chaine);
                nouveaunom.clearFocus ();
                break;

            case R.id.B_Enregistrer:
                //Ce que tu veux faire lorsque tu cliques sur le bouton 2
                finish ();
                break;
        }
    }

}


/* fin d'import */
