package es.nellagames.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ChallengeActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button option1, option2, option3, option4;
    private int correctAnswer;
    private int score = 0;
    private int questionCount = 0;
    private final int totalQuestions = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        questionTextView = findViewById(R.id.text_question);
        option1 = findViewById(R.id.button_option1);
        option2 = findViewById(R.id.button_option2);
        option3 = findViewById(R.id.button_option3);
        option4 = findViewById(R.id.button_option4);

        loadNewQuestion();

        View.OnClickListener answerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                int selectedAnswer = Integer.parseInt(clickedButton.getText().toString());
                if (selectedAnswer == correctAnswer) {
                    score++;
                }
                questionCount++;
                if (questionCount < totalQuestions) {
                    loadNewQuestion();
                } else {
                    Intent intent = new Intent(ChallengeActivity.this, ResultsActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("total", totalQuestions);
                    startActivity(intent);
                    finish();
                }
            }
        };

        option1.setOnClickListener(answerClickListener);
        option2.setOnClickListener(answerClickListener);
        option3.setOnClickListener(answerClickListener);
        option4.setOnClickListener(answerClickListener);
    }

    private void loadNewQuestion() {
        // Simple addition questions for demo
        int a = (int) (Math.random() * 10) + 1;
        int b = (int) (Math.random() * 10) + 1;
        correctAnswer = a + b;
        questionTextView.setText("What is " + a + " + " + b + "?");

        // Set options including correct answer and random numbers
        int correctPosition = (int) (Math.random() * 4);
        int[] options = new int[4];
        for (int i = 0; i < 4; i++) {
            if (i == correctPosition) {
                options[i] = correctAnswer;
            } else {
                options[i] = (int) (Math.random() * 20) + 1;
            }
        }

        option1.setText(String.valueOf(options[0]));
        option2.setText(String.valueOf(options[1]));
        option3.setText(String.valueOf(options[2]));
        option4.setText(String.valueOf(options[3]));
    }
}
