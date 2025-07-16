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

        milestoneMessage.setText("Congratulations! You reached " + milestone + " points!");

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

        // Opcional: Cambia la imagen o mensaje dependiendo del milestone
        // if (milestone >= 100) { ... }
    }
}
