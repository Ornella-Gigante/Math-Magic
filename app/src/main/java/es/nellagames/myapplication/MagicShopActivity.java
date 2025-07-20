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
    private boolean avatar1Unlocked, avatar2Unlocked, avatar3Unlocked, avatar4Unlocked, avatar5Unlocked;
    private TextView pointsView;
    private Button unlockAvatar1, unlockAvatar2, unlockAvatar3, unlockAvatar4, unlockAvatar5;
    private ImageView avatar1, avatar2, avatar3, avatar4, avatar5;
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
        unlockAvatar2 = findViewById(R.id.button_unlock_avatar2);
        unlockAvatar3 = findViewById(R.id.button_unlock_avatar3);
        unlockAvatar4 = findViewById(R.id.button_unlock_avatar4);
        unlockAvatar5 = findViewById(R.id.button_unlock_avatar5);
        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);
        avatar5 = findViewById(R.id.avatar5);

        Button mainMenuButton = findViewById(R.id.button_main_menu);
        mainMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(MagicShopActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        refreshShop();

        avatar1.setOnClickListener(v -> unlockAvatar(1, 100, v));
        avatar2.setOnClickListener(v -> unlockAvatar(2, 100, v));
        avatar3.setOnClickListener(v -> unlockAvatar(3, 100, v));
        avatar4.setOnClickListener(v -> unlockAvatar(4, 100, v));
        avatar5.setOnClickListener(v -> unlockAvatar(5, 50, v));

        unlockAvatar1.setOnClickListener(v -> unlockAvatar(1, 100, v));
        unlockAvatar2.setOnClickListener(v -> unlockAvatar(2, 100, v));
        unlockAvatar3.setOnClickListener(v -> unlockAvatar(3, 100, v));
        unlockAvatar4.setOnClickListener(v -> unlockAvatar(4, 100, v));
        unlockAvatar5.setOnClickListener(v -> unlockAvatar(5, 50, v));
    }

    private void unlockAvatar(int avatarIndex, int cost, View v) {
        if (magicPoints >= cost && !isAvatarUnlocked(avatarIndex)) {
            magicPoints -= cost;
            setAvatarUnlocked(avatarIndex, true);
            SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
            prefs.edit()
                    .putInt("magic_points", magicPoints)
                    .putBoolean(getAvatarKey(avatarIndex), true)
                    .apply();
            refreshShop();
            showSnackbar(v, "Avatar unlocked!", true);
        } else if (!isAvatarUnlocked(avatarIndex)) {
            showSnackbar(v, "Not enough points!", false);
        }
    }

    private boolean isAvatarUnlocked(int idx) {
        switch (idx) {
            case 1: return avatar1Unlocked;
            case 2: return avatar2Unlocked;
            case 3: return avatar3Unlocked;
            case 4: return avatar4Unlocked;
            case 5: return avatar5Unlocked;
            default: return false;
        }
    }

    private void setAvatarUnlocked(int idx, boolean unlocked) {
        switch (idx) {
            case 1: avatar1Unlocked = unlocked; break;
            case 2: avatar2Unlocked = unlocked; break;
            case 3: avatar3Unlocked = unlocked; break;
            case 4: avatar4Unlocked = unlocked; break;
            case 5: avatar5Unlocked = unlocked; break;
        }
    }

    private String getAvatarKey(int idx) {
        return "avatar" + idx + "_unlocked";
    }

    private void showSnackbar(View v, String message, boolean success) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(success ? 0xFF43EA7F : 0xFFE53935);
        TextView text = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        text.setTextColor(0xFFFFFFFF);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setTextSize(19f);
        snackbar.show();
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
        avatar2Unlocked = prefs.getBoolean("avatar2_unlocked", false);
        avatar3Unlocked = prefs.getBoolean("avatar3_unlocked", false);
        avatar4Unlocked = prefs.getBoolean("avatar4_unlocked", false);
        avatar5Unlocked = prefs.getBoolean("avatar5_unlocked", false);

        pointsView.setText("Magic Points: " + magicPoints);

        updateAvatarUI(avatar1, unlockAvatar1, avatar1Unlocked, "Unlock (100 pts)", 100);
        updateAvatarUI(avatar2, unlockAvatar2, avatar2Unlocked, "Unlock (100 pts)", 100);
        updateAvatarUI(avatar3, unlockAvatar3, avatar3Unlocked, "Unlock (100 pts)", 100);
        updateAvatarUI(avatar4, unlockAvatar4, avatar4Unlocked, "Unlock (100 pts)", 100);
        updateAvatarUI(avatar5, unlockAvatar5, avatar5Unlocked, "Unlock (50 pts)", 50);
    }

    private void updateAvatarUI(ImageView avatar, Button button, boolean unlocked, String unlockText, int cost) {
        if (unlocked) {
            button.setEnabled(false);
            button.setAlpha(1.0f);
            button.setText("Unlocked!");
            avatar.setAlpha(1.0f);
        } else {
            button.setEnabled(true);
            button.setAlpha(0.6f);
            button.setText(unlockText);
            avatar.setAlpha(0.5f);
        }
    }
}
