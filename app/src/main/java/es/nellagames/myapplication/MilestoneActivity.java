package es.nellagames.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MilestoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestone);

        int milestone = getIntent().getIntExtra("milestone", 0);

        ImageView avatarCelebrating = findViewById(R.id.avatar_celebrating);
        TextView milestoneMessage = findViewById(R.id.milestone_message);

        // Cambiar mensaje e imagen según milestone
        if (milestone >= 200) {
            milestoneMessage.setText("Legendary! You reached " + milestone + " magic points!");
            avatarCelebrating.setImageResource(R.drawable.avatar3); // Prepara esta imagen en tus drawables
        } else if (milestone >= 100) {
            milestoneMessage.setText("Amazing! You reached " + milestone + " magic points!");
            avatarCelebrating.setImageResource(R.drawable.avatar2);    // Prepara esta imagen en tus drawables
        } else if (milestone >= 50) {
            milestoneMessage.setText("Great! You reached " + milestone + " magic points!");
            avatarCelebrating.setImageResource(R.drawable.avatar1);  // Usa tu imagen de celebración básica
        } else {
            milestoneMessage.setText("Congratulations! You reached a new milestone!");
            avatarCelebrating.setImageResource(R.drawable.celebrate);
        }

        Button magicShopButton = findViewById(R.id.button_magic_shop);
        magicShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MilestoneActivity.this, MagicShopActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button mainMenuButton = findViewById(R.id.button_main_menu);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MilestoneActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
