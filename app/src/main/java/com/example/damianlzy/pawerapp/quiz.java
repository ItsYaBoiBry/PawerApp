package com.example.damianlzy.pawerapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class quiz extends AppCompatActivity {
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(quiz.this);
                LayoutInflater li = LayoutInflater.from(quiz.this);
                final View gtnc = li.inflate(R.layout.points_dialog, null);
                builder.setCancelable(true);
                builder.setView(gtnc);
                Button btnDone = gtnc.findViewById(R.id.btnDone);
                TextView tvPoints = gtnc.findViewById(R.id.tvPoints);
                final AlertDialog dialog  = builder.create();
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(getApplicationContext(),happenings.class);
                        startActivity(in);
                    }
                });
                dialog.show();
            }
        });
    }
}
