package nure.sytnyk.polina.pract3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MYViewHolder> {
    private final List<String> date;


    public MyAdapter(List<String> mdate) {
        this.date = mdate;
    }

    // ViewHolder зберігає компоненти елемента списку
    public static class MYViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MYViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView); // Знайдіть TextView в item.xml
        }
    }

    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new MYViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        // Зв'язування даних із представленням
        String text = date.get(position);
        holder.textView.setText(text);

        // Додаємо обробник кліків
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Ви натиснули: " + text, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return date.size();
    }
}

