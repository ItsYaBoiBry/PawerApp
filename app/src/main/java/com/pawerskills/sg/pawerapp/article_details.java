package com.pawerskills.sg.pawerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class article_details extends AppCompatActivity {
    Toolbar toolbar;
    Button btnCollectPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCollectPoints = findViewById(R.id.btnCollectPoints);

        btnCollectPoints.setVisibility(View.VISIBLE);

        btnCollectPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),quiz.class);
                startActivity(in);
            }
        });

    }
}
