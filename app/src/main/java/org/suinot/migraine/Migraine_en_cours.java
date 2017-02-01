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

/**
 * Created by remi on 09/12/16.
 * Gestion de migraine en cours, pour ajout de douleur/medicament ou pour la terminer.
 * le parametre en entrée est le numéro de la migraine en cours
 */

public class Migraine_en_cours extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener, Constantes.constantes {

    SeekBar seekBar;
    RadioGroup rg;
    Spinner liste_deroulante;
    String nom_actuel = "";
    private String date_actuel;
    private String heure_actuel;
//    private String old_date;
//    private String old_heure;
    private String douleur_actuel;
    private int medicament_actuel;
    private Button fin_de_Migraine;

    Context context;
    private EditText nouveau_nom;
    private EditText commentaire;
    private TextView dateTextView;
    private TextView timeTextView;
    ImageButton Nouvelle_date;
    private int numero_en_cours = 0;
    private int etat=1;
    Migraine migraine = new Migraine ();
    Douleur douleur=new Douleur ();
    Boolean date_changee = false;
    GestionBaseMigraine baseMigraine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        Intent intent = getIntent ();
        if (intent != null) {
            numero_en_cours = this.getIntent ().getExtras ().getInt ("last");


            context = (this);
            douleur_actuel = "0";
            baseMigraine = new GestionBaseMigraine (this);
            baseMigraine.open ();

            migraine=baseMigraine.getMigraineWithId (numero_en_cours);
            nom_actuel=migraine.getnom_migraine ();

            GestionBaseMedicament baseMedicament = new GestionBaseMedicament (this);
            baseMedicament.open ();

            setContentView (R.layout.nouvelle_migraine);
            fin_de_Migraine = (Button) findViewById (R.id.B_Fin_de_Migraine);
            // on demande la suite d'une migraine en cours -> la plus récente
            fin_de_Migraine.setEnabled (true);
            fin_de_Migraine.setClickable (true);

            findViewById (R.id.B_Fin_de_Migraine).setOnClickListener (this);
            findViewById (R.id.B_Enregistrer).setOnClickListener (this);

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
            final Calendar c = Calendar.getInstance ();

            java.text.DateFormat formatter = java.text.DateFormat.getDateInstance (
                    java.text.DateFormat.SHORT); // one of SHORT, MEDIUM, LONG, FULL, or DEFAULT
            formatter.setTimeZone (c.getTimeZone ());
            date_actuel = formatter.format (c.getTime ());
            dateTextView = (TextView) findViewById (R.id.Actuelle_Date);
            dateTextView.setText (date_actuel);

            SimpleDateFormat mHeureFormat = new SimpleDateFormat ("HH:mm", Locale.FRANCE);
            heure_actuel = mHeureFormat.format (new Date ());
            timeTextView = (TextView) findViewById (R.id.Actuelle_Heure);
            timeTextView.setText (heure_actuel);

            Nouvelle_date = (ImageButton) findViewById (R.id.New_Date);
            Nouvelle_date.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    Calendar now = Calendar.getInstance ();
                    DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance (
                            Migraine_en_cours.this,
                            now.get (Calendar.YEAR),
                            now.get (Calendar.MONTH),
                            now.get (Calendar.DAY_OF_MONTH)
                    );
                    dpd.show (getFragmentManager (), "Datepickerdialog");
                }
            });

            nouveau_nom = (EditText) findViewById (R.id.Nouveau_Nom);
            nouveau_nom.setText (nom_actuel);

            commentaire = (EditText) findViewById (R.id.B_Commentaire);

        } else {
            // si on arrive ici c'est que l'intent n'a pas donné l'id de la migraine
            finish();
        }
    }

    private void set_liste_deroulante() {
        liste_deroulante.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> a, View v, int position, long id) {
                // String item= liste_deroulente.getItemAtPosition(position);
                String item = liste_deroulante.getSelectedItem ().toString ();
                /* ici, ajouter à la listview mListView litem sélectionnée */
                if (!item.isEmpty ()) {
                    medicament_actuel = (int)id;
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
        date_changee=true;
        Calendar now = Calendar.getInstance ();
        TimePickerDialog tpd = TimePickerDialog.newInstance (
                Migraine_en_cours.this,
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
        date_changee=true;
    }
    /* ------------------------------------------------------------------------------------------------------
      Fin de  Fonction de geston du datapicker et timepicker ensuite
     ------------------------------------------------------------------------------------------------------ */

    // gestion de la seekbar
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

    // gestion des boutons
    public void onClick(View v) {
        // que déclanche t'on quand on clique?
        long i = 0;
        long j;
        byte[] txt = new byte[9];
//        String txt=new String ();
        Migraine migraine = new Migraine ();
        Douleur douleur = new Douleur ();
        switch (v.getId ()) {

            case R.id.B_Fin_de_Migraine:
                // ici, l'état dépend du bouton "fin"
                if ( etat == 1 ) {
                    etat += 1;
                    fin_de_Migraine.setText (R.string.bouton_chgt_detat);
                    // on demande à quelle date et heure!
                    if ( !date_changee ) {
                        Nouvelle_date.performClick ();
                    }
                    fin_de_Migraine.forceLayout ();
                } else if ( etat == 2 ) {
                    etat -= 1;
                    fin_de_Migraine.setText (R.string.fin_de_migraine);
                    fin_de_Migraine.forceLayout ();
                }
                break;
            case R.id.B_Enregistrer:
                // Récupération du nom de la migraine par la base de donnée si besoin
                // le reste des données viennent de l'utilisateur
/*
  * l'utilisateur  ajoute une douleur et/ou un médicament
  *  => on insert une nouvelle donnée dans la table douleur
  *  => on ajoute dans la table croisee un id douleur et un id medicament, avec le meme id migraine

Arrivé ici, il faudra calculer le delta entre la date de départ et la date de fin
*/
                i = Long.parseLong (douleur_actuel);
                douleur.setintensite_douleur ((int) i);
                douleur.setdate_douleur (date_actuel);
                douleur.setheure_douleur (heure_actuel);
                douleur.setcommentaire_douleur (commentaire.getText ().toString ());

                // Ecriture dans la base de données -> table douleurs
                i = baseMigraine.insertDouleur (douleur);
                if (i != -1) {
                    // Tout c'est bien passé jusqu'ici.
                    // insertion dans la table croisee de: l'id de la douleur, l'id du médic et l'id de la migraine en cours
                    j = baseMigraine.insertCroisee (i, medicament_actuel, numero_en_cours );
                    if (j != -1) {
                        //nom_actuel.
                        migraine.setnom_migraine (nom_actuel);
                        migraine.setduree_migraine (0);
                        migraine.setetat (etat);
                        if (etat == 2) {
                            migraine.setdate_fin_migraine (date_actuel);
                            migraine.setheure_fin_migraine (heure_actuel);
                        } else {
                            migraine.setdate_fin_migraine ("");
                            migraine.setheure_fin_migraine ("");
                        }
                        // update de la table migraines
                        j = baseMigraine.updateMigraine (numero_en_cours, migraine);
                        if (j == -1) {
                            Toast.makeText (getApplicationContext (),
                                    R.string.nouvelle_migraine_pb_insertion_migraine,
                                    Toast.LENGTH_LONG).show ();
                        }
                    } else {
                        Toast.makeText (getApplicationContext (),
                                R.string.nouvelle_migraine_pb_insertion_croisee,
                                Toast.LENGTH_LONG).show ();
                    }
                } else {
                    Toast.makeText (getApplicationContext (),
                            R.string.nouvelle_migraine_pb_insertion_douleur,
                            Toast.LENGTH_LONG).show ();
                }
                baseMigraine.close ();
                setResult (RETOUR_MIGRAINE_EN_COURS); // valeur pour le retour de l'activity
                finish();
            break;
        }
    }
}
