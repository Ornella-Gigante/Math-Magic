package es.nellagames.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PrepareMagicActivity extends AppCompatActivity {

    private static final int DELAY_MILLIS = 2300; // Tiempo de espera antes de pasar al quiz, ajustable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_magic); // Usa el layout proporcionado

        // Opcional: puedes agregar alguna animación simple aquí si lo deseas, por ejemplo:
        ImageView wizardAvatar = findViewById(R.id.wizard_avatar);
        // wizardAvatar.setAlpha(0f); // Animación opcional
        // wizardAvatar.animate().alpha(1f).setDuration(1400);

        // Cambia el mensaje de "Loading..." si quieres agregar frases aleatorias motivadoras:
        TextView loadingText = findViewById(R.id.text_loading);
        // loadingText.setText("Let the adventure begin..."); // Opcional

        // Retardo automático: cuando termine, inicia la siguiente activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PrepareMagicActivity.this, QuizGroupActivity.class);
                startActivity(intent);
                finish();
            }
        }, DELAY_MILLIS);
    }
}
