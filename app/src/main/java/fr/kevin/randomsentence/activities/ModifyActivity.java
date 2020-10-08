package fr.kevin.randomsentence.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.model.Register;
import fr.kevin.randomsentence.model.RegisterList;

public class ModifyActivity extends AppCompatActivity {
    public static final String REGISTER = "register";

    private Register register;
    private RegisterList registerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        start();

        register = (Register) getIntent().getSerializableExtra(REGISTER);

        if (register == null) {
            add();
        } else {
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
                registerList.remove(name);
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
                registerList.add(new Register(name));
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
        registerList = new RegisterList();
        registerList.add(new Register("latin"));
        registerList.choose("latin");
        registerList.getActualRegister().add("Sed ultrices justo a ligula fermentum, in accumsan metus laoreet. Praesent non orci eget ante faucibus sagittis. Vestibulum quis ultricies lorem. Sed vel maximus mauris, in fermentum nisi. Morbi vitae leo ut elit venenatis accumsan sed vel leo. Duis lobortis maximus nunc, vel pellentesque erat porta a. In fermentum bibendum magna, sit amet tristique nisi. Cras id fringilla augue, at vulputate nibh. Nulla iaculis, tellus eu scelerisque congue, leo urna cursus nibh, eget varius lacus mauris in lacus. Morbi vitae ligula quis augue tempor vulputate quis sed eros. Cras id velit sed ante blandit semper. Sed vitae tincidunt risus. Aenean velit ipsum, lobortis et ante ut, scelerisque efficitur velit. Suspendisse congue vehicula augue et sagittis.");
        register = registerList.getActualRegister();
    }
}