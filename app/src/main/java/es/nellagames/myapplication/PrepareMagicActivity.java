package es.nellagames.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PrepareMagicActivity extends AppCompatActivity {

    private static final int DELAY_MILLIS = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_magic);

        ImageView wizardAvatar = findViewById(R.id.wizard_avatar);
        TextView loadingText = findViewById(R.id.text_loading);

        new Handler().postDelayed(() -> {
            // Detener la música global justo antes de ir al quiz
            stopService(new Intent(PrepareMagicActivity.this, MusicService.class));

            Intent intent = new Intent(PrepareMagicActivity.this, QuizGroupActivity.class);
            startActivity(intent);
            finish();
        }, DELAY_MILLIS);
    }
}
