package com.example.damianlzy.pawerapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class happenings extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView mHappeningsRecyclerView;
    private RecyclerView.Adapter mHappeningsAdapter;
    private RecyclerView.LayoutManager mHappeningsLayoutManager;
    Context mContext;

    TextView readMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happenings);
        toolbar = findViewById(R.id.toolbar);
        readMore = findViewById(R.id.tvReadMore1);

        mContext = this.getApplicationContext();
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View rootView = inflater.inflate(R.layout.activity_happenings, null, false);

//        mHappeningsRecyclerView = rootView.findViewById(R.id.happeningsRecyclerView);
//        mHappeningsRecyclerView.setNestedScrollingEnabled(false);
//        mHappeningsRecyclerView.setHasFixedSize(true);
//
//        mHappeningsLayoutManager = new LinearLayoutManager(mContext);
//        mHappeningsRecyclerView.setLayoutManager(mHappeningsLayoutManager);
//        mHappeningsAdapter = new HistoryAdapter(getDataSetHistory(), mContext);
//        mHappeningsRecyclerView.setAdapter(mHappeningsAdapter);


        //mHappeningsAdapter.notifyDataSetChanged();

        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext,article_details.class);
                startActivity(in);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
