package es.nellagames.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MilestoneActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private int resumeQuestionIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestone);

        mediaPlayer = MediaPlayer.create(this, R.raw.music_achievement);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        int milestone = getIntent().getIntExtra("milestone", 0);
        // Recuperar índice de pregunta si viene del quiz
        resumeQuestionIndex = getIntent().getIntExtra("resume_question_index", -1);

        ImageView avatarCelebrating = findViewById(R.id.avatar_celebrating);
        TextView milestoneMessage = findViewById(R.id.milestone_message);

        if (milestone >= 70) {
            milestoneMessage.setText("Great! You reached " + milestone + " magic points!");
            avatarCelebrating.setImageResource(R.drawable.wizard);
        } else if (milestone >= 60) {
            milestoneMessage.setText("Legendary! You reached " + milestone + " magic points!");
            avatarCelebrating.setImageResource(R.drawable.delfin);
        } else if (milestone >= 50) {
            milestoneMessage.setText("Amazing! You reached " + milestone + " magic points!");
            avatarCelebrating.setImageResource(R.drawable.boy);
        } else {
            milestoneMessage.setText("Congratulations! You reached a new milestone!");
            avatarCelebrating.setImageResource(R.drawable.celebrate);
        }

        Button magicShopButton = findViewById(R.id.button_magic_shop);
        magicShopButton.setOnClickListener(v -> {
            stopMusic();
            Intent intent = new Intent(MilestoneActivity.this, MagicShopActivity.class);
            startActivity(intent);
            finish();
        });

        Button mainMenuButton = findViewById(R.id.button_main_menu);
        mainMenuButton.setOnClickListener(v -> {
            stopMusic();
            Intent intent = new Intent(MilestoneActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        Button continueButton = findViewById(R.id.button_continue);
        continueButton.setOnClickListener(v -> {
            stopMusic();
            Intent intent = new Intent(MilestoneActivity.this, QuizGroupActivity.class);
            if (resumeQuestionIndex != -1) {
                intent.putExtra("resume_question_index", resumeQuestionIndex);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Animar 10 estrellas (como en tu main) para la experiencia mágica
        animateFadeStar(findViewById(R.id.star_1), 0);
        animateFadeStar(findViewById(R.id.star_2), 350);
        animateFadeStar(findViewById(R.id.star_3), 650);
        animateFadeStar(findViewById(R.id.star_4), 900);
        animateFadeStar(findViewById(R.id.star_5), 1150);
        animateFadeStar(findViewById(R.id.star_6), 1400);
        animateFadeStar(findViewById(R.id.star_7), 410);
        animateFadeStar(findViewById(R.id.star_8), 950);
        animateFadeStar(findViewById(R.id.star_9), 1110);
        animateFadeStar(findViewById(R.id.star_10), 550);
    }

    private void animateFadeStar(final ImageView star, int delay) {
        if (star == null) return;
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(star, "alpha", 0f, 1f);
        fadeIn.setDuration(1100);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(star, "alpha", 1f, 0f);
        fadeOut.setDuration(1200);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(fadeIn, fadeOut);
        set.setInterpolator(new android.view.animation.LinearInterpolator());
        set.setStartDelay(delay);
        set.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                set.start(); // Loop infinito
            }
        });
        set.start();
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        stopMusic();
        super.onDestroy();
    }
}
