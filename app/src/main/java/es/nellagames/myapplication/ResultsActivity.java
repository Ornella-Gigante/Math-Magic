package es.nellagames.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 5);

        TextView resultText = findViewById(R.id.text_result);
        resultText.setText("You scored " + score + " out of " + total + "!");

        Button playAgainButton = findViewById(R.id.button_play_again);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, ChallengeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button mainMenuButton = findViewById(R.id.button_main_menu);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
        int bestScore = prefs.getInt("best_score", 0);
        if (score > bestScore) {
            prefs.edit().putInt("best_score", score).apply();
        }

    }
}
