package com.pawerskills.sg.pawerapp;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pawerskills.sg.pawerapp.functions.API;
import com.pawerskills.sg.pawerapp.functions.HttpReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class sign_in extends AppCompatActivity {

    TextView tvRegister, tvForgotPassword;
    EditText etEmail, etPassword;
    Button btnSubmit;
    LinearLayout progressbar;

    HttpReq httpRequest = new HttpReq();
    API apiLinks = new API();

    public static final String SESSION = "login_status";
    public static final String SESSION_ID = "session";
    SharedPreferences sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSubmit = findViewById(R.id.btnSignIn);
        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((etEmail.getText().toString().trim().length() > 0) && (etPassword.getText().toString().trim().length() > 0)) {
                    if (checkNetworks()) {
                        ShowProgress();
                        Login login = new Login();
                        login.execute(apiLinks.getLogin(), etEmail.getText().toString(), etPassword.getText().toString());
                    } else {
                        Toast.makeText(sign_in.this, "You are not connected to the internet", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(sign_in.this, "Enter your sign in information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(sign_in.this,register.class);
                startActivity(i);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class Login extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String results = httpRequest.PostRequest(strings[0], "user_email=" + strings[1] + "&user_pwd=" + strings[2]);

            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            HideProgress();
            Log.e("LOGIN POST EXECUTE: ", s);
            try {
                JSONObject result = new JSONObject(s);
                int status = result.getInt("status");
                if (status == 200) {
                    sessionManager = getSharedPreferences(SESSION, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sessionManager.edit();
                    editor.putString(SESSION_ID, String.valueOf(status));
                    JSONArray users = result.getJSONArray("users");
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
                    startActivity(new Intent(sign_in.this, display_video.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    //startActivity(new Intent(sign_in.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                } else if (status == 404) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(sign_in.this);
                    dialog.setCancelable(false);
                    dialog.setMessage("You have not registered yet!\nPlease register!");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialogue = dialog.create();
                    dialogue.show();
                } else if ( status == 201 ){

                    AlertDialog.Builder dialog = new AlertDialog.Builder(sign_in.this);
                    dialog.setCancelable(false);
                    dialog.setMessage("Your account has not been approved by your parent!");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialogue = dialog.create();
                    dialogue.show();



                } else if ( status == 202 ){

                    AlertDialog.Builder dialog = new AlertDialog.Builder(sign_in.this);
                    dialog.setCancelable(false);
                    dialog.setMessage("Your account has been rejected by your parent, Please contact an Admin!");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialogue = dialog.create();
                    dialogue.show();



                } else {
                    String message = result.getString("message");
                    Toast.makeText(sign_in.this,message,Toast.LENGTH_SHORT).show();
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
    public void HideProgress(){
        progressbar.setVisibility(View.GONE);
    }
    public void ShowProgress(){
        progressbar.setVisibility(View.VISIBLE);
    }
}
