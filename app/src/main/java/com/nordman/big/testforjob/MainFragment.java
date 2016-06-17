package com.nordman.big.testforjob;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
        Log.d("LOG","...data loaded");
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

            ((ExpandableListAdapter)parent.getExpandableListAdapter()).groupSelected = groupPosition;
            ((ExpandableListAdapter)parent.getExpandableListAdapter()).childSelected = childPosition;
            v.setBackgroundResource(R.color.colorAccent);
            View oldView = ((ExpandableListAdapter)parent.getExpandableListAdapter()).oldView;

            if (oldView != null) oldView.setBackgroundResource(R.color.colorWhite);
            v.setBackgroundResource(R.color.colorAccent);

            return true;
        }
    };

}
