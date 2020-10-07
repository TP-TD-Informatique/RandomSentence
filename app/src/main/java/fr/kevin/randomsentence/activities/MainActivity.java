package fr.kevin.randomsentence.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.model.RegisterList;

public class MainActivity extends AppCompatActivity {
    private RegisterList registerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerList = new RegisterList();

        String register = registerList.getActualRegister();
        ((TextView) findViewById(R.id.main_database_name)).setText(!register.equals("") ? register : getString(R.string.main_database_name_error));
        findViewById(R.id.main_database_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra(ListActivity.REGISTER_LIST, registerList);
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
}