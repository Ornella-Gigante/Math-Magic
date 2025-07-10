package es.nellagames.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import es.nellagames.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SharedPreferences and get best score inside onCreate
        SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
        int bestScore = prefs.getInt("best_score", 0);

        // Example: Show bestScore in a TextView (uncomment and add your TextView in XML if needed)
        // TextView bestScoreView = findViewById(R.id.text_best_score);
        // bestScoreView.setText("Best Score: " + bestScore);

        Button startButton = findViewById(R.id.button_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChallengeActivity.class);
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
    }
}
