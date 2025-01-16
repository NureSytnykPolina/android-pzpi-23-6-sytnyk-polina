package nure.sytnyk.polina.lab4;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private Context context;
    private ArrayList<Note> notesList;
    private OnNoteClickListener onNoteClickListener;
    private SharedPreferences preferences;

    public NotesAdapter(Context context, ArrayList<Note> notesList, OnNoteClickListener onNoteClickListener) {
        this.context = context;
        this.notesList = notesList;
        this.onNoteClickListener = onNoteClickListener;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
        holder.dateTime.setText(note.getDateTime());

        // Применяем размер шрифта
        int fontSize = preferences.getInt("fontSize", 14);
        holder.title.setTextSize(fontSize);
        holder.description.setTextSize(fontSize - 2);
        holder.dateTime.setTextSize(fontSize - 4);

        if (note.getImageUri() != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), note.getImageUri());
                holder.image.setImageBitmap(bitmap);
            } catch (IOException e) {
                holder.image.setImageResource(R.drawable.ic_launcher_background);
                e.printStackTrace();
            }
        } else {
            holder.image.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.importance.setImageResource(getImportanceIcon(note.getImportance()));

        holder.itemView.setOnClickListener(v -> onNoteClickListener.onNoteClick(note));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    private int getImportanceIcon(int importance) {
        switch (importance) {
            case 1:
                return R.drawable.low_importance;
            case 2:
                return R.drawable.medium_importance;
            case 3:
                return R.drawable.high_importance;
            default:
                return R.drawable.low_importance;
        }
    }
    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, dateTime;
        ImageView image, importance;

        public NoteViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            dateTime = itemView.findViewById(R.id.dateTime);
            image = itemView.findViewById(R.id.image);
            importance = itemView.findViewById(R.id.importance);
        }
    }

    public void updateFontSizes() {
        notifyDataSetChanged();
    }
}