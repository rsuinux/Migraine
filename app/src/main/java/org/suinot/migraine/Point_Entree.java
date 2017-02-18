package org.suinot.migraine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by remi on 18/08/16.
 * Activity principale de l'application
 */
public class Point_Entree extends AppCompatActivity implements Constantes.constantes, MydialogBoxHistoriqueUnitaire.onSubmitListener {

    public static Activity activity;
    GestionBaseMigraine baseMigraine;
    boolean executed = false;

    private Button b_historique;
    private Button b_graphique;
    private Button nouveau;
    private static final int CODE_RETOUR = 1;
    private TextView tnombreevnt;
    CustomAdapter_evenement listevent_Adapter = null;

    ListView listnombreevnt;
    ArrayList<Item_liste_d_evenement> arrayList_Evenements;
    Migraine donnees = new Migraine ();

    private int migraine_en_cours = 0;
    // A faire:
    //   1: rechercher les bases de données -> base médicaments -> fait
    //                                      -> base migraines -> fait
    //   2: si existe pas -> la créer
    //   3: l'ouvrir
    //   4: afficher sur la surface première les info:
    //       nombre de données ---> fait

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        activity = this;

        setContentView (R.layout.point_entree);
        final Integer nb_de_migraines;

        String valeur;

            /* --------------------------------------------------------------------------------
            Mise en place des diverses informations dans les champs textview
            ---------------------------------------------------------------------------------- */

        baseMigraine = new GestionBaseMigraine (this);
        baseMigraine.open ();

        //      Création d'une listeview avec le nombre de mibraine (date heure de début + nombre de médicament )
        // setup the data source
        arrayList_Evenements = new ArrayList<> ();
        arrayList_Evenements = baseMigraine.getAllMigraine ();

        listnombreevnt = (ListView) findViewById (R.id.liste_evenement);
        listevent_Adapter = new CustomAdapter_evenement
                (this,
                 R.layout.item_liste_d_evenement,
                 this.arrayList_Evenements);  //instantiation de l'adapter 1 seule fois;
        listnombreevnt.setAdapter (listevent_Adapter);

