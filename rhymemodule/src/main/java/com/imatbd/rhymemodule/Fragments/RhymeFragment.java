package com.imatbd.rhymemodule.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imatbd.rhymemodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RhymeFragment extends Fragment {

    private RecyclerView rvPoet;


    public RhymeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_rhyme, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        rvPoet = view.findViewById(R.id.rv_poet);
    }

}
