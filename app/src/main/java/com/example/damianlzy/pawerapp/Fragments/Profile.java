package com.example.damianlzy.pawerapp.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.damianlzy.pawerapp.MainActivity;
import com.example.damianlzy.pawerapp.R;
import com.example.damianlzy.pawerapp.edit_profile;
import com.example.damianlzy.pawerapp.sign_in;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends Fragment {

    SharedPreferences sessionManager;
    public static final String SESSION = "login_status";
    public static final String SESSION_ID = "session";
    TextView tvProfile, tvPoints, tvUsername;
    CircleImageView civProfile;
    Button btnEditProfile;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvProfile = view.findViewById(R.id.tvProfile);
        tvPoints = view.findViewById(R.id.tvPoints);
        tvUsername = view.findViewById(R.id.tvUsername);
        civProfile = view.findViewById(R.id.profile_image);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        Glide.with(Profile.this).load(R.drawable.profile_image_police).into(civProfile);
        sessionManager = getContext().getSharedPreferences(SESSION, 0);

        tvUsername.setText(sessionManager.getString("user_name",""));


        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),edit_profile.class);
                startActivity(i);
            }
        });


        return view;
    }
}
