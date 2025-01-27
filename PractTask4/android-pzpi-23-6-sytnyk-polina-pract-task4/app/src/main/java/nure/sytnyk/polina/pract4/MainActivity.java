package nure.sytnyk.polina.pract4;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText;
    private TextView resultTextView;
    private SharedPreferences sharedPreferences;
    private DataBase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nameEditText = findViewById(R.id.nameEditText);
        resultTextView = findViewById(R.id.resultTextView);
        Button addButton = findViewById(R.id.addButton);
        Button viewButton = findViewById(R.id.viewButton);
        Button readFileButton = findViewById(R.id.readFileButton);


        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        dbHelper = new DataBase(this);


        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();

            // Збереження імені в SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.apply();


            dbHelper.addUser(name);


            writeToFile(name);


            Toast.makeText(MainActivity.this, "Користувача додано успішно!", Toast.LENGTH_SHORT).show();

            // Очищення поля введення після додавання
            nameEditText.setText("");
        });


        viewButton.setOnClickListener(v -> {

            Cursor cursor = dbHelper.getAllUsers();
            StringBuilder result = new StringBuilder();

            // Обробка курсора та відображення результатів
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int nameColumnIndex = cursor.getColumnIndex(DataBase.COLUMN_NAME);
                    if (nameColumnIndex != -1) {
                        result.append("Name: ").append(cursor.getString(nameColumnIndex)).append("\n");
                    }
                }
                cursor.close();
            } else {
                result.append("Користувачі не знайдені.");
            }


            resultTextView.setText(result.toString());
        });


        readFileButton.setOnClickListener(v -> {
            try {
                // Відкриття файлу для читання
                FileInputStream fis = openFileInput("myfile.txt");
                int c;
                StringBuilder temp = new StringBuilder();
                while ((c = fis.read()) != -1) {
                    temp.append((char) c);
                }
                fis.close();
                resultTextView.setText("Saved name: " + temp.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Помилка при читанні з файлу", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void writeToFile(String name) {
        try {

            FileOutputStream fos = openFileOutput("myfile.txt", MODE_PRIVATE);
            fos.write(name.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Помилка при записі в файл", Toast.LENGTH_SHORT).show();
        }
    }
}
