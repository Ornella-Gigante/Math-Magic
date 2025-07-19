package es.nellagames.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.view.animation.LinearInterpolator;

public class QuizGroupActivity extends AppCompatActivity {

    private int magicPoints;
    private int currentQuestionIndex = 0;
    private MediaPlayer mediaPlayer;

    // Preguntas matemáticas variadas en inglés, incluyendo problemas narrados
    private String[] questions = {
            "What is 3 + 5?",
            "Emily has 3 apples and buys 4 more. How many apples does she have now?",
            "What is 12 - 7?",
            "Liam has 10 candies. He wants to share them equally between him and 4 friends. How many candies does each child get?",
            "What is 4 × 2?",
            "There are 12 cookies on a plate. If Mia eats 5 of them, how many cookies are left?",
            "What is 20 ÷ 5?",
            "A farmer has 6 baskets. Each basket contains 8 oranges. How many oranges does the farmer have altogether?",
            "Which is the largest number: 9, 13, 7, 11?",
            "Fill in the blank: 5 + __ = 9"
    };

    // Opciones múltiples para cada pregunta
    private String[][] options = {
            {"6", "7", "8", "9"},
            {"5", "6", "7", "8"},
            {"3", "4", "5", "6"},
            {"2", "3", "5", "10"},
            {"6", "7", "8", "9"},
            {"5", "6", "7", "8"},
            {"2", "3", "4", "5"},
            {"14", "24", "36", "48"},
            {"9", "13", "7", "11"},
            {"2", "3", "4", "5"}
    };

    // Índice de la opción correcta (empezando desde 0)
    private int[] correctAnswers = {2, 2, 1, 0, 2, 2, 0, 3, 1, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_group);

        mediaPlayer = MediaPlayer.create(this, R.raw.music_ingame);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
        magicPoints = prefs.getInt("magic_points", 0);

        TextView questionView = findViewById(R.id.text_question);
        Button[] optionButtons = {
                findViewById(R.id.option1),
                findViewById(R.id.option2),
                findViewById(R.id.option3),
                findViewById(R.id.option4)
        };

        loadQuestion(questionView, optionButtons);

        for (int i = 0; i < optionButtons.length; i++) {
            final int idx = i;
            optionButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (idx == correctAnswers[currentQuestionIndex]) {
                        magicPoints += 10;
                        prefs.edit().putInt("magic_points", magicPoints).apply();
                        if (magicPoints % 50 == 0) {
                            if (mediaPlayer != null) {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                            }
                            Intent intent = new Intent(QuizGroupActivity.this, MilestoneActivity.class);
                            intent.putExtra("milestone", magicPoints);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.length) {
                        loadQuestion(questionView, optionButtons);
                    } else {
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                        Intent intent = new Intent(QuizGroupActivity.this, ResultsActivity.class);
                        intent.putExtra("score", magicPoints / 10);
                        intent.putExtra("total", questions.length);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

        // Activa el parpadeo animado para las estrellas decorativas si están en tu layout XML
        animateFadeStar(findViewById(R.id.star_1), 0);
        animateFadeStar(findViewById(R.id.star_2), 350);
        animateFadeStar(findViewById(R.id.star_3), 880);
        animateFadeStar(findViewById(R.id.star_4), 1500);
        animateFadeStar(findViewById(R.id.star_5), 2040);
        animateFadeStar(findViewById(R.id.star_6), 860);
        animateFadeStar(findViewById(R.id.star_7), 1120);
        animateFadeStar(findViewById(R.id.star_8), 1710);
        animateFadeStar(findViewById(R.id.star_9), 950);
    }

    private void loadQuestion(TextView questionView, Button[] optionButtons) {
        questionView.setText(questions[currentQuestionIndex]);
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[currentQuestionIndex][i]);
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
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    /**
     * Animación fade in/fade out para estrellas decorativas en bucle.
     */
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
                set.start(); // Loop infinito
            }
        });
        set.start();
    }
}
