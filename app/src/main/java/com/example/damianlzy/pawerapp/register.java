package com.example.damianlzy.pawerapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damianlzy.pawerapp.functions.API;
import com.example.damianlzy.pawerapp.functions.HttpReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class register extends AppCompatActivity {

    TextView tvSignIn,tvTerms,tvPolicy;
    EditText etFName, etLName, etEmail, etUsername, etDOB, etPassword, etCPassword, etPContact, etPEmail, etPName;
    Button btnRegister;
    CheckBox cbTermsPolicy, cbUpdates;
    LinearLayout consentLayout,progressbar;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    int userAge;
    //Boolean dateIsValid = false;
    HttpReq httpRequest = new HttpReq();
    API apiLinks = new API();

    public static final String SESSION = "login_status";
    public static final String SESSION_ID = "session";
    SharedPreferences sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvSignIn = findViewById(R.id.tvSignIn);
        tvTerms = findViewById(R.id.tvTerms);
        tvPolicy = findViewById(R.id.tvPolicy);

        etFName = findViewById(R.id.etFirstName);
        etLName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etDOB = findViewById(R.id.etDOB);
        etPassword = findViewById(R.id.etPassword);
        etCPassword = findViewById(R.id.etConfirmPassword);
        etPContact = findViewById(R.id.etParentContact);
        etPEmail = findViewById(R.id.etParentEmail);
        etPName = findViewById(R.id.etParentName);

        btnRegister = findViewById(R.id.btnRegister);

        consentLayout = findViewById(R.id.consentLayout);
        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);

        cbTermsPolicy = findViewById(R.id.checkBoxTermsPolicy);
        cbUpdates = findViewById(R.id.checkBoxUpdates);


        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(register.this, android.app.AlertDialog.THEME_HOLO_LIGHT,mDateSetListener,year,month,day);
                long milliseconds = (long) 1000*(365 * 24 * 60 * 60 * 1000);
                Date currentDate = new Date();
                Date oneHundredYearsBefore = new Date(currentDate.getTime() - milliseconds);
                dialog.getDatePicker().setMinDate(oneHundredYearsBefore.getTime());
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String sMonth = month + "";
                String sDay = day + "";
                String sYear = year + "";
                if(month < 10){
                     sMonth = "0"+sMonth;
                }
                if(day < 10){
                    sDay = "0"+sDay;
                }
                etDOB.setText(sDay + "/" + sMonth + "/" + sYear);
                checkAge(day+"",month+"",year+"");
            }
        };
