package nure.sytnyk.polina.lab4;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ModifyNoteActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 100;

    private EditText titleField, descriptionField;
    private Spinner prioritySpinner;
    private ImageView noteImage;
    private Uri imageUri;
    private long noteId;

    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Initialize UI elements
        titleField = findViewById(R.id.titleEditText);
        descriptionField = findViewById(R.id.descriptionEditText);
        prioritySpinner = findViewById(R.id.Spinner);
        noteImage = findViewById(R.id.noteImageView);
        Button selectImageButton = findViewById(R.id.selectImage);
        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        dbHelper = new Database(this);

        // Get data from Intent
        Intent intent = getIntent();
        noteId = intent.getLongExtra("note_id", -1);
        titleField.setText(intent.getStringExtra("title"));
        descriptionField.setText(intent.getStringExtra("description"));
        prioritySpinner.setSelection(intent.getIntExtra("importance", 0));

        String imagePath = intent.getStringExtra("image_uri");
        if (imagePath != null) {
            imageUri = Uri.parse(imagePath);
            noteImage.setImageURI(imageUri);
        }

        // Set button listeners
        selectImageButton.setOnClickListener(v -> openGallery());
        saveButton.setOnClickListener(v -> saveNote());
        deleteButton.setOnClickListener(v -> deleteNote());
    }

    private void openGallery() {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImageIntent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            noteImage.setImageURI(imageUri);
        }
    }

    private void saveNote() {
        String title = titleField.getText().toString().trim();
        String description = descriptionField.getText().toString().trim();
        int priority = prioritySpinner.getSelectedItemPosition();
        String image = (imageUri != null) ? imageUri.toString() : "";

        // Check if fields are filled
        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update note
        int rowsAffected = dbHelper.updateNote(noteId, title, description, priority, getCurrentDateTime(), image);

        if (rowsAffected > 0) {
            showToast("Заметка обновлена");
            finish();
        } else {
            showToast("Ошибка обновления");
        }
    }

    private void deleteNote() {
        showToast("Заметка 111111");
        dbHelper.deleteNote(noteId);
        showToast("Заметка удалена");
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String getCurrentDateTime() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date());
    }
}

