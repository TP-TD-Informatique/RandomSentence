package fr.kevin.randomsentence.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.model.Register;
import fr.kevin.randomsentence.storage.RegisterJsonFileStorage;

public class CreateDialogFragment extends DialogFragment {

    private final Updatable updatable;

    private View view;

    public CreateDialogFragment(Updatable updatable) {
        this.updatable = updatable;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        view = requireActivity().getLayoutInflater().inflate(R.layout.create_dialog, null);
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.create_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = ((EditText) view.findViewById(R.id.create_name)).getText().toString();
                        if (name.isEmpty()) {
                            Toast.makeText(getContext(), R.string.create_dialog_warning, Toast.LENGTH_SHORT).show();
                        } else {
                            RegisterJsonFileStorage.get(getContext()).insert(new Register(((EditText) view.findViewById(R.id.create_name)).getText().toString()));
                            updatable.update();
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_negative, null)
                .create();
    }
}
