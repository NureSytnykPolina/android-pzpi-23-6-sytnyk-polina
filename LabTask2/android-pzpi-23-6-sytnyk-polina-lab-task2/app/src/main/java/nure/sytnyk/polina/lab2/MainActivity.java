package nure.sytnyk.polina.lab2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private SeekBar sbRed, sbGreen, sbBlue;
    private View colorPreview;


    private final int[] rgbValues = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        if (savedInstanceState != null) {
            restoreColorValues(savedInstanceState);
            applyColor();
        }


        setupSeekBarListeners();
    }


    private void initializeComponents() {
        sbRed = findViewById(R.id.red_bar);
        sbGreen = findViewById(R.id.green_bar);
        sbBlue = findViewById(R.id.blue_bar);
        colorPreview = findViewById(R.id.color_panel);
    }

    // Метод для відновлення значень кольору із збереженого стану
    private void restoreColorValues(Bundle savedState) {
        rgbValues[0] = savedState.getInt("RED_VALUE", 0);
        rgbValues[1] = savedState.getInt("GREEN_VALUE", 0);
        rgbValues[2] = savedState.getInt("BLUE_VALUE", 0);
    }

    // Метод для налаштування слухача для всіх повзунків
    private void setupSeekBarListeners() {
        configureSeekBar(sbRed, 0);
        configureSeekBar(sbGreen, 1);
        configureSeekBar(sbBlue, 2);
    }


    private void configureSeekBar(SeekBar seekBar, int index) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rgbValues[index] = progress;
                applyColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // Метод для застосування цвета на основе поточних значений RGB
    private void applyColor() {
        int color = Color.rgb(rgbValues[0], rgbValues[1], rgbValues[2]);
        colorPreview.setBackgroundColor(color);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("RED_VALUE", rgbValues[0]);
        outState.putInt("GREEN_VALUE", rgbValues[1]);
        outState.putInt("BLUE_VALUE", rgbValues[2]);
    }
}
