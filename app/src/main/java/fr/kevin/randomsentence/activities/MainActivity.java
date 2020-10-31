package fr.kevin.randomsentence.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.model.GenerateType;
import fr.kevin.randomsentence.model.Register;
import fr.kevin.randomsentence.model.RegisterList;
import fr.kevin.randomsentence.storage.RegisterJsonFileStorage;

import static fr.kevin.randomsentence.model.GenerateType.*;

public class MainActivity extends AppCompatActivity {
    private RegisterList registerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start();

        findViewById(R.id.main_database_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.main_database_name).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.main_database_name_info), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        findViewById(R.id.main_generate_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = registerList.getActualRegister().generate(
                        Integer.parseInt(((EditText) findViewById(R.id.main_quantity)).getText().toString()));
                ((TextView) findViewById(R.id.main_generated_text)).setText(text);
            }
        });
        findViewById(R.id.main_generate_btn).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.main_generate_btn_info), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        final TextView generatedText = findViewById(R.id.main_generated_text);
        generatedText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cd = ClipData.newPlainText("text", generatedText.getText());
                cm.setPrimaryClip(cd);
                Toast.makeText(getApplicationContext(), getString(R.string.main_copy_generated_text), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    private void start() {
        RegisterJsonFileStorage registerJsonFileStorage = RegisterJsonFileStorage.get(getApplicationContext());
        registerList = new RegisterList(registerJsonFileStorage.findAll());
        registerList.choose(registerJsonFileStorage.getUsed());

        Register register = registerList.getActualRegister();
        ((TextView) findViewById(R.id.main_database_name)).setText(register == null ? getString(R.string.main_database_name_error) : register.getName());
        if (register == null) {
            findViewById(R.id.main_generate_btn).setEnabled(false);
            findViewById(R.id.main_quantity).setEnabled(false);
        } else {
            findViewById(R.id.main_generate_btn).setEnabled(true);
            findViewById(R.id.main_quantity).setEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int id = R.id.generate_word;
        switch (Register.getGenerateType()) {
            case WORD:
                id = R.id.generate_word;
                break;
            case SENTENCE:
                id = R.id.generate_sentence;
                break;
            case PARAGRAPH:
                id = R.id.generate_paragraph;
                break;
        }
        menu.findItem(id).setChecked(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        GenerateType type = WORD;
        if (item.getItemId() == R.id.generate_sentence)
            type = SENTENCE;
        else if (item.getItemId() == R.id.generate_paragraph)
            type = PARAGRAPH;
        Register.setGenerateType(type);

        return true;
    }
}