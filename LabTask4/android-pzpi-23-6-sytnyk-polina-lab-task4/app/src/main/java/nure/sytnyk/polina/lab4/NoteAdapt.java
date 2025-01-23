package nure.sytnyk.polina.lab4;

import android.content.Context;
import android.graphics.Bitmap;
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

public class NoteAdapt extends RecyclerView.Adapter<NoteAdapt.NoteViewHolder> {

    private final Context context;
    private final ArrayList<Note> notesList;
    private final OnNoteClickListener onNoteClickListener;

    public NoteAdapt(Context context, ArrayList<Note> notesList, OnNoteClickListener onNoteClickListener) {
        this.context = context;
        this.notesList = notesList;
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note, parent, false);
        return new NoteViewHolder(view, onNoteClickListener);  // Передаем слушатель в ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;
        private final TextView dateTime;
        private final ImageView image;
        private final ImageView importance;
        private final OnNoteClickListener onNoteClickListener;  // Ссылка на слушатель

        public NoteViewHolder(View itemView, OnNoteClickListener onNoteClickListener) {
            super(itemView);
            this.onNoteClickListener = onNoteClickListener;  // Инициализация слушателя
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            dateTime = itemView.findViewById(R.id.dateTime);
            image = itemView.findViewById(R.id.image);
            importance = itemView.findViewById(R.id.importance);
        }

        public void bind(Note note) {
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            dateTime.setText(note.getDateTime());

            setImage(note);
            setImportance(note);

            itemView.setOnClickListener(v -> onNoteClickListener.onNoteClick(note));  // Использование слушателя
        }

        private void setImage(Note note) {
            if (note.getImageUri() != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(itemView.getContext().getContentResolver(), note.getImageUri());
                    image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    image.setImageResource(R.drawable.ic_launcher_background);
                    e.printStackTrace();
                }
            } else {
                image.setImageResource(R.drawable.ic_launcher_background);
            }
        }

        private void setImportance(Note note) {
            importance.setImageResource(getImportanceIcon(note.getImportance()));
        }

        private int getImportanceIcon(int importance) {
            switch (importance) {
                case 1:
                    return R.drawable.low;
                case 2:
                    return R.drawable.medium;
                case 3:
                    return R.drawable.high;
                default:
                    return R.drawable.low;
            }
        }
    }
}