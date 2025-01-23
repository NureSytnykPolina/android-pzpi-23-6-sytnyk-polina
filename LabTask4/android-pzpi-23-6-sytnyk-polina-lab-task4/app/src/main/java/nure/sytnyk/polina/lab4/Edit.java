package nure.sytnyk.polina.lab4;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Edit extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private Spinner importanceSpinner;
    private ImageView noteImageView;
    private Button selectImageButton;
    private Button saveButton;
    private Button deleteButton;
    private Database  dbHelper;
    private Uri imageUri;
    private String imageUri1;
    private Note note;
    private long noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        dbHelper = new Database(this);
        // Инициализация элементов UI
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        importanceSpinner = findViewById(R.id.Spinner);
        noteImageView = findViewById(R.id.noteImageView);
        selectImageButton = findViewById(R.id.selectImage);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);


        // Получение данных из Intent

        Intent intent = getIntent();
         noteId = intent.getLongExtra("note_id", -1);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        int importance = intent.getIntExtra("importance", 1);  // Default to "1" if no importance is provided
        String dateTime = intent.getStringExtra("date_time");
        String imageUriString = intent.getStringExtra("image_uri");
        /*Uri imageUri1 = (imageUriString != null && !imageUriString.isEmpty())
                ? Uri.parse(imageUriString)
                : null;*/
        imageUri1 = imageUriString;

        if (noteId != -1) {
            note = new Note(noteId, title, description, importance, dateTime, imageUriString != null ? Uri.parse(imageUriString) : null);
            titleEditText.setText(note.getTitle());
            descriptionEditText.setText(note.getDescription());
            // Устанавливаем значение для Spinner
            importanceSpinner.setSelection(importance - 1);  // Примерный индекс для отображения
            if (note.getImageUri() != null) {
                noteImageView.setImageURI(note.getImageUri());
            }
        }

        // Обработчик выбора изображения
        selectImageButton.setOnClickListener(view -> openImagePicker());


        saveButton.setOnClickListener(view -> saveNote());


        deleteButton.setOnClickListener(view -> deleteNote());
    }

    private void openImagePicker() {
        // Логика выбора изображения через Intent
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1); // Здесь 1 - это код запроса
    }

    private void saveNote() {
        String newTitle = titleEditText.getText().toString();


        String newDescription = descriptionEditText.getText().toString();
        int newImportance = importanceSpinner.getSelectedItemPosition() + 1;
        String newDateTime = System.currentTimeMillis() + "";

        if (newTitle.isEmpty() || newDescription.isEmpty()) {
            Toast.makeText(this, "Поля не можуть бути пусті", Toast.LENGTH_SHORT).show();
            return;
        }
        int rowsAff = dbHelper.updateNote(noteId,  newTitle, newDescription, newImportance, newDateTime, imageUri1);
        if (rowsAff > 0) {
            Toast.makeText(this, "Нотатка змінена успішно", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Помилка. Нотатка не змінена ", Toast.LENGTH_SHORT).show();
        }
       /* note = new Note(note.getId(), newTitle, newDescription, newImportance, newDateTime, imageUri);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated_note", (CharSequence) note);
        setResult(RESULT_OK, resultIntent);
        finish();*/




    }

    private void deleteNote() {

        showToast("ID =  "+noteId);
        dbHelper.deleteNote( noteId);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("deleted_note_id", note.getId());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) { // Если это запрос на выбор изображения
                imageUri = data.getData();
                noteImageView.setImageURI(imageUri);
            }
        }
    }
}
