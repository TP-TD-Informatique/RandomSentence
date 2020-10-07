package fr.kevin.randomsentence.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.adapter.RegisterAdapter;
import fr.kevin.randomsentence.model.RegisterList;

public class ListActivity extends AppCompatActivity {
    public static final String REGISTER_LIST = "register_list";

    private RegisterList registerList;

    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        registerList = (RegisterList) getIntent().getSerializableExtra(REGISTER_LIST);

        list = findViewById(R.id.list_database_list);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list.setAdapter(new RegisterAdapter(registerList) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(View v) {
                String name = String.valueOf(((RegisterHolder) list.getChildViewHolder(v)).database_name.getText());
                registerList.choose(name);
                ((RegisterHolder) list.getChildViewHolder(v)).item_layout.setBackgroundColor(getColor(R.color.colorPrimary));
            }

            @Override
            public boolean onItemLongClick(View v) {
                Toast.makeText(getApplicationContext(), "Modifier la base de données", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        findViewById(R.id.list_database_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Add a database", Toast.LENGTH_LONG).show();
            }
        });
    }
}