package nure.sytnyk.polina.pract2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
    }

    // Обробник кліків для цифр
    public void onDigitClick(View view) {
        Button button = (Button) view;
        currentInput += button.getText().toString();
        result.setText(currentInput);
    }

    // Обробник кліків для операторів
    public void onOperatorClick(View view) {
        Button button = (Button) view;

        // Перевірка на порожній ввід перед перетворенням
        if (currentInput.isEmpty()) {
            Toast.makeText(this, "Будь ласка, введіть число спочатку", Toast.LENGTH_SHORT).show();
            return;
        }

        operator = button.getText().toString();

        try {
            firstOperand = Double.parseDouble(currentInput);  // Спроба перетворити перший операнд
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Невірний ввід", Toast.LENGTH_SHORT).show();
            currentInput = "";
            return;
        }

        currentInput = "";
    }

    // Очищення екрану
    public void onClearClick(View view) {
        currentInput = "";
        operator = "";
        firstOperand = 0;
        result.setText("");
    }


    public void onEqualsClick(View view) {
        // Перевірка на порожній ввід
        if (currentInput.isEmpty()) {
            Toast.makeText(this, "Будь ласка, введіть друге число", Toast.LENGTH_SHORT).show();
            return;
        }

        double secondOperand = 0;
        try {
            secondOperand = Double.parseDouble(currentInput);  // Спроба перетворити другий операнд
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Невірний ввід", Toast.LENGTH_SHORT).show();
            return;
        }

        double resultValue = 0;

        switch (operator) {
            case "+":
                resultValue = firstOperand + secondOperand;
                break;
            case "-":
                resultValue = firstOperand - secondOperand;
                break;
            case "*":
                resultValue = firstOperand * secondOperand;
                break;
            case "/":
                if (secondOperand != 0) {
                    resultValue = firstOperand / secondOperand;
                } else {
                    result.setText("Помилка");
                    return;
                }
                break;
            default:
                Toast.makeText(this, "Невірний оператор", Toast.LENGTH_SHORT).show();
                return;
        }

        result.setText(String.valueOf(resultValue));
        currentInput = String.valueOf(resultValue);
        operator = "";
    }
}
