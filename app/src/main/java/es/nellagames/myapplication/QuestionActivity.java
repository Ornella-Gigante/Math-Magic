package es.nellagames.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {

    private TextView textQuestion;
    private Button option1, option2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        textQuestion = findViewById(R.id.text_question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);

        // Opcional: Cambiar texto de pregunta y opciones dinámicamente

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Respuesta correcta (ejemplo)
                Toast.makeText(QuestionActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                // Aquí puedes avanzar a la siguiente pregunta o acción
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Respuesta incorrecta (ejemplo)
                Toast.makeText(QuestionActivity.this, "Try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
