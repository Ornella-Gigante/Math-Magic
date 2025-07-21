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
            // ... your 15 questions ...
    };

    private String[][] options = {
            // ... your 15 sets of options ...
    };

    private int[] correctAnswers = {
            // ... your 15 correct answer indices ...
    };

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

        // Si venimos de un milestone, usamos resume_question_index
        int resumeIndex = getIntent().getIntExtra("resume_question_index", -1);
        if (resumeIndex != -1) {
            currentQuestionIndex = resumeIndex;
        } else {
            currentQuestionIndex = prefs.getInt("current_question_index", 0);
        }

        TextView questionView = findViewById(R.id.text_question);

        RadioGroup radioGroup = findViewById(R.id.radio_group);
        RadioButton[] radioButtons = new RadioButton[]{
                findViewById(R.id.radio_button1),
                findViewById(R.id.radio_button2),
                findViewById(R.id.radio_button3),
                findViewById(R.id.radio_button4)
        };

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

            if (selectedIdx == correctIndex) {
                radioButtons[selectedIdx].setBackgroundResource(R.drawable.btn_correct);
                radioButtons[selectedIdx].setText("That's correct!");
                magicPoints += 10;
                prefs.edit().putInt("magic_points", magicPoints).apply();

                // Guarda el índice de la siguiente pregunta (no la actual)
                prefs.edit().putInt("current_question_index", currentQuestionIndex + 1).apply();

                // Milestone check
                int[] milestones = {30, 50, 80};
                int lastMilestone = prefs.getInt("last_milestone", 0);
                for (int milestone : milestones) {
                    if (magicPoints >= milestone && lastMilestone < milestone) {
                        prefs.edit().putInt("last_milestone", milestone).apply();
                        Intent milestoneIntent = new Intent(QuizGroupActivity.this, MilestoneActivity.class);
                        milestoneIntent.putExtra("milestone", milestone);
                        milestoneIntent.putExtra("resume_question_index", currentQuestionIndex + 1); // +1 para la siguiente pregunta
                        startActivity(milestoneIntent);
                        finish();
                        return;
                    }
                }
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
                    prefs.edit().remove("current_question_index").apply();
                    Intent intent = new Intent(QuizGroupActivity.this, ResultsActivity.class);
                    intent.putExtra("score", magicPoints / 10);
                    intent.putExtra("total", questions.length);
                    startActivity(intent);
                    finish();
                }
            }, 1300);
        });

        // Star animations
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
