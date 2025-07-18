package es.nellagames.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MagicShopActivity extends AppCompatActivity {

    private int magicPoints;
    private boolean avatar1Unlocked;
    private TextView pointsView;
    private Button unlockAvatar1;
    private ImageView avatar1;
    private MediaPlayer magicShopPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_shop);

        // Inicia la música suave al entrar en la Magic Shop
        magicShopPlayer = MediaPlayer.create(this, R.raw.music_intro);
        magicShopPlayer.setLooping(true);
        magicShopPlayer.start();

        pointsView = findViewById(R.id.text_points);
        unlockAvatar1 = findViewById(R.id.button_unlock_avatar1);
        avatar1 = findViewById(R.id.avatar1);

        // Botón para ir al menú principal
        Button mainMenuButton = findViewById(R.id.button_main_menu);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MagicShopActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        refreshShop();

        unlockAvatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (magicPoints >= 100 && !avatar1Unlocked) {
                    magicPoints -= 100;
                    avatar1Unlocked = true;
                    SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
                    prefs.edit()
                            .putInt("magic_points", magicPoints)
                            .putBoolean("avatar1_unlocked", true)
                            .apply();
                    refreshShop();
                    Toast.makeText(MagicShopActivity.this, "Avatar unlocked!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MagicShopActivity.this, "Not enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshShop();
        // Reanuda la música si está pausada
        if (magicShopPlayer != null && !magicShopPlayer.isPlaying()) {
            magicShopPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pausa la música al salir temporalmente de la Magic Shop
        if (magicShopPlayer != null && magicShopPlayer.isPlaying()) {
            magicShopPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        // Libera el recurso de música al destruir la actividad
        if (magicShopPlayer != null) {
            magicShopPlayer.release();
            magicShopPlayer = null;
        }
        super.onDestroy();
    }

    private void refreshShop() {
        SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
        magicPoints = prefs.getInt("magic_points", 0);
        avatar1Unlocked = prefs.getBoolean("avatar1_unlocked", false);

        pointsView.setText("Magic Points: " + magicPoints);
        if (avatar1Unlocked) {
            unlockAvatar1.setEnabled(false);
            unlockAvatar1.setText("Unlocked!");
            avatar1.setAlpha(1.0f);
        } else {
            unlockAvatar1.setEnabled(true);
            unlockAvatar1.setText("Unlock (100 pts)");
            avatar1.setAlpha(0.5f);
        }
    }
}
