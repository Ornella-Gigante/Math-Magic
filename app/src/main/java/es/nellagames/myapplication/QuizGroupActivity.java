package es.nellagames.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioButton;
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
    boolean preguntaRespondida;

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

    private int[] correctAnswers = {2, 2, 1, 0, 2, 2, 0, 3, 1, 2};

    private String[] lastOptionTexts = new String[4];

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

        RadioGroup radioGroup = findViewById(R.id.radio_group);
        RadioButton[] radioButtons = new RadioButton[]{
                findViewById(R.id.radio_button1),
                findViewById(R.id.radio_button2),
                findViewById(R.id.radio_button3),
                findViewById(R.id.radio_button4)
        };

        // ✅ CORRECCIÓN: Cargar la primera pregunta al iniciar
        loadQuestion(questionView, radioButtons, radioGroup);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == -1 || preguntaRespondida) return;
            preguntaRespondida = true;
            int correctIndex = correctAnswers[currentQuestionIndex];
            int selectedIdx = -1;

            for (int idx = 0; idx < radioButtons.length; idx++) {
                RadioButton rb = radioButtons[idx];
                if (rb.getId() == checkedId) selectedIdx = idx;
                rb.setEnabled(false);
            }

            String correctText = radioButtons[correctIndex].getText().toString();

            if (selectedIdx == correctIndex) {
                radioButtons[selectedIdx].setBackgroundResource(R.drawable.btn_correct);
                radioButtons[selectedIdx].setText("That's correct!");
                magicPoints += 50;
                prefs.edit().putInt("magic_points", magicPoints).apply();
            } else if (selectedIdx != -1) {
                radioButtons[selectedIdx].setBackgroundResource(R.drawable.btn_incorrect);
                radioButtons[selectedIdx].setText("Ups! That's wrong!");
                radioButtons[correctIndex].setBackgroundResource(R.drawable.btn_correct);
            }

            radioGroup.postDelayed(() -> {
                for (int i = 0; i < radioButtons.length; i++) {
                    radioButtons[i].setBackgroundResource(R.drawable.default_option_bg);
                    radioButtons[i].setText(options[currentQuestionIndex][i]);
                    radioButtons[i].setEnabled(true);
                }
                radioGroup.clearCheck();
                currentQuestionIndex++;
                preguntaRespondida = false;
                if (currentQuestionIndex < questions.length) {
                    loadQuestion(questionView, radioButtons, radioGroup);
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
            }, 1300);
        });

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

    private void loadQuestion(TextView questionView, RadioButton[] radioButtons, RadioGroup radioGroup) {
        questionView.setText(questions[currentQuestionIndex]);
        for (int i = 0; i < radioButtons.length; i++) {
            lastOptionTexts[i] = options[currentQuestionIndex][i];
            radioButtons[i].setText(options[currentQuestionIndex][i]);
            radioButtons[i].setBackgroundResource(R.drawable.default_option_bg);
            radioButtons[i].setEnabled(true);
        }
        radioGroup.clearCheck();
        preguntaRespondida = false;
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
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                set.start();
            }
        });
        set.start();
    }
}
