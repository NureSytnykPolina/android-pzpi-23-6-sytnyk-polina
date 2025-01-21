package nure.sytnyk.polina.lab3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView screen;

    private String userInput = "";

    private String pendingOperation = "";

    private double firstNumber = 0;

    private boolean isCalculationDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.result);
        screen.setFocusable(false);

        setupButtons();
    }


    private void setupButtons() {
        int[] numberButtonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(this::onNumberClick);
        }

        int[] operationButtonIds = {
                R.id.Plus, R.id.Minus, R.id.Multiply, R.id.Divide
        };

        for (int id : operationButtonIds) {
            findViewById(id).setOnClickListener(this::onOperationClick);
        }

        findViewById(R.id.Equals).setOnClickListener(view -> calculateResult());
        findViewById(R.id.Clear).setOnClickListener(view -> resetCalculator());
        findViewById(R.id.Delete).setOnClickListener(view -> deleteLastInput());
        findViewById(R.id.Percent).setOnClickListener(view -> applyPercent());
        findViewById(R.id.Dot).setOnClickListener(view -> addDecPoint());
    }

    // Обробник натискання цифрових кнопок
    private void onNumberClick(View view) {
        if (isCalculationDone) {
            userInput = "";
            isCalculationDone = false;
        }

        Button button = (Button) view;
        userInput += button.getText().toString();
        updateScreen(userInput);
    }

    // Обробник натискання кнопок операцій
    private void onOperationClick(View view) {
        if (!userInput.isEmpty()) {
            firstNumber = Double.parseDouble(userInput);
            userInput = "";
            Button button = (Button) view;
            pendingOperation = button.getText().toString();
            updateScreen(pendingOperation);
        }
    }


    private void calculateResult() {
        if (!userInput.isEmpty() && !pendingOperation.isEmpty()) {
            try {
                double secondNumber = Double.parseDouble(userInput);
                double result = 0;

                switch (pendingOperation) {
                    case "+":
                        result = firstNumber + secondNumber;
                        break;
                    case "-":
                        result = firstNumber - secondNumber;
                        break;
                    case "×":
                        result = firstNumber * secondNumber;
                        break;
                    case "÷":
                        if (secondNumber == 0) {
                            updateScreen("Error");
                            return;
                        }
                        result = firstNumber / secondNumber;
                        break;
                }

                userInput = formatResult(result);
                updateScreen(userInput);
                pendingOperation = "";
                isCalculationDone = true;

            } catch (NumberFormatException e) {
                updateScreen("Error");
            }
        }
    }


    private void resetCalculator() {
        userInput = "";
        pendingOperation = "";
        firstNumber = 0;
        isCalculationDone = false;
        updateScreen("0");
    }


    private void deleteLastInput() {
        if (!userInput.isEmpty()) {
            userInput = userInput.substring(0, userInput.length() - 1);
            updateScreen(userInput.isEmpty() ? "0" : userInput);
        }
    }


    private void applyPercent() {
        if (!userInput.isEmpty()) {
            try {
                double value = Double.parseDouble(userInput) / 100;
                userInput = formatResult(value);
                updateScreen(userInput);
            } catch (NumberFormatException e) {
                updateScreen("Error");
            }
        }
    }


    private void addDecPoint() {
        if (!userInput.contains(".")) {
            userInput += userInput.isEmpty() ? "0." : ".";
            updateScreen(userInput);
        }
    }


    private void updateScreen(String text) {
        screen.setText(text);
    }


    private String formatResult(double value) {
        return (value == (long) value) ? String.valueOf((long) value) : String.valueOf(value);
    }
}
