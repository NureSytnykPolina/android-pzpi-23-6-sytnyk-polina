package nure.sytnyk.polina.lab4;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteAdapt.OnNoteClickListener {

    private static final int IMAGE_PICK_CODE = 100;
    private static final int NEW_NOTE_REQUEST = 200;
    private static final int MODIFY_NOTE_REQUEST = 300;

    private RecyclerView noteListView;
    private NoteAdapt noteAdapter;
    private ArrayList<Note> displayedNotes = new ArrayList<>();
    private ArrayList<Note> backupNotes = new ArrayList<>();
    private Database database;

    private Uri imageUri;
    private EditText searchBar;
    private SharedPreferences preferences;
    private final ActivityResultLauncher<String> permissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
                if (result) {
                    refreshNoteList();
                } else {
                    Toast.makeText(this, "111annot fetch images.", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyThemeMain();
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        initializeUI();
        validatePermissions();
        searchBar = findViewById(R.id.searchText);
        configureSearchFeature();

    }

    private void initializeUI() {
        noteListView = findViewById(R.id.recyclerView);

        searchBar = findViewById(R.id.Edit);
        database = new Database(this);

        findViewById(R.id.addButton).setOnClickListener(v -> openNewNoteScreen());
        findViewById(R.id.setButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Set.class));
        });
        noteListView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapt(this, displayedNotes, this); // Прямо передаем 'this' для реализации интерфейса
        noteListView.setAdapter(noteAdapter);
    }

    private void validatePermissions() {
        String permission = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) ?
                Manifest.permission.READ_MEDIA_IMAGES : Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionRequest.launch(permission);
        }
    }

    private void configureSearchFeature() {
        if (searchBar != null) {
            searchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    showToast(s.toString());
                    applySearchFilter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        } else {
            Log.e("MainActivity", "searchBar is null. Check if it is correctly initialized.");
        }
    }

    public void applyThemeMain() {
        int theme = preferences.getInt("theme", 0);
        if (theme == 0) {
            setTheme(R.style.Light);
        } else {
            setTheme(R.style.Dark);
        }
    }



    private void applySearchFilter(String query) {
        if (backupNotes.isEmpty()) {
            backupNotes.addAll(displayedNotes);
        }

        displayedNotes.clear();

        if (query.isEmpty()) {
            displayedNotes.addAll(backupNotes);
        } else {
            for (Note item : backupNotes) {
                if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    displayedNotes.add(item);
                }
            }
        }
        noteAdapter.notifyDataSetChanged();
    }

    private void refreshNoteList() {
        displayedNotes.clear();
        backupNotes.clear();
        displayedNotes.addAll(database.getNotes());
        backupNotes.addAll(displayedNotes);
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshNoteList();
    }

    private void openNewNoteScreen() {
        Intent intent = new Intent(this, AddNote.class);
        startActivityForResult(intent, NEW_NOTE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
        }

        if ((requestCode == NEW_NOTE_REQUEST || requestCode == MODIFY_NOTE_REQUEST) && resultCode == RESULT_OK) {
            refreshNoteList();
        }
    }

    @Override
    public void onNoteClick(Note note) {
        Intent intent = new Intent(this, Edit.class);
        intent.putExtra("note_id", note.getId());
        intent.putExtra("title", note.getTitle()); //+"  note_id ="+note.getId());
        intent.putExtra("description", note.getDescription());
        intent.putExtra("importance", note.getImportance());
        intent.putExtra("image_uri", note.getImageUri() != null ? note.getImageUri().toString() : "");

        startActivityForResult(intent, MODIFY_NOTE_REQUEST);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