        listnombreevnt.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d ("setonclick", "click court" + "executed= " + String.valueOf (executed));
                // Click court
                // l'ID reçu est le n° dans le listview et pas dnas la base de donnée!!! 'ordre inversé!!!)
                click_cours (id_base_migraine (position));
            }
        });

        listnombreevnt.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener () {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Click court
                Log.d ("setonclick", "click long " + "executed= " + String.valueOf (executed));
                // l'ID reçu est le n° dans le listview et pas dnas la base de donnée!!! 'ordre inversé!!!)
                click_long (id_base_migraine (position));
                return false;
            }
        });

        nb_de_migraines = baseMigraine.NombreMigraine ();
        tnombreevnt = (TextView) findViewById (R.id.T_nombre_evnt);
        tnombreevnt.setText (String.format ("%d", nb_de_migraines));

        /* --------------------------------------------------------------------------------
            Gestion des boutons
        --------------------------------------------------------------------------------- */
        nouveau = (Button) findViewById (R.id.B_new_event);
        nouveau.setOnClickListener (new OnClickListener () {
            @Override
            public void onClick(View view) {
                //On créé l'Intent qui va nous permettre d'afficher l'autre Activity
                //Mettez le nom de l'Activity dans la quelle vous êtes actuellement
                // en parametre: le numéro de la migraine en cours

                Intent Nouvelle_migraine = new Intent (getApplicationContext (), Nouvelle_Migraine.class);
                Nouvelle_migraine.putExtra ("last", migraine_en_cours);
                startActivityForResult (Nouvelle_migraine, CODE_RETOUR);
            }
        });

        b_historique = (Button) findViewById (R.id.B_Historique);
        b_historique.setOnClickListener ((new OnClickListener () {
            @Override
            public void onClick(View view) {
                if (!executed) {
                    executed = true;
                    Intent Vue_Historique = new Intent (getApplicationContext (), Historique.class);
                    Vue_Historique.putExtra("last", migraine_en_cours);
                    startActivityForResult (Vue_Historique, CODE_RETOUR);
                }
            }
        }));

        b_graphique = (Button) findViewById (R.id.B_graphique);
        b_graphique.setOnClickListener (new OnClickListener () {
            @Override
            public void onClick(View v) {
                if (!executed) {
                    executed = true;
                    Intent Vue_Graphique = new Intent (getApplicationContext (), Graphique.class);
                    Vue_Graphique.putExtra("last", migraine_en_cours);
                    startActivityForResult (Vue_Graphique, CODE_RETOUR);
                }
            }
        });
    }

    int id_base_migraine(int ID) {
        // Recherche de l'identifiant dans la base migraine, en fonction de l'ID du listview
        // ID=0 => premier du listview mais dermier de la base
        String nom = listevent_Adapter.getItemNomWithID (ID);
        return baseMigraine.getMigraineWithNom (nom).getId ();
    }

    void click_cours(int ID) {
        //On créé l'Intent qui va nous permettre d'afficher l'autre Activity
        //Mettre le nom de l'Activity dans la quelle vous êtes actuellement
        donnees = baseMigraine.getMigraineWithId (ID);
        if (donnees.getetat () == 1) {
            reprise_migraine (ID);
        } else if (donnees.getetat () == 2) {
            historique_migraine (ID);
        } else {
            Toast.makeText (getApplicationContext (),
                    R.string.erreur_get_etat_cours,
                    Toast.LENGTH_LONG);
        }
    }

    void click_long(int ID) {
        //On créé l'Intent qui va nous permettre d'afficher l'autre Activity
        //Mettre le nom de l'Activity dans la quelle vous êtes actuellement
        donnees = baseMigraine.getMigraineWithId (ID);
        if (donnees.getetat () == 1) {
            reprise_migraine (ID);
        } else if (donnees.getetat () == 2) {
            historique_migraine (ID);
        } else {
            Toast.makeText (getApplicationContext (),
                    R.string.erreur_get_etat_long,
                    Toast.LENGTH_LONG);
        }
    }

    void reprise_migraine(int ID) {
        if (!executed) {
            executed = true;
            Intent Migraine_En_Cours = new Intent (getApplicationContext (), Migraine_en_cours.class);
            Migraine_En_Cours.putExtra ("last", ID);
            startActivityForResult (Migraine_En_Cours, CODE_RETOUR);
        }
    }

    void historique_migraine(int ID) {
        newInstanceDialog (DIALOG_HISTORIQUE_UNITAIRE, ID);
    }

    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater ();
        menuInflater.inflate (R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        super.onOptionsItemSelected (item);
        switch (item.getItemId ()) {
            case R.id.M_Config:
                if (!executed) {
                    executed = true;
                    intent = new Intent (this, Configuration.class);
                    startActivity (intent);
                }
                return true;
            case R.id.M_Info:
                newInstanceDialog (DIALOG_INFORMATION, 0);
                return true;
            case R.id.M_Quit:
                // finish ();
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //on regarde quelle Activity a répondu
        String valeur;
        int nb_de_migraines;
        baseMigraine.open ();
        nb_de_migraines = baseMigraine.NombreMigraine ();
// Actualisation du Listview => cf méthode
        switch (requestCode) {
            case CODE_RETOUR:

                //On regarde qu'elle est la réponse envoyée et en fonction de la réponse on affiche un message différent.
                switch (resultCode) {
                    case 0:
                        // on ne passe ici que si l'utilisateur annule son action!!!
//                        Log.d("Retour des classe","request code= "+ requestCode + " / resultcode=0 mais on ne devrait pas arriver ici!");
                        executed=false;
                    case RETOUR_NOUVELLE_MIGRAINE: // retour de L'Activity Nouvel Migraine
                        executed = false;
                        if (nb_de_migraines > 0) {
                            Migraine donnees = baseMigraine.getMigraineWithId (nb_de_migraines);

                            tnombreevnt.setText (String.format ("%d", nb_de_migraines));
                            tnombreevnt.forceLayout ();
                            baseMigraine.modifie_list_unitaite (this, arrayList_Evenements, donnees, true);
                        }
//                        listevent_Adapter.notifyDataSetChanged ();
                        listevent_Adapter.updateReceiveList ();

                        break;
                    case RETOUR_MIGRAINE_EN_COURS: // retour de Migraine en cours
                        executed = false;
//ici modification du listview nombre de migraine
                        Migraine donnees = baseMigraine.getMigraineWithId (nb_de_migraines);
                        baseMigraine.modifie_list_unitaite (this, arrayList_Evenements, donnees, false);
                        listevent_Adapter.updateReceiveList ();

                        break;
                    case RETOUR_CONFIGURATION: // retour de Configuration
                        executed = false;

                        break;
                    case RETOUR_HISTORIQUE: // retour de Historique
                        executed = false;

                        break;
                    case RETOUR_GRAPHIQUE: // retour de Graphique
                        executed = false;

                        break;
                }
                Log.d ("Code de retour", "requestCode= " + requestCode + " / resultcode= " + resultCode + " / executed= " + String.valueOf (executed));
        }
    }

    public void newInstanceDialog(int identifiant, int ID) {
        Dialog dialog;
        //En fonction de l'identifiant de la boîte qu'on veut créer
        switch (identifiant) {
            case DIALOG_INFORMATION:
                // On construit la première boîte de dialogue

                Builder alert = new AlertDialog.Builder (this);
                alert.setIcon (R.mipmap.encephale);
                alert.setTitle (R.string.app_name);
                alert.setCancelable (true);
                alert.setMessage (getResources ().getString (R.string.copyleft));
                alert.setPositiveButton (getResources ().getString (R.string.copyleft_ok), new OkOnClickListener ());
                dialog = alert.create ();
                dialog.show ();
                break;
            case DIALOG_HISTORIQUE_UNITAIRE:
                // boite de dialog revisitée pour l'historique d'une migraine (click long)
                MydialogBoxHistoriqueUnitaire dialogbox = new MydialogBoxHistoriqueUnitaire ();
                dialogbox.mListener = this;
                dialogbox.get_id_base_de_donnees (ID);
                dialogbox.show (getFragmentManager (), "");

                break;
        }

    }

    @Override
    public void setOnSubmitListener(String arg) {

    }

    private final class OkOnClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

        }
    }
}
