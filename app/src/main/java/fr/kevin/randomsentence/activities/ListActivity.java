package fr.kevin.randomsentence.activities;

import android.content.Intent;
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

import java.util.HashMap;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.adapter.RegisterAdapter;
import fr.kevin.randomsentence.model.Register;
import fr.kevin.randomsentence.model.RegisterList;
import fr.kevin.randomsentence.storage.RegistersJsonFileStorage;

public class ListActivity extends AppCompatActivity {
    private RegisterList registerList;

    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        start();

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
                String name = String.valueOf(((RegisterHolder) list.getChildViewHolder(v)).database_name.getText());
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra(ModifyActivity.REGISTER, registerList.getRegisters().get(name));
                startActivity(intent);
                return true;
            }
        });

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
        registerList = new RegisterList();
        registerList.create("latin");
        registerList.choose("latin");
        registerList.getRegister().add("Sed ultrices justo a ligula fermentum, in accumsan metus laoreet. Praesent non orci eget ante faucibus sagittis. Vestibulum quis ultricies lorem. Sed vel maximus mauris, in fermentum nisi. Morbi vitae leo ut elit venenatis accumsan sed vel leo. Duis lobortis maximus nunc, vel pellentesque erat porta a. In fermentum bibendum magna, sit amet tristique nisi. Cras id fringilla augue, at vulputate nibh. Nulla iaculis, tellus eu scelerisque congue, leo urna cursus nibh, eget varius lacus mauris in lacus. Morbi vitae ligula quis augue tempor vulputate quis sed eros. Cras id velit sed ante blandit semper. Sed vitae tincidunt risus. Aenean velit ipsum, lobortis et ante ut, scelerisque efficitur velit. Suspendisse congue vehicula augue et sagittis.");

        if (registerList.getRegistersName().isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
            startActivity(intent);
        }
    }
}