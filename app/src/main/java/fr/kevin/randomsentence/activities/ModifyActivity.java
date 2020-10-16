package fr.kevin.randomsentence.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.model.Register;
import fr.kevin.randomsentence.model.RegisterList;
import fr.kevin.randomsentence.storage.RegisterJsonFileStorage;

public class ModifyActivity extends AppCompatActivity {
    public static final String REGISTER = "register";

    private RegisterJsonFileStorage registerJsonFileStorage;

    private Integer registerId;
    private RegisterList registerList;
    private Register register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        start();

        registerId = (Integer) getIntent().getSerializableExtra(REGISTER);

        if (registerId == null) {
            add();
        } else {
            register = registerList.get(registerId);
            modify();
        }
    }

    private void modify() {
        findViewById(R.id.modify_database_name).setEnabled(false);
        ((EditText) findViewById(R.id.modify_database_name)).setText(register.getName());
        findViewById(R.id.modify_add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sentence = ((EditText) findViewById(R.id.modify_add_sentence)).getText().toString();
                register.add(sentence);
                registerJsonFileStorage.update(registerId, register);
                Toast.makeText(getApplicationContext(), R.string.modify_add_sentence_info, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.modify_ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.modify_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.modify_database_name)).getText().toString();
                registerJsonFileStorage.delete(registerId);
                finish();
            }
        });
    }

    private void add() {
        ((EditText) findViewById(R.id.modify_database_name)).setHint("Name ...");
        findViewById(R.id.modify_add_btn).setEnabled(false);
        findViewById(R.id.modify_add_sentence).setEnabled(false);

        findViewById(R.id.modify_ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.modify_database_name)).getText().toString();
                registerJsonFileStorage.insert(new Register(name));
                finish();
            }
        });
        findViewById(R.id.modify_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void start() {
        registerJsonFileStorage = RegisterJsonFileStorage.get(getApplicationContext());
        registerList = new RegisterList(registerJsonFileStorage.findAll());
    }
}