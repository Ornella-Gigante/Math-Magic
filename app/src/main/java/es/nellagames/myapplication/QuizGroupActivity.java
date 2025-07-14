package es.nellagames.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuizGroupActivity extends AppCompatActivity {

    private int magicPoints;
    private int currentQuestionIndex = 0;
    private String[] questions = {
            "What is 2 + 3?",
            "What is 7 - 4?",
            "What is 5 x 2?",
            "What is 12 / 4?"
    };
    private String[][] options = {
            {"4", "5", "6", "7"},
            {"2", "3", "4", "5"},
            {"7", "8", "10", "12"},
            {"2", "3", "4", "5"}
    };
    private int[] correctAnswers = {1, 1, 2, 1}; // Index of correct option

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_group);

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
                        // Aquí puedes navegar a la pantalla de resultados o reiniciar el quiz
                        finish();
                    }
                }
            });
        }
    }

    private void loadQuestion(TextView questionView, Button[] optionButtons) {
        questionView.setText(questions[currentQuestionIndex]);
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[currentQuestionIndex][i]);
        }
    }
}
