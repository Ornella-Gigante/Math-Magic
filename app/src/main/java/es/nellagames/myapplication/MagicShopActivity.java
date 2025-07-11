package es.nellagames.myapplication;

import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_shop);

        SharedPreferences prefs = getSharedPreferences("math_magic_prefs", MODE_PRIVATE);
        magicPoints = prefs.getInt("magic_points", 0);
        avatar1Unlocked = prefs.getBoolean("avatar1_unlocked", false);

        TextView pointsView = findViewById(R.id.text_points);
        pointsView.setText("Magic Points: " + magicPoints);

        Button unlockAvatar1 = findViewById(R.id.button_unlock_avatar1);
        ImageView avatar1 = findViewById(R.id.avatar1);

        if (avatar1Unlocked) {
            unlockAvatar1.setEnabled(false);
            unlockAvatar1.setText("Unlocked!");
            avatar1.setAlpha(1.0f);
        } else {
            avatar1.setAlpha(0.5f);
        }

        unlockAvatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (magicPoints >= 100 && !avatar1Unlocked) {
                    magicPoints -= 100;
                    avatar1Unlocked = true;
                    prefs.edit()
                            .putInt("magic_points", magicPoints)
                            .putBoolean("avatar1_unlocked", true)
                            .apply();
                    pointsView.setText("Magic Points: " + magicPoints);
                    unlockAvatar1.setEnabled(false);
                    unlockAvatar1.setText("Unlocked!");
                    avatar1.setAlpha(1.0f);
                    Toast.makeText(MagicShopActivity.this, "Avatar unlocked!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MagicShopActivity.this, "Not enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
