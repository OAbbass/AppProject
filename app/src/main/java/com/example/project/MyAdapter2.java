package com.example.project;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.HashMap;

import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {

    Context context;
    ArrayList<Users> list;
    String userID;
    FirebaseAuth fAuth;
    DatabaseReference fStore;

    public MyAdapter2(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recycleview2, parent, false);
        return new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        Users user = list.get(position);
        holder.Nam.setText(user.getFirstname() + " " + user.getLastname());
        holder.ages.setText(user.getAge());
        holder.ge.setText(user.getGender());
        holder.eml.setText(user.getEmail());
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid().toString();

        holder.viewschedul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sender = new Intent(context, TrainerSchedule.class);
                sender.putExtra("KEY_SENDER", user.getID());
                context.startActivity(sender);
                holder.viewschedul.setVisibility(View.GONE);
                }
            });
        }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView Nam,ages, ge, eml;
        Button viewschedul;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            Nam = itemView.findViewById(R.id.Name);
            ages = itemView.findViewById(R.id.ag);
            ge = itemView.findViewById(R.id.gend);
            eml = itemView.findViewById(R.id.ema);

            viewschedul = itemView.findViewById(R.id.viewschedule);

        }
    }
}
