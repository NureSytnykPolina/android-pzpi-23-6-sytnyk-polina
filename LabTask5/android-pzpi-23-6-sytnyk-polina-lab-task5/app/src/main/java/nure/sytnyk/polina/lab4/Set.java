package nure.sytnyk.polina.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Set extends AppCompatActivity {
    private SharedPreferences preferences;
    private RadioGroup themeG;
    private SeekBar SizeSeekBar;
    private TextView SizeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyThemeSet();

        setContentView(R.layout.activity_set);

        themeG = findViewById(R.id.theme);
        SizeSeekBar = findViewById(R.id.SizeSeekBar);
        SizeValue = findViewById(R.id.SizeValue);
           initializeUI();

       themeG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // Сохраняем выбранную тему
                    int themeValue = (checkedId == R.id.dark) ? 1 : 0;
                    preferences.edit().putInt("theme", themeValue).apply();


                    restartApp();
                }
            });

            SizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int fontSize = progress + 10;
                preferences.edit().putInt("fontSize", fontSize).apply();
                SizeValue.setText(fontSize + "sp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void initializeUI() {
        // Получаем сохраненную тему из SharedPreferences
        int curtheme = preferences.getInt("theme", 0);
        int checkedId = (curtheme == 1) ? R.id.dark : R.id.light;
        themeG.clearCheck();
        themeG.check(checkedId);


        int fontSize = preferences.getInt("fontSize", 14);
        SizeSeekBar.setProgress(fontSize - 10);
        SizeValue.setText(fontSize + "sp");
    }


    private void applyThemeSet() {
        int theme = preferences.getInt("theme", 0);
        if (theme == 0) {
            setTheme(R.style.Light);
        } else {
            setTheme(R.style.Dark);
        }
    }

    private void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);  // Перезапуск с MainActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Очистка стека активити
        startActivity(intent);
        finish();
    }
}

