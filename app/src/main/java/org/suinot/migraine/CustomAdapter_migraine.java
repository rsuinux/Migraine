package org.suinot.migraine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by remi on 18/10/16.
 * Adapter custom pour gerer les listView avec plusieurs elements dedans
 */

class CustomAdapter_migraine extends BaseAdapter {
    // store the context (as an inflated layout)
    private LayoutInflater inflater;
    // store the resource (typically list_item.xml)
    private int resource;
    // store (a reference to) the data
    private ArrayList<Item_Migraine> data;

    /**
     * Default constructor. Creates the new Adaptor object to
     * provide a ListView with data.
     *
     * @param context
     * @param resource
     * @param data
     */
    CustomAdapter_migraine(Context context, int resource, ArrayList<Item_Migraine> data) {
        this.inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return this.data.size ();
    }

    @Override
    public Object getItem(int position) {
        return this.data.get (position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // reuse a given view, or inflate a new one from the xml
        View view;

        if (convertView == null) {
            view = this.inflater.inflate (resource, parent, false);
        } else {
            view = convertView;
        }

        // bind the data to the view object
        return this.bindData (view, position);
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
        Item_Migraine item = this.data.get (position);

        // extract the view object
        View viewElement = view.findViewById (R.id.NM_Nom);
        // cast to the correct type
        TextView tv = (TextView) viewElement;
        // set the value
        tv.setText (item.NM_Nom);

        viewElement = view.findViewById (R.id.NM_Date);
        tv = (TextView) viewElement;
        tv.setText (item.NM_Date);

        viewElement = view.findViewById (R.id.NM_Heure);
        tv = (TextView) viewElement;
        tv.setText (item.NM_Heure);

        viewElement = view.findViewById (R.id.NM_Douleur);
        tv = (TextView) viewElement;
        tv.setText (item.NM_Douleur);

        viewElement = view.findViewById (R.id.NM_Medic);
        tv = (TextView) viewElement;
        tv.setText (item.NM_Medic);

        viewElement = view.findViewById (R.id.NM_Dose);
        tv = (TextView) viewElement;
        tv.setText (item.NM_Dose);

        // return the final view object
        return view;
    }
}
