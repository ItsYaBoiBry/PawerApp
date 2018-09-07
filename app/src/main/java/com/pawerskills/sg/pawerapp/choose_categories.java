package com.pawerskills.sg.pawerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pawerskills.sg.pawerapp.functions.DialogMenu;

import java.util.ArrayList;

public class choose_categories extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14;
    int counter = 0;
    boolean firstClicked = false;
    ArrayList<Integer> allButtonIDs = new ArrayList<>();
    ArrayList<String> selectedCat = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_categories);
        setUpButtons();


    }

    @Override
    public void onClick(View v) {
        Button btnClicked = findViewById(v.getId());
        if(btnClicked.getBackground().getAlpha() == 128 && counter == 3){
            Toast.makeText(choose_categories.this,"Please select only 3",Toast.LENGTH_SHORT).show();
        } else {
            if (firstClicked == false) {
                translucentAll();
                firstClicked = true;

                counter++;
                btnClicked.setTextColor(getResources().getColor(R.color.black));
                btnClicked.getBackground().setAlpha(255);
                selectedCat.add(btnClicked.getText().toString());
            } else {

                if (btnClicked.getBackground().getAlpha() == 255) {
                    counter--;
                    btnClicked.setTextColor(getResources().getColor(R.color.blackalpha));
                    btnClicked.getBackground().setAlpha(128);
                    selectedCat.remove(btnClicked.getText().toString());

                } else {
                    counter++;
                    btnClicked.setTextColor(getResources().getColor(R.color.black));
                    btnClicked.getBackground().setAlpha(255);
                    selectedCat.add(btnClicked.getText().toString());
                }

                if (counter == 3) {
                    DialogMenu dialogMenu = new DialogMenu(choose_categories.this);
                    dialogMenu.setListener(new DialogMenu.OnDialogMenuListener() {
                        @Override
                        public void onConfirm() {
                            if(counter == 3){
                                //store chosen categories in DB from selectedCat
                                //intent towards MainActivityi
                                Intent i = new Intent(choose_categories.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(choose_categories.this,"Please select " + (3-counter) + " categories",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        }


        Log.i("chosenCat",selectedCat+"");
    }

    public void translucentAll(){
        for(int i = 0; i < allButtonIDs.size(); i++){
            Log.i("opacitytest","3");
            Button btn = findViewById(allButtonIDs.get(i));
            btn.getBackground().setAlpha(128);
            btn.setTextColor(getResources().getColor(R.color.blackalpha));
        }
    }

    public void opaqueAll(){
        for(int i = 0; i < allButtonIDs.size(); i++){
            Log.i("opacitytest","3");
            Button btn = findViewById(allButtonIDs.get(i));
            btn.getBackground().setAlpha(255);
            btn.setTextColor(getResources().getColor(R.color.blackalpha));
        }
    }

    public void setUpButtons(){
        btn1 = findViewById(R.id.btnOne);
        btn2 = findViewById(R.id.btnTwo);
        btn3 = findViewById(R.id.btnThree);
        btn4 = findViewById(R.id.btnFour);
        btn5 = findViewById(R.id.btnFive);
        btn6 = findViewById(R.id.btnSix);
        btn7 = findViewById(R.id.btnSeven);
        btn8 = findViewById(R.id.btnEight);
        btn9 = findViewById(R.id.btnNine);
        btn10 = findViewById(R.id.btnTen);
        btn11 = findViewById(R.id.btnEleven);
        btn12 = findViewById(R.id.btnTwelve);
        btn13 = findViewById(R.id.btnThirteen);
        btn14 = findViewById(R.id.btnFourteen);
        btn1.setTag(1);
        btn1.setOnClickListener(this);
        allButtonIDs.add(btn1.getId());
        btn2.setTag(2);
        btn2.setOnClickListener(this);
        allButtonIDs.add(btn2.getId());
        btn3.setTag(3);
        btn3.setOnClickListener(this);
        allButtonIDs.add(btn3.getId());
        btn4.setTag(4);
        btn4.setOnClickListener(this);
        allButtonIDs.add(btn4.getId());
        btn5.setTag(5);
        btn5.setOnClickListener(this);
        allButtonIDs.add(btn5.getId());
        btn6.setTag(6);
        btn6.setOnClickListener(this);
        allButtonIDs.add(btn6.getId());
        btn7.setTag(7);
        btn7.setOnClickListener(this);
        allButtonIDs.add(btn7.getId());
        btn8.setTag(8);
        btn8.setOnClickListener(this);
        allButtonIDs.add(btn8.getId());
        btn9.setTag(9);
        btn9.setOnClickListener(this);
        allButtonIDs.add(btn9.getId());
        btn10.setTag(10);
        btn10.setOnClickListener(this);
        allButtonIDs.add(btn10.getId());
        btn11.setTag(11);
        btn11.setOnClickListener(this);
        allButtonIDs.add(btn11.getId());
        btn12.setTag(12);
        btn12.setOnClickListener(this);
        allButtonIDs.add(btn12.getId());
        btn13.setTag(13);
        btn13.setOnClickListener(this);
        allButtonIDs.add(btn13.getId());
        btn14.setTag(14);
        btn14.setOnClickListener(this);
        allButtonIDs.add(btn14.getId());


    }


}
