package com.nordman.big.testforjob;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FavoritesFragment extends Fragment implements CountryBundleHandler{
    private CountryBundle countryBundle;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        countryBundle = new CountryBundle(this.getActivity(),this);
        Log.d("LOG","...FavoritesFragment.onCreateView");

        return view;
    }

    @Override
    public void onResume() {
        Log.d("LOG","...onResume");
        super.onResume();
    }

    @Override
    public void onDataLoaded() {
        Log.d("LOG","...data loaded FavoritesFragment");
    }

    private class ItemAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final int resource;
        private final String[] values;

        public ItemAdapter(Context context, int resource, String[] values) {
            super(context, resource, values);
            this.context = context;
            this.resource = resource;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(resource, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.lblFavoritesItem);
            textView.setText(values[position]);

            return rowView;
        }
    }

    public void refreshFavorites() {
        Log.d("LOG","...refreshFavorites()...");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        ListView listView = (ListView) getActivity().findViewById(R.id.lview);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        TextView txtList = (TextView) view.findViewById(R.id.lblFavoritesItem);
                        Log.d("LOG",txtList.getText().toString());

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor ed = prefs.edit();
                        ed.putString(txtList.getText().toString(),"off");
                        ed.apply();

                        refreshFavorites();
                    }
                }
        );

        ArrayList<String> filteredList = new ArrayList<String>();
        for (String temp : countryBundle.listAllChildren) {
            if (prefs.getString(temp,"off").equals("on")) {
                filteredList.add(temp);
            }
        }

        String[] array = filteredList.toArray(new String[filteredList.size()]);
        ItemAdapter adapter1 = new ItemAdapter(getContext(),R.layout.favorites_item, array);

        listView.setAdapter(adapter1);
    }
}
