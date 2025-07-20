package es.nellagames.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
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

        magicShopPlayer = MediaPlayer.create(this, R.raw.music_intro);
        magicShopPlayer.setLooping(true);
        magicShopPlayer.start();

        pointsView = findViewById(R.id.text_points);
        unlockAvatar1 = findViewById(R.id.button_unlock_avatar1);
        avatar1 = findViewById(R.id.avatar1);

        // Puedes ocultar el botón si solo usas el tap en la imagen:
        // unlockAvatar1.setVisibility(View.GONE);

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

        // Interacción principal: tap sobre el avatar para desbloquear
        avatar1.setOnClickListener(new View.OnClickListener() {
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
                    // Snackbar bonito y personalizado
                    Snackbar snackbar = Snackbar.make(v, "Avatar unlocked!", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(0xFF43EA7F); // Verde exitoso
                    TextView text = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    text.setTextColor(0xFFFFFFFF);
                    text.setTextSize(19f);
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    snackbar.show();
                } else if (!avatar1Unlocked) {
                    Snackbar snackbar = Snackbar.make(v, "Not enough points!", Snackbar.LENGTH_SHORT);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(0xFFE53935); // Rojo error
                    TextView text = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    text.setTextColor(0xFFFFFFFF);
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    snackbar.show();
                }
                // Si ya está desbloqueado, puedes mostrar otra animación/mensaje si quieres
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshShop();
        if (magicShopPlayer != null && !magicShopPlayer.isPlaying()) {
            magicShopPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (magicShopPlayer != null && magicShopPlayer.isPlaying()) {
            magicShopPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
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
