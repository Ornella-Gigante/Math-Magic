package es.nellagames.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MilestoneActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestone);

        // Música para celebraciones (achievement)
        // Pon un archivo llamado music_achievement.mp3 en res/raw/
        mediaPlayer = MediaPlayer.create(this, R.raw.music_achievement);
        mediaPlayer.setLooping(false); // Sonará una vez
        mediaPlayer.start();

        int milestone = getIntent().getIntExtra("milestone", 0);

        ImageView avatarCelebrating = findViewById(R.id.avatar_celebrating);
        TextView milestoneMessage = findViewById(R.id.milestone_message);

        // Cambiar mensaje e imagen según milestone
        if (milestone >= 200) {
            milestoneMessage.setText("Legendary! You reached " + milestone + " magic points!");
            avatarCelebrating.setImageResource(R.drawable.delfin); // Prepara esta imagen en tus drawables
        } else if (milestone >= 100) {
            milestoneMessage.setText("Amazing! You reached " + milestone + " magic points!");
            avatarCelebrating.setImageResource(R.drawable.boy);    // Prepara esta imagen en tus drawables
        } else if (milestone >= 50) {
            milestoneMessage.setText("Great! You reached " + milestone + " magic points!");
            avatarCelebrating.setImageResource(R.drawable.wizard);  // Usa tu imagen de celebración básica
        } else {
            milestoneMessage.setText("Congratulations! You reached a new milestone!");
            avatarCelebrating.setImageResource(R.drawable.celebrate);
        }

        Button magicShopButton = findViewById(R.id.button_magic_shop);
        magicShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                Intent intent = new Intent(MilestoneActivity.this, MagicShopActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button mainMenuButton = findViewById(R.id.button_main_menu);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                Intent intent = new Intent(MilestoneActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
