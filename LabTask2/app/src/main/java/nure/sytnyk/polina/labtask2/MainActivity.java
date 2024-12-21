package nure.sytnyk.polina.labtask2;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private LinearLayout colorPanel;
    private SeekBar redSeekBar, greenSeekBar, blueSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


         colorPanel = findViewById(R.id.color_panel);
        redSeekBar = findViewById(R.id.red_seek_bar);
        greenSeekBar = findViewById(R.id.green_seek_bar);
        blueSeekBar = findViewById(R.id.blue_seek_bar);


        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColorPanel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        redSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        greenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        blueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        updateColorPanel();
    }

    private void updateColorPanel() {
        int red = redSeekBar.getProgress();
        int green = greenSeekBar.getProgress();
        int blue = blueSeekBar.getProgress();
        int color = Color.rgb(red, green, blue);
        colorPanel.setBackgroundColor(color);
    }

    @Override
    public void onConfigurationChanged(@NonNull android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recreate(); // Переинициализация активности
    }
}
