package com.pawerskills.damianlzy.pawerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.pawerskills.damianlzy.pawerapp.functions.API;
import com.pawerskills.damianlzy.pawerapp.functions.HttpReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class display_video_editprofile extends AppCompatActivity {

    Button btnUpdate;
    EditText etFName, etLName, etUsername;
    Toolbar toolbar;

    public static final String SESSION = "login_status";
    public static final String SESSION_ID = "session";
    SharedPreferences sessionManager;

    HttpReq httpRequest = new HttpReq();
    API api = new API();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_video_editprofile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sessionManager = getSharedPreferences(SESSION, Context.MODE_PRIVATE);

        btnUpdate = findViewById(R.id.btnUpdate);
        etFName = findViewById(R.id.etFirstName);
        etLName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUsername);

        etFName.setText(sessionManager.getString("user_firstname",""));
        etLName.setText(sessionManager.getString("user_lastname",""));
        etUsername.setText(sessionManager.getString("user_username",""));


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String firstName = etFName.getText().toString();
                //String lastName = etLName.getText().toString();
                //String username = etUsername.getText().toString();

                if((etFName.getText().toString().trim().length() > 0) && (etLName.getText().toString().trim().length() > 0) && (etUsername.getText().toString().trim().length() > 0)) {
                    if (checkNetworks()) {
                        Update update = new Update();
                        String email = sessionManager.getString("user_email","");
                        update.execute(api.updateProfile(), email, etFName.getText().toString(), etLName.getText().toString(), etUsername.getText().toString());
                    } else {
                        Toast.makeText(display_video_editprofile.this, "You are not connected to the internet", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(display_video_editprofile.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    public class Update extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String results = httpRequest.PostRequest(strings[0], "email=" + strings[1] + "&firstname=" + strings[2] + "&lastname=" + strings[3] + "&username=" + strings[4]);

            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("UPDATE POST EXECUTE: ", s);
            try {
                JSONObject result = new JSONObject(s);
                int status = result.getInt("status");
                if (status == 200) {
                    sessionManager = getSharedPreferences(SESSION, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sessionManager.edit();
                    editor.putString(SESSION_ID, String.valueOf(status));
                    JSONArray users = result.getJSONArray("result");
                    JSONObject user = users.getJSONObject(0);
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
                    startActivity(new Intent(display_video_editprofile.this, display_video.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    //startActivity(new Intent(sign_in.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                } else if (status == 404) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(display_video_editprofile.this);
                    dialog.setCancelable(false);
                    dialog.setMessage("You have not registered yet!\nPlease register!");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialogue = dialog.create();
                    dialogue.show();
                } else {
                    String message = result.getString("message");
                    Toast.makeText(display_video_editprofile.this,message,Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkNetworks() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
