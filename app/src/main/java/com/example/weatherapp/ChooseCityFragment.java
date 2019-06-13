package com.example.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChooseCityFragment extends ListFragment {

    int currentPosition = 0;
    String cityName;
    SharedPreferences mSettings;
    NavigationView navigationView;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_city);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Cities,
                android.R.layout.simple_list_item_activated_1);
        setListAdapter(adapter);
        mSettings = getActivity().getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("CurrentCity", 0);
        }
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentCity", currentPosition);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        currentPosition = position;
        cityName = l.getItemAtPosition(position).toString();
        showFragment2();
    }

    private void showFragment2() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment detail = new WeatherTodayFragment();
        ft.addToBackStack(null);
        ft.replace(R.id.fragment_container, detail);
        ft.commit();
    }

    private void saveCityName() {
        SharedPreferences.Editor ed = mSettings.edit();
        ed.putString(MainActivity.APP_CITY_NAME, cityName);
        ed.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveCityName();
    }


}
