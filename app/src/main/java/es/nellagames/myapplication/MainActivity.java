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
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ---- MÚSICA DE INICIO ----
        mediaPlayer = MediaPlayer.create(this, R.raw.music_intro);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        // Mostrar el mejor puntaje al iniciar la actividad
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

        // ---- Animación dinámica para estrellas ----
        ImageView star1 = findViewById(R.id.moving_star1);
        if (star1 != null) {
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(star1, "translationY", 0f, 45f, 0f, -45f, 0f);
            animator1.setDuration(4000);
            animator1.setInterpolator(new LinearInterpolator());
            animator1.setRepeatCount(ValueAnimator.INFINITE);
            animator1.start();
        }

        ImageView star2 = findViewById(R.id.moving_star2);
        if (star2 != null) {
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(star2, "translationY", 0f, 35f, 0f, -35f, 0f);
            animator2.setDuration(3500);
            animator2.setInterpolator(new LinearInterpolator());
            animator2.setRepeatCount(ValueAnimator.INFINITE);
            animator2.start();
        }

        ImageView star3 = findViewById(R.id.moving_star3);
        if (star3 != null) {
            ObjectAnimator animator3 = ObjectAnimator.ofFloat(star3, "translationX", 0f, 35f, 0f, -35f, 0f);
            animator3.setDuration(3800);
            animator3.setInterpolator(new LinearInterpolator());
            animator3.setRepeatCount(ValueAnimator.INFINITE);
            animator3.start();
        }

        ImageView star4 = findViewById(R.id.moving_star4);
        if (star4 != null) {
            ObjectAnimator animator4 = ObjectAnimator.ofFloat(star4, "translationX", 0f, 45f, 0f, -45f, 0f);
            animator4.setDuration(4200);
            animator4.setInterpolator(new LinearInterpolator());
            animator4.setRepeatCount(ValueAnimator.INFINITE);
            animator4.start();
        }
    }

    // Muestra el best score actualizado cada vez que se vuelve al menú principal
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
