package fr.kevin.randomsentence.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.model.RegisterList;

public abstract class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.RegisterHolder> {

    public static class RegisterHolder extends RecyclerView.ViewHolder {
        public LinearLayout item_layout;
        public TextView database_name;

        public RegisterHolder(@NonNull View itemView) {
            super(itemView);
            item_layout = itemView.findViewById(R.id.item_layout);
            database_name = itemView.findViewById(R.id.item_database_name);
        }
    }

    private RegisterList registers;

    public RegisterAdapter(RegisterList registers) {
        this.registers = registers;
    }

    @NonNull
    @Override
    public RegisterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return onItemLongClick(v);
            }
        });

        return new RegisterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterHolder holder, int position) {
        holder.database_name.setText(registers.getRegistersName().get(position));
    }

    @Override
    public int getItemCount() {
        return registers.getRegistersName().size();
    }

    public abstract void onItemClick(View v);

    public abstract boolean onItemLongClick(View v);
}
