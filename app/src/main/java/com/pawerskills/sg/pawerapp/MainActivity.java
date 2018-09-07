package com.pawerskills.sg.pawerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pawerskills.sg.pawerapp.Fragments.Home;
import com.pawerskills.sg.pawerapp.Fragments.Profile;
import com.pawerskills.sg.pawerapp.Fragments.Settings;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {



    SharedPreferences sessionManager;
    public static final String SESSION = "login_status";
    public static final String SESSION_ID = "session";
    DrawerLayout drawerLayout;
    FragmentTransaction ft;
    ImageView title_home_logo,title_profile_logo,title_edit_profile_logo,title_happenings_logo,title_settings_logo;
    TextView title, user_name, user_points;
    CircleImageView profileimage;
    Button btnLogout;

    public void updateprofile(){
        //update drawer profile here
//        sessionManager = getSharedPreferences(SESSION, Context.MODE_PRIVATE);
//        Glide.with(MainActivity.this).load(sessionManager.getString("user_image", "https://www.greenravolution.com/API/uploads/291d5076443149a4273f0199fea9db39a3ab4884.png")).into(profileimage);
//        user_points.setText(String.valueOf("Points: " + sessionManager.getInt("user_total_points", 0)) + " (No rank yet)");
//        user_name.setText(sessionManager.getString("user_name",""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title_home_logo = findViewById(R.id.home_page_logo);
        title_edit_profile_logo = findViewById(R.id.edit_profile_page_logo);
        title_profile_logo = findViewById(R.id.profile_page_logo);
        title_settings_logo = findViewById(R.id.settings_page_logo);
        title_happenings_logo = findViewById(R.id.happenings_page_logo);

        //title_home_logo.setVisibility(View.GONE);
        title_edit_profile_logo.setVisibility(View.GONE);
        title_profile_logo.setVisibility(View.GONE);
        title_settings_logo.setVisibility(View.GONE);
        title_happenings_logo.setVisibility(View.GONE);

        sessionManager = getSharedPreferences(SESSION, Context.MODE_PRIVATE);

        NavigationView navigationView = findViewById(R.id.left_drawer);
        View headerview = navigationView.getHeaderView(0);
        LinearLayout userProfile = headerview.findViewById(R.id.userProfile);
        profileimage = headerview.findViewById(R.id.profile_image);

        btnLogout = findViewById(R.id.btnLogout);

        //Glide.with(MainActivity.this).load(sessionManager.getString("user_image", "https://www.greenravolution.com/API/uploads/291d5076443149a4273f0199fea9db39a3ab4884.png")).into(profileimage);
        Glide.with(MainActivity.this).load(R.drawable.profile_image_police).into(profileimage);

        user_name = headerview.findViewById(R.id.user_name);
        user_name.setText(sessionManager.getString("user_name",""));

        user_points = headerview.findViewById(R.id.user_points);
//        user_points.setText(String.valueOf("Points: " + sessionManager.getInt("user_total_points", 0)) + " (No rank yet)");
        user_points.setText("120 points");


        configureNavigationDrawer();
        configureToolbar();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager = getSharedPreferences(SESSION, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sessionManager.edit();
                editor.putString(SESSION_ID, null);
                editor.apply();
                Intent in = new Intent(MainActivity.this,sign_in.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                finish();
            }
        });


        if (savedInstanceState == null) {
            replacefragment(new Home());
        }
    }


    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setHomeAsUpIndicator(R.drawable.menu_img);
        actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void configureNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navView = findViewById(R.id.left_drawer);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();

                switch (itemId) {
                    case R.id.main:
                        Log.i("MainActivity", "clicked on Home item");
                        replacefragment(new Home());
                        title_home_logo.setVisibility(View.VISIBLE);
                        title_profile_logo.setVisibility(View.GONE);
                        title_settings_logo.setVisibility(View.GONE);
                        title_edit_profile_logo.setVisibility(View.GONE);
                        title_happenings_logo.setVisibility(View.GONE);
                        //TextView tvHome = navView.findViewById(R.id.tvH)
                        CloseDrawer();
                        return true;

                    case R.id.profile:
                        Log.i("MainActivity", "clicked on profile item");
                        replacefragment(new Profile());
                        title_home_logo.setVisibility(View.GONE);
                        title_profile_logo.setVisibility(View.VISIBLE);
                        title_settings_logo.setVisibility(View.GONE);
                        title_edit_profile_logo.setVisibility(View.GONE);
                        title_happenings_logo.setVisibility(View.GONE);
                        CloseDrawer();
                        return true;

                    case R.id.settings:
                        Log.i("MainActivity", "clicked on settings item");
                        replacefragment(new Settings());
                        title_home_logo.setVisibility(View.GONE);
                        title_profile_logo.setVisibility(View.GONE);
                        title_settings_logo.setVisibility(View.VISIBLE);
                        title_edit_profile_logo.setVisibility(View.GONE);
                        title_happenings_logo.setVisibility(View.GONE);
                        CloseDrawer();
                        return true;


                }

                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            // Android home
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    CloseDrawer();
                    return true;
                } else {
                    OpenDrawer();
                    return true;
                }
                // manage other entries if you have it ...
            case R.id.share:
                Log.i("MainActivity", "clicked on cart");
                //startActivity(new Intent(this, ActivityCart.class));
                return true;

        }

        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_menu, menu);//Menu Resource, Menu
        return true;
    }

    public void replacefragment(Fragment fragment) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }

    public void CloseDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void OpenDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateprofile();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateprofile();
    }
}
