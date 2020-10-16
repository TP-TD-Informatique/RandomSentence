package fr.kevin.randomsentence.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import fr.kevin.randomsentence.R;
import fr.kevin.randomsentence.model.Register;
import fr.kevin.randomsentence.model.RegisterList;

public abstract class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.RegisterHolder> {

    public static class RegisterHolder extends RecyclerView.ViewHolder {
        public LinearLayout item_layout;
        public TextView database_name;
        public TextView database_size;
        public int registerId;

        public RegisterHolder(@NonNull View itemView) {
            super(itemView);
            item_layout = itemView.findViewById(R.id.item_layout);
            database_name = itemView.findViewById(R.id.item_database_name);
            database_size = itemView.findViewById(R.id.item_database_size);
        }
    }

    private final RegisterList registers;
    private final Context context;

    public RegisterAdapter(RegisterList registers, Context context) {
        this.registers = registers;
        this.context = context;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RegisterHolder holder, int position) {
        int id = 0;
        for (int key : registers.keySet()) {
            id = key;
            if (position-- <= 0) break;
        }
        Register register = registers.get(id);
        holder.database_name.setText(register.getName());
        holder.database_size.setText(String.valueOf(register.size()));
        holder.registerId = id;
        if (id == registers.getRegister()) {
            ((LinearLayout) holder.itemView.findViewById(R.id.item_layout)).setBackgroundColor(context.getColor(R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return registers.size();
    }

    public abstract void onItemClick(View v);

    public abstract boolean onItemLongClick(View v);
}
