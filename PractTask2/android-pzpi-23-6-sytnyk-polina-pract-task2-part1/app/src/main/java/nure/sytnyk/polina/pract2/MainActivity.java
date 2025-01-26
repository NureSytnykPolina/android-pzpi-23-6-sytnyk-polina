package nure.sytnyk.polina.pract2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView displayText;
    private boolean isLargeFont = false;
    private boolean isRedText = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayText = findViewById(R.id.text_title);
    }


    public void UpdateText(View view) {
        displayText.setText("Текст Змінено");
    }


    public void DisplayMes(View view) {
        Toast.makeText(this, "Повідомлення показано", Toast.LENGTH_SHORT).show();
    }


    public void FontSize(View view) {
        displayText.setTextSize(isLargeFont ? 14 : 26);
        isLargeFont = !isLargeFont;
    }


    public void TextColor(View view) {
        displayText.setTextColor(isRedText ? Color.BLACK : Color.GREEN);
        isRedText = !isRedText;
    }
}
