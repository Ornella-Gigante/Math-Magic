package es.nellagames.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PrepareMagicActivity extends AppCompatActivity {

    private static final int DELAY_MILLIS = 5000; // Waiting time before quiz starts
    private MediaPlayer preparePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_magic);

        // Start magical music
        preparePlayer = MediaPlayer.create(this, R.raw.music_intro);
        preparePlayer.setLooping(true);
        preparePlayer.start();

        ImageView wizardAvatar = findViewById(R.id.wizard_avatar);
        TextView loadingText = findViewById(R.id.text_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preparePlayer != null) {
                    preparePlayer.stop();
                    preparePlayer.release();
                    preparePlayer = null;
                }
                Intent intent = new Intent(PrepareMagicActivity.this, QuizGroupActivity.class);
                startActivity(intent);
                finish();
            }
        }, DELAY_MILLIS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (preparePlayer != null && preparePlayer.isPlaying()) {
            preparePlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (preparePlayer != null && !preparePlayer.isPlaying()) {
            preparePlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        if (preparePlayer != null) {
            preparePlayer.release();
            preparePlayer = null;
        }
        super.onDestroy();
    }
}
