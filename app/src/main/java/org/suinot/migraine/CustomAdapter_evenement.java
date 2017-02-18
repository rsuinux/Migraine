package org.suinot.migraine;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static org.suinot.migraine.Point_Entree.activity;

// import org.suinot.migraine.Point_Entree.activity;

/**
 * Created by remi on 26/12/16.
 * Customisation du listener sur le listview item_liste_d_evement
 */

public class CustomAdapter_evenement extends BaseAdapter {
    static private String TAG = "CustomAdapter_evenement";
    Context context;
    // store the context (as an inflated layout)
    private LayoutInflater inflater;
    // store the resource (typically list_item.xml)
    private int resource;
    // store (a reference to) the data
    private ArrayList<Item_liste_d_evenement> list_event;

    public Context getActivity() {
        return activity;
    }

    // MyViewHolder va nous permettre de ne pas devoir rechercher
    // les vues à chaque appel de getView, nous gagnons ainsi en performance
    private class MyViewHolder {
        TextView textViewNom, textViewDate, textViewHeure, textViewMedicament;
        ImageView imageView;
    }

    /**
     * Default constructor. Creates the new Adaptor object to
     * provide a ListView with data.
     *
     * @param context
     * @param resource
     * @param data
     */
    CustomAdapter_evenement(Context context, int resource, ArrayList<Item_liste_d_evenement> data) {
        this.inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.list_event = data;
    }

    @Override
    public int getCount() {
        return this.list_event.size ();
    }

    // retourne un élément de notre liste en fonction de sa position
    @Override
    public Object getItem(int position) {
        return this.list_event.get (position);
    }

    // retourne le nom de l'élément (R.id.item_event_nom) en fonction de la position
    public String getItemNomWithID(int ID) {
        Item_liste_d_evenement list = this.list_event.get (ID);
        return list.get_item_Nom ();
    }

    // retourne l'id d'un élément de notre liste en fonction de sa position
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Bind the provided data to the view.
     * This is the only method not required by base adapter.
     */
    View bindData(View view, int position) {
        // make sure it's worth drawing the view
        if (this.list_event.get (position) == null) {
            return view;
        }
        // pull out the object
        Item_liste_d_evenement item = this.list_event.get (position);

        // extract the view object
        View viewElement = view.findViewById (R.id.item_event_nom);
        // cast to the correct type
        TextView tv = (TextView) viewElement;
        tv.setText (item.nom);

        // set the value
        viewElement = view.findViewById (R.id.item_event_date);
        tv = (TextView) viewElement;
        tv.setText (item.date_debut);

        viewElement = view.findViewById (R.id.item_event_heure);
        tv = (TextView) viewElement;
        tv.setText (item.heure_debut);

        viewElement = view.findViewById (R.id.item__event_nombre_medicament);
        tv = (TextView) viewElement;
        tv.setText (item.nombre_medicaments);

        // return the final view object
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder = null;

        final View result;

        // au premier appel ConvertView est null, on inflate notre layout
        if (convertView == null) {
Log.d(TAG, "convertview null");
            LayoutInflater mInflater = LayoutInflater.from (getActivity().getApplicationContext ());

            convertView = mInflater.inflate(R.layout.item_liste_d_evenement, parent, false);

            // nous plaçons dans notre MyViewHolder les vues de notre layout
            mViewHolder = new MyViewHolder();
            /* Première ligne */
            // nom
            mViewHolder.textViewNom = (TextView) convertView.findViewById(R.id.item_event_nom);
            // date
            mViewHolder.textViewDate = (TextView) convertView.findViewById(R.id.item_event_date);
            // heure
            mViewHolder.textViewHeure = (TextView) convertView.findViewById(R.id.item_event_heure);

            /* Seconde ligne */
            // medicament
            mViewHolder.textViewMedicament = (TextView) convertView.findViewById(R.id.item__event_nombre_medicament);

            /* Au bout des deux lignes: */
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_event_reprise);

            // nous attribuons comme tag notre MyViewHolder à convertView
            convertView.setTag(mViewHolder);
            result = convertView;

        } else {
            // convertView n'est pas null, nous récupérons notre objet MyViewHolder
            // et évitons ainsi de devoir retrouver les vues à chaque appel de getView
Log.d(TAG, "convertview non null");
            mViewHolder = (MyViewHolder) convertView.getTag();
            result = convertView;
        }

        // nous récupérons l'item de la liste demandé par getView
        Item_liste_d_evenement listItem = (Item_liste_d_evenement) getItem(position);

        // nous pouvons attribuer à nos vues les valeurs de l'élément de la liste
        mViewHolder.textViewNom.setText(listItem.get_item_Nom ());
        mViewHolder.textViewDate.setText(listItem.get_item_date_debut ());
        mViewHolder.textViewHeure.setText(listItem.get_item_heure_debut ());
        mViewHolder.textViewMedicament.setText(listItem.get_item_medicament ());
        mViewHolder.imageView.setImageResource(listItem.get_item_ImageId ());

        // nous retournos la vue de l'item demandé
        return result;
    }

    void updateReceiveList() {
        this.notifyDataSetChanged();
    }
}