package es.nellagames.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.view.animation.LinearInterpolator;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private void animateFadeStar(final ImageView star, int delay) {
        if (star == null) return;
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(star, "alpha", 0f, 1f);
        fadeIn.setDuration(1200);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(star, "alpha", 1f, 0f);
        fadeOut.setDuration(1200);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(fadeIn, fadeOut);
        set.setInterpolator(new LinearInterpolator());
        set.setStartDelay(delay);
        set.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                set.start(); // loop infinito
            }
        });
        set.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.music_intro);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
        int bestScore = prefs.getInt("best_score", 0);
        TextView bestScoreView = findViewById(R.id.text_best_score);
        if (bestScoreView != null) {
            bestScoreView.setText("Best Score: " + bestScore);
        }

        Button startButton = findViewById(R.id.button_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrepareMagicActivity.class);
                startActivity(intent);
            }
        });

        Button shopButton = findViewById(R.id.button_shop);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MagicShopActivity.class);
                startActivity(intent);
            }
        });

        // Anima todas las estrellas (IDs deben coincidir con el XML)
        animateFadeStar(findViewById(R.id.star_1), 0);
        animateFadeStar(findViewById(R.id.star_2), 500);
        animateFadeStar(findViewById(R.id.star_3), 1000);
        animateFadeStar(findViewById(R.id.star_4), 1600);
        animateFadeStar(findViewById(R.id.star_5), 2100);
        animateFadeStar(findViewById(R.id.star_6), 1100);
        animateFadeStar(findViewById(R.id.star_7), 800);
        animateFadeStar(findViewById(R.id.star_8), 1450);
        animateFadeStar(findViewById(R.id.star_9), 1250);
        animateFadeStar(findViewById(R.id.star_10), 1850);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
        int bestScore = prefs.getInt("best_score", 0);
        TextView bestScoreView = findViewById(R.id.text_best_score);
        if (bestScoreView != null) {
            bestScoreView.setText("Best Score: " + bestScore);
        }
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
