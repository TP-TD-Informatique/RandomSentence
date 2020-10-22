package fr.kevin.randomsentence.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.adapter.RegisterAdapter;
import fr.kevin.randomsentence.model.RegisterList;
import fr.kevin.randomsentence.storage.RegisterJsonFileStorage;

public class ListActivity extends AppCompatActivity {
    private RegisterList registerList;

    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        start();

        findViewById(R.id.list_database_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    private void start() {
        final RegisterJsonFileStorage registerJsonFileStorage = RegisterJsonFileStorage.get(getApplicationContext());
        registerList = new RegisterList(registerJsonFileStorage.findAll());
        registerList.choose(registerJsonFileStorage.getUsed());

        if (registerList.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
            startActivity(intent);
        }

        list = findViewById(R.id.list_database_list);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list.setAdapter(new RegisterAdapter(registerList, getApplicationContext()) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(View v) {
                int id = ((RegisterHolder) list.getChildViewHolder(v)).registerId;
                registerJsonFileStorage.use(id);
                ((RegisterHolder) list.getChildViewHolder(v)).item_layout.setBackgroundColor(getColor(R.color.colorPrimary));
                onResume();
            }

            @Override
            public void onItemModify(View v) {
                int id = ((RegisterHolder) list.getChildViewHolder(v)).registerId;
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra(ModifyActivity.REGISTER, id);
                startActivity(intent);
            }
        });
    }
}