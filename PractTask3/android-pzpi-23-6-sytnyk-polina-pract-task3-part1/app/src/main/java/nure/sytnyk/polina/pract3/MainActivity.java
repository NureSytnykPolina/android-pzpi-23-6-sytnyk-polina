package nure.sytnyk.polina.pract3;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button showDialogButton = findViewById(R.id.showDialogButton);
        showDialogButton.setOnClickListener(v -> new AlertDialog.Builder(MainActivity.this)
                .setTitle("Діалог")
                .setMessage("Це приклад AlertDialog.")
                .setPositiveButton("OK", (dialog, which) -> {
                    Toast.makeText(this, "Натиснуто ОК", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    Toast.makeText(this, "Натиснуто Cancel", Toast.LENGTH_SHORT).show();
                })
                .show());

        Button showDatePickerButton = findViewById(R.id.showDatePickerButton);
        showDatePickerButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        Toast.makeText(
                                this,
                                "Обрано дату: " + dayOfMonth + "." + month + "." + year,
                                Toast.LENGTH_SHORT
                        ).show();
                    }, 2023, 8, 1);
            datePickerDialog.show();
        });

        Button showCustomDialogButton = findViewById(R.id.showCustomDialogButton);
        showCustomDialogButton.setOnClickListener(v -> {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(dialogView)
                    .setPositiveButton("OK", (dialog, id) -> {
                        Toast.makeText(MainActivity.this, "Натиснуто ОК", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> {
                        Toast.makeText(MainActivity.this, "Натиснуто Cancel", Toast.LENGTH_SHORT).show();
                    });
            builder.create().show();
        });
    }
}

