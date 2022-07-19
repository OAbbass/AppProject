package com.example.project;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Session> list;
    String userID;
    FirebaseAuth fAuth;
    DatabaseReference fStore;

    public  Boolean checkattendie(String atends, String myID)
    {
        String[] splitStr = atends.trim().split("\\s+");
        for (int i = 0; i < splitStr.length; i++)
        {
            if (splitStr[i].equals(myID))
            {
                Log.e("TAG", "USER ID FOUND IN ATTENDEES");
                return true;

            }
        }
        Log.e("TAG", "USER ID NOT IN ATTENDEES");
        return false;
    }

    public  Integer lastattendie(String atends, String myID)
    {
        Integer last;
        String[] splitStr = atends.trim().split("\\s+");
        for (int i = 0; i < splitStr.length; i++)
        {
            if (splitStr[i].equals(myID))
            {
                last = splitStr[i].length()+1;
                Log.e("TAG", splitStr[i]);
                return last;
            }
            last = splitStr[i].length()+1;
        }
        return 0;
    }


    public MyAdapter(Context context, ArrayList<Session> list) {
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

        Session session = list.get(position);
        holder.session.setText(session.getSess());
        holder.type.setText(session.getTy());
        holder.trainer.setText(session.getTra());
        holder.date.setText(session.getDat());
        holder.time.setText(session.getTi());
        holder.slots.setText(session.getCurrentIn() + "/" + session.getSize());
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid().toString();
        if (userID.equals(session.getTrainerID()))
        {
            holder.register.setText("Cancel Session");
        }
        if (checkattendie(session.getAttendees(), userID))
        {
            holder.register.setText("Cancel Registration");
        }

        holder.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (session.getCurrentIn() < session.getSize())
                {
                    if (!checkattendie(session.getAttendees(), userID))
                    {
                        Log.e("TAG", "Registering");
                        fStore = FirebaseDatabase.getInstance().getReference().child("sessions");
                        HashMap hashMap = new HashMap();
                        hashMap.put("currentIn", session.getCurrentIn() + 1);
                        hashMap.put("attendees", session.getAttendees() + userID + " ");
                        session.setAttendees(userID + " ");
                        fStore.child(session.getSessionID()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(context, "Successfully Registered!", Toast.LENGTH_SHORT);
                                session.setCurrentIn(session.getCurrentIn() + 1);
                                holder.slots.setText(session.getCurrentIn() + "/" + session.getSize());
                                Log.e("TAG", userID);
                            }
                        });
                        holder.register.setText("Cancel Registration");
                    }
                    else if (holder.register.getText().toString().equals("Cancel Session"))
                    {
                        holder.cancel.setVisibility(View.VISIBLE);
                        String cancelID = session.getSessionID().toString();
                        holder.cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                fStore.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot: snapshot.getChildren())
                                        {
                                            Session session1 = dataSnapshot.getValue(Session.class);
                                            if (cancelID.equals(session1.getSessionID()))
                                            {
                                                dataSnapshot.getRef().removeValue();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });
                    }
                    else
                    {
                        fStore = FirebaseDatabase.getInstance().getReference().child("sessions");
                        HashMap hashMap = new HashMap();
                        hashMap.put("currentIn", session.getCurrentIn() - 1);

                        Log.e("TAG", session.getAttendees());
                        Integer last = lastattendie(session.getAttendees().toString(), userID); // make own function or fix
                        Log.e("TAG", last.toString());
                        Integer first = session.getAttendees().indexOf(userID);
                        Log.e("TAG", first.toString());
                        //StringBuilder temp = new StringBuilder(session.getAttendees().toString());
                        Log.e("TAG", userID);

//                        for (int i = first; i < last + 2; i++)
//                        {
//                            Log.e("TAG", temp.toString());
//                            Log.e("TAG", temp.);
//                            temp.deleteCharAt(i);
//                        }

                        StringBuffer text = new StringBuffer(session.getAttendees().toString());
                        text.replace(first, last, "");

                        session.setAttendees(text.toString());
                        hashMap.put("attendees", session.getAttendees());

                        fStore.child(session.getSessionID()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(context, "Successfully Registered!", Toast.LENGTH_SHORT);
                                session.setCurrentIn(session.getCurrentIn() - 1);
                                holder.slots.setText(session.getCurrentIn() + "/" + session.getSize());
                                Log.e("TAG", userID);
                            }
                        });
                        holder.register.setText("Register");
                    }
                }

                else
                {
                    Toast.makeText(context, "Session is Full", Toast.LENGTH_SHORT);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView session,type, trainer, date, time, slots, cancel;
        Button register, details;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            session = itemView.findViewById(R.id.Session);
            type = itemView.findViewById(R.id.Type);
            trainer = itemView.findViewById(R.id.Trainer);
            date = itemView.findViewById(R.id.Date);
            time = itemView.findViewById(R.id.Time);
            slots = itemView.findViewById(R.id.Slots);
            cancel = itemView.findViewById(R.id.cancel);

            register = itemView.findViewById(R.id.register);
        }
    }
}
