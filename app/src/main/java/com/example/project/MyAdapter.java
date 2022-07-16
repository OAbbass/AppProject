package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> list;

    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recycleview_custom_layout, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        User user = list.get(position);
        holder.session.setText(user.getSess());
        holder.type.setText(user.getTy());
        holder.trainer.setText(user.getTra());
        holder.date.setText(user.getDat());
        holder.time.setText(user.getTi());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView session,type, trainer, date, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            session = itemView.findViewById(R.id.Session);
            type = itemView.findViewById(R.id.Type);
            trainer = itemView.findViewById(R.id.Trainer);
            date = itemView.findViewById(R.id.Date);
            time = itemView.findViewById(R.id.Time);
        }
    }
}
