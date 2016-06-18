package com.nordman.big.testforjob;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blunderer.materialdesignlibrary.interfaces.ListView;

/**
 * Created by s_vershinin on 17.06.2016.
 *
 */
public class MainFragment extends Fragment implements CountryBundleHandler {
    private static final String ARG_TEXT = "text";
    private CountryBundle countryBundle;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    public MainFragment() {
    }

    public static MainFragment newInstance(String text) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TEXT, text);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        countryBundle = new CountryBundle(this.getActivity(),this);
        return view;
    }

    @Override
    public void onDataLoaded() {
        Log.d("LOG","...data loaded MainFragment");
        expListView = (ExpandableListView) getActivity().findViewById(R.id.lvExp);
        listAdapter = new ExpandableListAdapter(this.getActivity(), countryBundle.listDataHeader, countryBundle.listDataChild);
        expListView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(onClickListener);
    }

    ExpandableListView.OnChildClickListener onClickListener = new ExpandableListView.OnChildClickListener(){
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

            //int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor ed = prefs.edit();
            ed.putInt("groupSelected",groupPosition);
            ed.putInt("childSelected",childPosition);
            ((ExpandableListAdapter)parent.getExpandableListAdapter()).groupSelected = groupPosition;
            ((ExpandableListAdapter)parent.getExpandableListAdapter()).childSelected = childPosition;

            v.setBackgroundResource(R.color.colorAccent);
            View oldView = ((ExpandableListAdapter)parent.getExpandableListAdapter()).oldView;

            if (oldView != null) oldView.setBackgroundResource(R.color.colorWhite);
            v.setBackgroundResource(R.color.colorAccent);
            ((ExpandableListAdapter)parent.getExpandableListAdapter()).oldView = v;

            TextView txtListChild = (TextView) v.findViewById(R.id.lblListItem);
            Log.d("LOG",txtListChild.getText().toString());
            ImageView imageStarOn = (ImageView) v.findViewById(R.id.imageStarOn);
            ImageView imageStarOff = (ImageView) v.findViewById(R.id.imageStarOff);
            if (imageStarOn.getVisibility()==View.VISIBLE) {
                imageStarOn.setVisibility(View.GONE);
                imageStarOff.setVisibility(View.VISIBLE);
                ed.putString(txtListChild.getText().toString(),"off");
            } else {
                imageStarOn.setVisibility(View.VISIBLE);
                imageStarOff.setVisibility(View.GONE);
                ed.putString(txtListChild.getText().toString(),"on");
            }

            ed.apply();
            return true;
        }
    };

}