//        etDOB.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String text = s.toString();
//                if(text.length() == 10) {
//                    if (text.substring(2, 3).equalsIgnoreCase("/") && text.substring(5, 6).equalsIgnoreCase("/")) {
//                        String day = text.substring(0, 2);
//                        String month = text.substring(3, 5);
//                        String year = text.substring(6);
//                        Calendar c = Calendar.getInstance();
//                        if ((Integer.parseInt(day) <= 31) && (Integer.parseInt(month) <= 12) && (Integer.parseInt(year) < c.get(Calendar.YEAR))) {
//                            checkAge(day, month, year);
//                            dateIsValid = true;
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Invalid format. Date does not exist", Toast.LENGTH_SHORT).show();
//                            dateIsValid = false;
//                        }
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Invalid format. Please use DD/MM/YYYY", Toast.LENGTH_SHORT).show();
//                        dateIsValid = false;
//                    }
//                }
////                } else {
////                    Toast.makeText(getApplicationContext(),"Invalid format. Please use DD/MM/YYYY",Toast.LENGTH_SHORT).show();
////                    dateIsValid = false;
////                }
//            }
//        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNetworks()) {
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    int ifname = etFName.getText().toString().trim().length();
                    int ilname = etLName.getText().toString().trim().length();
                    int iemail = etEmail.getText().toString().trim().length();
                    int iuser = etUsername.getText().toString().trim().length();
                    int idob = etDOB.getText().toString().trim().length();
                    int ipassword = etPassword.getText().toString().trim().length();
                    int icpassword = etCPassword.getText().toString().trim().length();

                    int ipContact = etPContact.getText().toString().trim().length();
                    int ipEmail = etPEmail.getText().toString().trim().length();
                    int ipName = etPName.getText().toString().trim().length();

                    Log.i("length of words",ifname + " " + ilname + " " + iemail + " " + iuser + " " + idob + " " + ipassword + " " + icpassword + " " + ipContact + " " + ipEmail + " " + ipName );

                    if(cbTermsPolicy.isChecked()){

                        if( (ifname > 0) && (ilname > 0) && (iemail > 0) && (iuser > 0) && (idob == 10) && (ipassword > 0) && (icpassword > 0) ){
                        //if(TextUtils.isEmpty(ifname) && TextUtils.isEmpty(ilname) && TextUtils.isEmpty(iemail) && TextUtils.isEmpty(iuser) && TextUtils.isEmpty(idob) && TextUtils.isEmpty(ipassword) && TextUtils.isEmpty(icpassword)){

                            if(userAge <= 13){
                                if( ipContact>0 &&  ipEmail>0 &&  ipName>0){
                                    if(etPEmail.getText().toString().matches(emailPattern)) {
                                        if(etEmail.getText().toString().matches(emailPattern)) {
                                            if (etPassword.getText().toString().equals(etCPassword.getText().toString())) {
                                                ShowProgress();
                                                Register doregister = new Register();
                                                doregister.execute(apiLinks.getRegister());
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Password and Confirm Password does not match", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Log.i("invalid","email wrong");
                                            Toast.makeText(getApplicationContext(),"Email is invalid",Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Parent's email is invalid",Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(),"Parent's consent is required",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.i("invalid","user is not below 13");
                                if(etEmail.getText().toString().matches(emailPattern)) {
                                    if (etPassword.getText().toString().equals(etCPassword.getText().toString())) {
                                        ShowProgress();
                                        Register doregister = new Register();
                                        doregister.execute(apiLinks.getRegister());
                                        progressbar.setVisibility(View.VISIBLE);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password and Confirm Password does not match", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.i("invalid","email wrong");
                                    Toast.makeText(getApplicationContext(),"Email is invalid",Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"Please check for empty fields",Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(),"You have to agree to the terms of use and privacy policy to use this app",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(register.this, "You are not connected to the internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                LayoutInflater li = LayoutInflater.from(register.this);
                final View gtnc = li.inflate(R.layout.pp_dialog, null);
                builder.setCancelable(true);
                builder.setView(gtnc);
                Button btnDone = gtnc.findViewById(R.id.btnDone);
                final AlertDialog dialog  = builder.create();
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        tvTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                LayoutInflater li = LayoutInflater.from(register.this);
                final View gtnc = li.inflate(R.layout.tnc_dialog, null);
                builder.setCancelable(true);
                builder.setView(gtnc);
                Button btnDone = gtnc.findViewById(R.id.btnDone);
                final AlertDialog dialog = builder.create();
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    public void checkAge(String day, String month, String year){
        int iyear = Integer.parseInt(year);
        int imonth = Integer.parseInt(month);
        int iday = Integer.parseInt(day);
        userAge = Integer.parseInt(getAge(iyear,imonth,iday));

        if(userAge <= 13){
            consentLayout.setVisibility(View.VISIBLE);
            Toast.makeText(register.this,"You will need your parent's consent to continue",Toast.LENGTH_SHORT).show();
        } else {
            consentLayout.setVisibility(View.GONE);
        }
    };

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public void HideProgress() {
        progressbar.setVisibility(View.GONE);
    }

    public void ShowProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    public boolean checkNetworks() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ib = new Intent();
        ib.putExtra("type", "0");
        setResult(1, ib);
        finish();
    }
    public class Register extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String results;
            if(userAge <= 13){
                Log.i("ageRegister","below or equals 13");
                results = httpRequest.PostRequest(strings[0]
                        , "user_firstname=" + etFName.getText().toString()
                                + "&user_lastname=" + etLName.getText().toString()
                                + "&user_username=" + etUsername.getText().toString()
                                + "&user_email=" + etEmail.getText().toString()
                                + "&user_dob=" + etDOB.getText().toString()
                                + "&user_pwd=" + etPassword.getText().toString()
                                + "&parent_contact=" + etPContact.getText().toString()
                                + "&parent_address=" + etPEmail.getText().toString()
                                + "&parent_name=" + etPName.getText().toString());
            } else {
                Log.i("ageRegister","above 13");
                results = httpRequest.PostRequest(strings[0]
                        , "user_firstname=" + etFName.getText().toString()
                                + "&user_lastname=" + etLName.getText().toString()
                                + "&user_username=" + etUsername.getText().toString()
                                + "&user_email=" + etEmail.getText().toString()
                                + "&user_dob=" + etDOB.getText().toString()
                                + "&user_pwd=" + etPassword.getText().toString());
//                                + "&parent_contact=" + null
//                                + "&parent_address=" + null
//                                + "&parent_name=" + null);
            }
            try{
                if(results!=null){
                    JSONObject resultObject = new JSONObject(results);
                    int status = resultObject.getInt("status");
                    Log.i("status",status+"");
                    if (status == 200) {
                        JSONArray users = resultObject.getJSONArray("result");
                        JSONObject user = users.getJSONObject(0);
                        String userID = "" + user.getInt("id");
                    }
                }
            } catch (JSONException e){
                e.printStackTrace();
            }

            return results;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            HideProgress();
            progressbar.setVisibility(View.GONE);
            Log.e("SIGNUP POST EXECUTE: ", ""+s);
            try {
                JSONObject result = new JSONObject(s);
                int status = result.getInt("status");

                if (status == 404) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(register.this);
                    dialog.setCancelable(false);
                    dialog.setMessage("You have already registered!\nPlease Login via our login page");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(register.this,sign_in.class));
                        }
                    });
                    AlertDialog dialogue = dialog.create();
                    dialogue.show();

                } else if (status == 200) {
                    sessionManager = getSharedPreferences(SESSION, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sessionManager.edit();
                    editor.putString(SESSION_ID, String.valueOf(status));
                   Log.i("jsonReturn",result.toString());
                    JSONArray users = result.getJSONArray("users");
                    JSONObject user = users.getJSONObject(0);
                    if(user.getString("status_id").equalsIgnoreCase("2")){
                        editor.putString("user_name", user.getString("user_firstname")+" "+user.getString("user_lastname"));
                        editor.putString("user_firstname", user.getString("user_firstname"));
                        editor.putString("user_lastname", user.getString("user_lastname"));
                        editor.putString("user_email", user.getString("user_email"));
                        editor.putString("user_username", user.getString("user_username"));
                        editor.putString("user_dob", user.getString("user_dob"));
                        editor.putString("parent_name", user.getString("parent_name"));
                        editor.putString("parent_contact", user.getString("parent_contact"));
                        editor.putString("parent_address", user.getString("parent_address"));
                        editor.apply();
                        startActivity(new Intent(register.this, display_video.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    } else {

                        AlertDialog.Builder dialog = new AlertDialog.Builder(register.this);
                        LayoutInflater li = LayoutInflater.from(register.this);
                        final View gtnc = li.inflate(R.layout.dialog_userhasregistered, null);
                        dialog.setCancelable(true);
                        dialog.setView(gtnc);
                        dialog.setPositiveButton("I understand", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(register.this,sign_in.class));
                                    }
                                });

                        AlertDialog dialogue = dialog.create();
                        dialogue.show();


                    }

                }else{
                    Log.e("STATUS CODE: ",status+"");
                    Log.e("STATUS MESSAGE: ", result.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
