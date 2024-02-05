package com.android.buddy;

import android.os.Bundle;
import android.view.View;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;


public class MainFragment extends Fragment {

    GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.mapfragment, container, false);
    }
}