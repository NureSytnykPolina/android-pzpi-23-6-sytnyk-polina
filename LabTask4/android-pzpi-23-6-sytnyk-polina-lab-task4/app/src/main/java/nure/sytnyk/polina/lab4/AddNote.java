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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNote extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText titleEditText, descriptionEditText;
    private Spinner importanceSpinner;
    private Button saveButton, selectImageButton;
    private ImageView noteImageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initViews();
        setListeners();
    }

    private void initViews() {
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        importanceSpinner = findViewById(R.id.importanceSpinner);
        saveButton = findViewById(R.id.saveButton);
        selectImageButton = findViewById(R.id.selectImageButton);
        noteImageView = findViewById(R.id.noteImageView);
    }

    private void setListeners() {
        selectImageButton.setOnClickListener(v -> openGallery());
        saveButton.setOnClickListener(v -> validateAndSaveNote());

        titleEditText.addTextChangedListener(new SimpleTextWatcher());
        descriptionEditText.addTextChangedListener(new SimpleTextWatcher());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            handleImageSelection(data);
        }
    }

    private void handleImageSelection(Intent data) {
        imageUri = data.getData();
        noteImageView.setImageURI(imageUri);
    }

    private void validateAndSaveNote() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            saveNote(title, description);
        }
    }

    private void saveNote(String title, String description) {
        int importance = importanceSpinner.getSelectedItemPosition();
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        String imageUriString = (imageUri != null) ? imageUri.toString() : "";

        Database dbHelper = new Database(this);
        long noteId = dbHelper.addNote(title, description, importance, dateTime, imageUriString);

        if (noteId != -1) {
            Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error adding note", Toast.LENGTH_SHORT).show();
        }
    }

    private class SimpleTextWatcher implements android.text.TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(android.text.Editable editable) {

        }
    }
}