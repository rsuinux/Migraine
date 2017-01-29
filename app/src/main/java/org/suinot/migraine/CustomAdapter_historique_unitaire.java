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
    private ArrayList<Item_historique_unitaire> list_historique_unitaire;

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
        this.list_historique_unitaire = data;
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
        return this.list_historique_unitaire.size ();
    }

    // retourne un élément de notre liste en fonction de sa position
    @Override
    public Object getItem(int position) {
        return this.list_historique_unitaire.get (position);
    }

    // retourne le nom de l'élément (R.id.item_event_nom) en fonction de la position
    public String getItemCommentaireWithID(int ID) {
        Item_historique_unitaire list = this.list_historique_unitaire.get (ID);
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
        if (this.list_historique_unitaire.get (position) == null) {
            return view;
        }
        // pull out the object
        Item_historique_unitaire item = this.list_historique_unitaire.get (position);

        // extract the view object
        View viewElement = view.findViewById (R.id.valeur_item_historique_unitaire_douleur);
        // cast to the correct type
        TextView tv = (TextView) viewElement;
        tv.setText (item.valeur_douleur);

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
        MyViewHolder mViewHolder = null;

        // au premier appel ConvertView est null, on inflate notre layout
        if (convertView == null) {
            LayoutInflater mInflater;
            mInflater = (LayoutInflater)
                    getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.dialog_historique_unitaire, parent, false);

            mViewHolder = new MyViewHolder();

            // nous attribuons comme tag notre MyViewHolder à convertView
            convertView.setTag(mViewHolder);
        } else {
            // convertView n'est pas null, nous récupérons notre objet MyViewHolder
            // et évitons ainsi de devoir retrouver les vues à chaque appel de getView
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        // nous plaçons dans notre MyViewHolder les vues de notre layout
        /* Première ligne */
        // nombre de douleur
        mViewHolder.textViewdouleur = (TextView) .findViewById(R.id.valeur_item_historique_unitaire_douleur);
        // nombre de medicament
        mViewHolder.textViewmedicament = (TextView) convertView.findViewById(R.id.valeur_item_historique_unitaire_medicament);

            /* Seconde ligne */
        // commentaire(s) associé(s)
        mViewHolder.textViewcommentaire = (TextView) convertView.findViewById(R.id.valeur_item_historique_unitaire_commentaire);

        // nous récupérons l'item de la liste demandé par getView
        Item_historique_unitaire listItem = (Item_historique_unitaire) getItem(position);
Log.d("custom_adapteur", " valeur douleur: " + String.valueOf ( listItem.get_valeur_douleur () ));
        // nous pouvons attribuer à nos vues les valeurs de l'élément de la liste
        // attention: listItem.get_valeur_douleur () est un int
        mViewHolder.textViewdouleur.setText( String.valueOf ( listItem.get_valeur_douleur () ) );
        mViewHolder.textViewmedicament.setText(listItem.get_nom_medicament ());
        mViewHolder.textViewcommentaire.setText(listItem.get_item_commentaire ());

        // nous retournos la vue de l'item demandé
        return convertView;
    }
}


