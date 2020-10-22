package fr.kevin.randomsentence.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.dialog.DeleteDialogFragment;
import fr.kevin.randomsentence.dialog.Updatable;
import fr.kevin.randomsentence.model.Register;
import fr.kevin.randomsentence.model.RegisterList;
import fr.kevin.randomsentence.storage.RegisterJsonFileStorage;

public class ModifyActivity extends AppCompatActivity implements Updatable {
    public static final String REGISTER = "register";

    private RegisterJsonFileStorage registerJsonFileStorage;

    private Integer registerId;
    private RegisterList registerList;
    private Register register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        registerJsonFileStorage = RegisterJsonFileStorage.get(getApplicationContext());
        registerList = new RegisterList(registerJsonFileStorage.findAll());

        registerId = (Integer) getIntent().getSerializableExtra(REGISTER);
        register = registerList.get(registerId);
        ((TextView) findViewById(R.id.modify_database_name)).setText(register.getName());
        findViewById(R.id.modify_add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText add_sentence = findViewById(R.id.modify_add_sentence);
                String sentence = add_sentence.getText().toString();
                add_sentence.setText("");
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
                (new DeleteDialogFragment(ModifyActivity.this, registerId)).show(getSupportFragmentManager(), "");
            }
        });
    }

    @Override
    public void update() {
        finish();
    }
}