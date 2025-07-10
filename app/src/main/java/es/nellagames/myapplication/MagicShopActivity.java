package es.nellagames.myapplication;



import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MagicShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_shop);

        TextView shopTitle = findViewById(R.id.text_shop_title);
        shopTitle.setText("Magic Shop\n(Coming Soon!)");
        // Extend this activity to allow avatar/background unlocks in the future
    }
}
