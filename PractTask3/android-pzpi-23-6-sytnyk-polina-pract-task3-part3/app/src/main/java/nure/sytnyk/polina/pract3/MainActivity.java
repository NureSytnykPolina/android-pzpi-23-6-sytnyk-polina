package nure.sytnyk.polina.pract3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        RecyclerView listView = findViewById(R.id.recyclerView);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(new MyAdapter(generateStrings()));
    }

    @NonNull
    private List<String> generateStrings() {
        List<String> items = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            items.add("Строка " + i);
        }
        return items;
    }
}
