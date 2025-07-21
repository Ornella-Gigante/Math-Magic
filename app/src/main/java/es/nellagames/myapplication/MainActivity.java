package es.nellagames.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicia la música global solo al entrar aquí
        startService(new Intent(this, MusicService.class));

        SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
        int bestScore = prefs.getInt("best_score", 0);
        TextView bestScoreView = findViewById(R.id.text_best_score);
        if (bestScoreView != null) {
            bestScoreView.setText("Best Score: " + bestScore);
        }

        Button startButton = findViewById(R.id.button_start);
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PrepareMagicActivity.class);
            startActivity(intent);
        });

        Button shopButton = findViewById(R.id.button_shop);
        shopButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MagicShopActivity.class);
            startActivity(intent);
        });

        Button resetButton = findViewById(R.id.button_reset);
        resetButton.setOnClickListener(v -> {
            resetGameProgress();
            Toast.makeText(MainActivity.this, "Game progress reset!", Toast.LENGTH_SHORT).show();
            recreate();
        });

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
            public void onAnimationEnd(android.animation.Animator animation) { set.start(); }
        });
        set.start();
    }

    private void resetGameProgress() {
        SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
