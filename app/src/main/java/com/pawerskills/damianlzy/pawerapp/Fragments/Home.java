package com.pawerskills.damianlzy.pawerapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pawerskills.damianlzy.pawerapp.R;
import com.pawerskills.damianlzy.pawerapp.happenings;


public class Home extends Fragment {



    Button btnHappenings;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnHappenings = view.findViewById(R.id.btnOne);

        btnHappenings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getContext(),happenings.class);
                startActivity(in);
            }
        });

        return view;
    }
}

