package com.example.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Fragment1 extends ListFragment {

    boolean isExistFragment2;
    int currentPosition = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Cities,
                android.R.layout.simple_list_item_activated_1);
        setListAdapter(adapter);
        View detailsFrame = getActivity().findViewById(R.id.temp_value);
        isExistFragment2 = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("CurrentCity", 0);
        }
        if (isExistFragment2) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showFragment2();
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentCity", currentPosition);
    }

    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        currentPosition = position;
        showFragment2();
    }

    private void showFragment2() {
        Fragment2 detail = getFragmentManager().findFragmentById(R.id.temp_value) == null ?
                null : (Fragment2)getFragmentManager().findFragmentById(R.id.temp_value);

            getListView().setItemChecked(currentPosition, true);
            if (detail == null || detail.getIndex() != currentPosition) {
                detail = Fragment2.create(currentPosition);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (isExistFragment2) {
                    ft.replace(R.id.temp_value, detail);
                }
                else{
                    ft.replace(R.id.fragment_container, detail);
                }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
    }
}
