package nure.sytnyk.polina.lab4;
import android.net.Uri;

public class Note {
    private long id;
    private String title;
    private String description;
    private int importance;
    private String dateTime;
    private Uri imageUri;

    public Note(long id, String title, String description, int importance, String dateTime, Uri imageUri) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.importance = importance;
        this.dateTime = dateTime;
        this.imageUri = imageUri;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImportance() {
        return importance;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
