package fr.kevin.randomsentence.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.adapter.RegisterAdapter;
import fr.kevin.randomsentence.dialog.CreateDialogFragment;
import fr.kevin.randomsentence.dialog.Updatable;
import fr.kevin.randomsentence.model.RegisterList;
import fr.kevin.randomsentence.storage.RegisterJsonFileStorage;

public class ListActivity extends AppCompatActivity implements Updatable {
    private RegisterList registerList;

    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        findViewById(R.id.list_database_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new CreateDialogFragment(ListActivity.this)).show(getSupportFragmentManager(), "");
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
        if (registerList.isEmpty()) {
            (new CreateDialogFragment(ListActivity.this)).show(getSupportFragmentManager(), "");
        }
        registerList.choose(registerJsonFileStorage.getUsed());

        list = findViewById(R.id.list_database_list);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(new RegisterAdapter(registerList, getApplicationContext()) {
            @Override
            public void onItemClick(View v) {
                int id = ((RegisterHolder) list.getChildViewHolder(v)).registerId;
                registerJsonFileStorage.use(id);
                update();
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

    @Override
    public void update() {
        onResume();
    }
}