package es.nellagames.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 5);

        TextView resultText = findViewById(R.id.text_result);
        resultText.setText("You scored " + score + " out of " + total + "!");

        // Update the best score if necessary
        SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
        int bestScore = prefs.getInt("best_score", 0);
        if (score > bestScore) {
            prefs.edit().putInt("best_score", score).apply();
            bestScore = score;
        }

        // Music for the results screen
        mediaPlayer = MediaPlayer.create(this, R.raw.music_achievement); // place music_achievement.mp3 in res/raw
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        Button playAgainButton = findViewById(R.id.button_play_again);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                Intent intent = new Intent(ResultsActivity.this, QuizGroupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button mainMenuButton = findViewById(R.id.button_main_menu);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
