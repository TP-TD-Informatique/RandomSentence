package fr.kevin.randomsentence.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.model.Register;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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