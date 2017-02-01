package org.suinot.migraine;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static org.suinot.migraine.Point_Entree.activity;

/**
 * Created by remi on 21/01/17.
 * Customisation du listener sur le listview dialog_historique_unitaire
*/

public class CustomAdapter_historique_unitaire extends BaseAdapter {
    Context context;
    // store the context (as an inflated layout)
    private LayoutInflater inflater;
    // store the resource (typically list_item.xml)
    private int resource;
    // store (a reference to) the data
    private ArrayList<Item_historique_unitaire> data;

    /**
     * Default constructor. Creates the new Adaptor object to
     * provide a ListView with data.
     *
     * @param context
     * @param resource
     * @param data
     */
    CustomAdapter_historique_unitaire (Context context, int resource, ArrayList<Item_historique_unitaire> data) {
        this.inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.data = data;
    }

    public Context getActivity() {
        return activity;
    }

    // MyViewHolder va nous permettre de ne pas devoir rechercher
    // les vues à chaque appel de getView, nous gagnons ainsi en performance
    private class MyViewHolder {
        TextView textViewdouleur, textViewmedicament, textViewcommentaire;
    }


    @Override
    public int getCount() {
        return this.data.size ();
    }

    // retourne un élément de notre liste en fonction de sa position
    @Override
    public Object getItem(int position) {
        return this.data.get (position);
    }

    // retourne le nom de l'élément (R.id.item_event_nom) en fonction de la position
    public String getItemCommentaireWithID(int ID) {
        Item_historique_unitaire list = this.data.get (ID);
        return list.get_item_commentaire ();
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
        if (this.data.get (position) == null) {
            return view;
        }
        // pull out the object
        Item_historique_unitaire item = this.data.get (position);

        // extract the view object
        View viewElement = view.findViewById (R.id.valeur_item_historique_unitaire_douleur);
        // cast to the correct type
        TextView tv = (TextView) viewElement;
        tv.setText (String.valueOf(item.valeur_douleur) );

        // set the value
        viewElement = view.findViewById (R.id.valeur_item_historique_unitaire_medicament);
        tv = (TextView) viewElement;
        tv.setText (item.nom_medicament);

        viewElement = view.findViewById (R.id.valeur_item_historique_unitaire_commentaire);
        tv = (TextView) viewElement;
        tv.setText (item.commentaire);

        // return the final view object
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        // au premier appel ConvertView est null, on inflate notre layout
        if (convertView == null) {
            view = this.inflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }
       return this.bindData (view, position);
    }
}


