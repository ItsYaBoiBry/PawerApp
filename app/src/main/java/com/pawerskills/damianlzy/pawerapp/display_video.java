package com.pawerskills.damianlzy.pawerapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.widget.TextView;
import android.widget.VideoView;

//import com.google.android.youtube.player.YouTubeBaseActivity;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerView;

//public class display_video extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
public class display_video extends  AppCompatActivity{

    Button btnLogout,btnMoveon, btnHome, btnEditProfile;
    //TextView tv;
    SharedPreferences sessionManager;
    public static final String SESSION = "login_status";
    public static final String SESSION_ID = "session";

    //public static final String API_KEY = "AIzaSyD357y9iOb8PsDQxXD2jti68qlf2CE50FU";
    //Youtube Test
//    public static final String VIDEO_ID = "t0qu3qEkvQo";

    //Local mp4 video
    private static final String VIDEO_SAMPLE = "pawer_app_video";
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_video);

        sessionManager = getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        btnLogout = findViewById(R.id.btnLogout);
        btnMoveon = findViewById(R.id.btnMoveon);
        btnHome = findViewById(R.id.btnHome);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        //tv = findViewById(R.id.textView);
        //String name = sessionManager.getString("user_name","");
        //tv.setText("Welcome " + name);

        //YouTubePlayerView youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_player);
        //youTubePlayerView.initialize(API_KEY,this);

        mVideoView = findViewById(R.id.videoview);

        mVideoView.start();

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                Uri videoUri = getMedia(VIDEO_SAMPLE);
                mVideoView.setVideoURI(videoUri);
                mVideoView.start();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sessionManager = getSharedPreferences(SESSION, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sessionManager.edit();
//                editor.putString(SESSION_ID, null);
//                editor.apply();
//                Intent in = new Intent(display_video.this,sign_in.class);
//                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(in);
//                finish();

                final AlertDialog.Builder dialog = new AlertDialog.Builder(display_video.this);
                dialog.setCancelable(false);
                dialog.setTitle("Logout");
                dialog.setMessage("Are you sure you want to log out?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sessionManager = getSharedPreferences(SESSION, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sessionManager.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(display_video.this, sign_in.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialogue = dialog.create();
                dialogue.show();
            }
        });

        btnMoveon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(display_video.this,choose_categories.class);
                startActivity(i);
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(display_video.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(display_video.this,display_video_editprofile.class);
                startActivity(i);
            }
        });

    }

    private Uri getMedia(String mediaName) {
        return Uri.parse("android.resource://" + getPackageName() +
                "/raw/" + mediaName);
    }

    private void initializePlayer() {
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        mVideoView.setVideoURI(videoUri);
    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }


//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
//        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
//        /** add listeners to YouTubePlayer instance **/
//        player.setPlayerStateChangeListener(playerStateChangeListener);
//        player.setPlaybackEventListener(playbackEventListener);
//
//        /** Start buffering **/
//        if (!wasRestored) {
//            player.cueVideo(VIDEO_ID);
//            player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
//        }
//    }
//
//    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
//        @Override
//        public void onBuffering(boolean arg0) {
//        }
//        @Override
//        public void onPaused() {
//        }
//        @Override
//        public void onPlaying() {
//        }
//        @Override
//        public void onSeekTo(int arg0) {
//        }
//        @Override
//        public void onStopped() {
//        }
//    };
//
//    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
//        @Override
//        public void onAdStarted() {
//        }
//        @Override
//        public void onError(YouTubePlayer.ErrorReason arg0) {
//        }
//        @Override
//        public void onLoaded(String arg0) {
//        }
//        @Override
//        public void onLoading() {
//        }
//        @Override
//        public void onVideoEnded() {
//        }
//        @Override
//        public void onVideoStarted() {
//        }
//    };

    @Override
    protected void onStart() {
        super.onStart();

        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();

        releasePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

}

