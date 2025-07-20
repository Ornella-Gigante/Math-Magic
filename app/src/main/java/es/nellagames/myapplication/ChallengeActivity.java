// ChallengeActivity.java
package es.nellagames.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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
                Button clicked = (Button) v;
                int selected = Integer.parseInt(clicked.getText().toString());
                boolean correct = (selected == correctAnswer);
                if (correct) {
                    score++;
                    int pointsEarned = 10;
                    SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
                    int magicPoints = prefs.getInt("magic_points", 0);
                    magicPoints += pointsEarned;
                    prefs.edit().putInt("magic_points", magicPoints).apply();

                    // Lanzar milestone si corresponde
                    int[] milestones = {10, 20, 25, 30, 50};
                    int lastMilestone = prefs.getInt("last_milestone", 0);
                    for (int milestone : milestones) {
                        if (magicPoints >= milestone && lastMilestone < milestone) {
                            prefs.edit().putInt("last_milestone", milestone).apply();
                            Intent milestoneIntent = new Intent(ChallengeActivity.this, MilestoneActivity.class);
                            milestoneIntent.putExtra("milestone", milestone);
                            startActivity(milestoneIntent);
                            break;
                        }
                    }
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
        int a = (int) (Math.random() * 10) + 1;
        int b = (int) (Math.random() * 10) + 1;
        correctAnswer = a + b;
        questionTextView.setText("What is " + a + " + " + b + "?");

        int correctPos = (int) (Math.random() * 4);
        int[] options = new int[4];
        for (int i = 0; i < 4; i++) {
            if (i == correctPos) {
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
