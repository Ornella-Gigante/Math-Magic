package es.nellagames.myapplication;

import android.os.Bundle;
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
        // Puedes cambiar la imagen del avatar según el milestone si lo deseas
    }
}
