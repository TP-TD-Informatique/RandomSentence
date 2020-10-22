package fr.kevin.randomsentence.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.model.Register;
import fr.kevin.randomsentence.storage.RegisterJsonFileStorage;

public class DeleteDialogFragment extends DialogFragment {

    private final Updatable updatable;
    private final int id;

    public DeleteDialogFragment(Updatable updatable, int id) {
        this.updatable = updatable;
        this.id = id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Register register = RegisterJsonFileStorage.get(getContext()).find(id);
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_dialog_title)
                .setMessage(getString(R.string.delete_diagog_message, register.getName()))
                .setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RegisterJsonFileStorage.get(getContext()).delete(id);
                        updatable.update();
                    }
                })
                .setNegativeButton(R.string.dialog_negative, null)
                .create();
    }
}
