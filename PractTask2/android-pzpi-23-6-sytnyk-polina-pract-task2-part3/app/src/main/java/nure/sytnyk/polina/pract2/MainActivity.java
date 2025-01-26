package nure.sytnyk.polina.pract2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int clickCount = 0; // Счётчик кликов
    private TextView clickCounterText;
    private TextView timerText; // Поле для отображения таймера
    private Button clickButton;
    private Button secondActivityButton; // Кнопка перехода ко второму Activity
    private Handler timerHandler;
    private Runnable timerRunnable;
    private long timerStartTime;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Связываем элементы интерфейса
        clickCounterText = findViewById(R.id.clickCounter);
        timerText = findViewById(R.id.timerText);
        clickButton = findViewById(R.id.clickButton);
        secondActivityButton = findViewById(R.id.secondActivityButton);


        if (savedInstanceState != null) {
            clickCount = savedInstanceState.getInt("clickCount", 0);
            clickCounterText.setText(String.valueOf(clickCount));
        }


        clickButton.setOnClickListener(v -> {
            clickCount++;
            clickCounterText.setText(String.valueOf(clickCount));
        });

        // Обработчик нажатия на кнопку перехода ко второму Activity
        secondActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        // Инициализация таймера
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (isTimerRunning) {
                    long elapsedMillis = System.currentTimeMillis() - timerStartTime;
                    long seconds = elapsedMillis / 1000;
                    timerText.setText("Time: " + seconds + "s"); // Обновляем текст таймера
                    timerHandler.postDelayed(this, 1000); // Запускаем каждые 1000 мс
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isTimerRunning) {
            timerStartTime = System.currentTimeMillis();
            timerHandler.post(timerRunnable); // Запускаем таймер
            isTimerRunning = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTimerRunning = false;
        Toast.makeText(this, "Timer paused", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isTimerRunning) {
            timerStartTime = System.currentTimeMillis();
            timerHandler.post(timerRunnable);
            isTimerRunning = true;
        }
        Toast.makeText(this, "Timer resumed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("clickCount", clickCount); // Сохраняем счётчик
    }

    @Override
    protected void onStop() {
        super.onStop();
        isTimerRunning = false; // Останавливаем таймер при остановке
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
