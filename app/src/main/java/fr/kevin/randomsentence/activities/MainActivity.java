package fr.kevin.randomsentence.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.model.Register;
import fr.kevin.randomsentence.model.RegisterList;

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
        registerList = new RegisterList();
        registerList.add(new Register("latin"));
        registerList.choose("latin");
        registerList.getActualRegister().add("Sed ultrices justo a ligula fermentum, in accumsan metus laoreet. Praesent non orci eget ante faucibus sagittis. Vestibulum quis ultricies lorem. Sed vel maximus mauris, in fermentum nisi. Morbi vitae leo ut elit venenatis accumsan sed vel leo. Duis lobortis maximus nunc, vel pellentesque erat porta a. In fermentum bibendum magna, sit amet tristique nisi. Cras id fringilla augue, at vulputate nibh. Nulla iaculis, tellus eu scelerisque congue, leo urna cursus nibh, eget varius lacus mauris in lacus. Morbi vitae ligula quis augue tempor vulputate quis sed eros. Cras id velit sed ante blandit semper. Sed vitae tincidunt risus. Aenean velit ipsum, lobortis et ante ut, scelerisque efficitur velit. Suspendisse congue vehicula augue et sagittis.");

        String register = registerList.getActualRegister().getName();
        ((TextView) findViewById(R.id.main_database_name)).setText(!register.equals("") ? register : getString(R.string.main_database_name_error));
        if (register.equals("")) {
            ((Button) findViewById(R.id.main_generate_btn)).setEnabled(false);
        }
    }
}