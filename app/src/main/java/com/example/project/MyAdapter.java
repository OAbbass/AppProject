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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    DatabaseReference databaseReference;
    public Users user1;
    String temp1;

    public  Boolean checkattendie(String atends, String myID)
    {
        String[] splitStr = atends.trim().split("\\s+");
        for (int i = 0; i < splitStr.length; i++)
        {
            if (splitStr[i].equals(myID))
            {
                return true;
            }
        }
        return false;
    }

    public  Integer lastattendie(String atends, String myID)
    {
        Integer last = 0;
        String[] splitStr = atends.trim().split("\\s+");
        for (int i = 0; i < splitStr.length; i++)
        {
            if (splitStr[i].equals(myID))
            {
                last = last + splitStr[i].length()+1;
                Log.e("target ID", myID);
                Log.e("ins ID", splitStr[i]);
                Log.e("first char pos", last.toString());
                return last;
            }
            last = last + splitStr[i].length()+1;
        }
        return 0;
    }

    public  Integer firstattendie(String atends, String myID)
    {
        Integer last = 0;
        String[] splitStr = atends.trim().split("\\s+");
        for (int i = 0; i < splitStr.length; i++)
        {
            if (splitStr[i].equals(myID))
            {
                Log.e("target ID", myID);
                Log.e("ins ID", splitStr[i]);
                Log.e("last char pos", last.toString());
                return last;
            }
            last = last + splitStr[i].length()+1;
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
        if (checkattendie(session.getAttendees().toString(), userID))
        {
            holder.register.setText("Cancel Registration");
        }


        fStore = FirebaseDatabase.getInstance().getReference().child("sessions");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");


        holder.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (session.getTrainerID().equals(userID))
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
                                            if(session.getTy().equals("Private"))
                                            {
                                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for (DataSnapshot dataSnapshot: snapshot.getChildren())
                                                        {
                                                            user1 = dataSnapshot.getValue(Users.class);

                                                            Log.e("CHECK user", user1.getID().trim());
                                                            Log.e("check id", session.getAttendees().trim());

                                                            if (user1.getID().trim().equals(session.getAttendees().trim()))
                                                            {
                                                                Log.e("CHECK", "Private Check Complete");
                                                                Log.e("Email check", user1.getEmail().toString());
                                                                Intent intent3 = new Intent(Intent.ACTION_SEND);
                                                                intent3.putExtra(Intent.EXTRA_EMAIL, user1.getEmail());
                                                                intent3.putExtra(Intent.EXTRA_SUBJECT, "Unfortunately, I Had to Cancel my Session");
                                                                intent3.putExtra(Intent.EXTRA_TEXT, "Sorry, but " + user1.getFirstname() + "I had to cancel our session at " + session.getDat());
                                                                intent3.setType("message/rfc822");
                                                                context.startActivity(intent3);
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                    }
                                                });
                                            }
                                            fStore.child(cancelID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(context, "Session Deleted", Toast.LENGTH_SHORT).show();


                                                    }
                                                }
                                            });
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
                else if (session.getCurrentIn() < session.getSize() || checkattendie(session.getAttendees(), userID))
                {
                    if (!checkattendie(session.getAttendees(), userID))
                    {
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                                {
                                    userID = fAuth.getCurrentUser().getUid().toString();
                                    user1 = dataSnapshot.getValue(Users.class);
                                    if (user1.getID().equals(userID))
                                    {
                                        dataSnapshot.child("SessionsRegisteredID").getRef().setValue(user1.getSessionsRegisteredID() + session.getSessionID() + " ");
                                        user1.setSessionsRegisteredID(user1.getSessionsRegisteredID() + session.getSessionID() + " ");
                                        break;
                                    }
                                }
                                Log.e ("TAG", user1.getID());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        HashMap hashMap = new HashMap();
                        hashMap.put("currentIn", session.getCurrentIn() + 1);
                        hashMap.put("attendees", session.getAttendees() + userID + " ");
                        session.setAttendees(session.getAttendees() + userID + " ");




                        fStore.child(session.getSessionID()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(context, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                session.setCurrentIn(session.getCurrentIn() + 1);
                                holder.slots.setText(session.getCurrentIn() + "/" + session.getSize());
                                Log.e("TAG", userID);
                            }
                        });
                        holder.register.setText("Cancel Registration");
                    }
                    else
                    {

                        HashMap hashMap = new HashMap();
                        Integer last = lastattendie(session.getAttendees().toString(), userID);
                        Integer first = firstattendie(session.getAttendees().toString(), userID);

                        StringBuffer text = new StringBuffer(session.getAttendees().toString());
                        text.replace(first, last, "");

                        Log.e("Mem Att after removal", session.getAttendees().toString());
                        session.setAttendees(text.toString());
                        hashMap.put("attendees", session.getAttendees());
                        hashMap.put("currentIn", session.getCurrentIn() - 1);

                        fStore.child(session.getSessionID()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(context, "Registration Cancelled", Toast.LENGTH_SHORT).show();
                                session.setCurrentIn(session.getCurrentIn() - 1);
                                holder.slots.setText(session.getCurrentIn() + "/" + session.getSize());
                            }
                        });
                        holder.register.setText("Register");


                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                                {
                                    userID = fAuth.getCurrentUser().getUid().toString();
                                    user1 = dataSnapshot.getValue(Users.class);
                                    if (user1.getID().equals(userID))
                                    {
                                        Integer last = lastattendie(user1.getSessionsRegisteredID(), session.getSessionID());
                                        Integer first = firstattendie(user1.getSessionsRegisteredID(), session.getSessionID());
                                        StringBuffer text = new StringBuffer(user1.getSessionsRegisteredID());
                                        text.replace(first, last, "");
                                        Log.e("FIRST", first.toString());
                                        Log.e("LAST", last.toString());
                                        Log.e("After removal", text.toString());
                                        dataSnapshot.child("SessionsRegisteredID").getRef().setValue(text.toString());
                                        user1.setSessionsRegisteredID(text.toString());

                                        Toast.makeText(context, "Registration Removed!", Toast.LENGTH_SHORT).show();


                                        if(session.getTy().equals("Private"))
                                        {
                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                                                {
                                                    user1 = dataSnapshot.getValue(Users.class);
                                                    if (user1.getID().equals(session.getTrainerID()))
                                                    {
                                                        Log.e("CHECK", "Private Check Complete");
                                                        Log.e("Email check", user1.getEmail().toString());
                                                        Intent intent2 = new Intent(Intent.ACTION_SEND);
                                                        intent2.putExtra(Intent.EXTRA_EMAIL, user1.getEmail());
                                                        intent2.putExtra(Intent.EXTRA_SUBJECT, "Unfortunately, I Had to Cancel my Registration");
                                                        intent2.putExtra(Intent.EXTRA_TEXT, "Sorry, but " + session.getTra() + "I had to cancel my registration at " + session.getDat());
                                                        intent2.setType("message/rfc822");
                                                        context.startActivity(intent2);
                                                        break;
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        });
                                        }
                                        break;
                                    }
                                }
                                Log.e ("TAG", user1.getID());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });


                    }
                }

                else
                {
                    holder.register.setText("Session is Full");
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

//    public void sendemail(String email, String name, String date)
//    {
//        Intent intent2 = new Intent(Intent.ACTION_SEND);
//        intent2.putExtra(Intent.EXTRA_EMAIL, email); // session.getTrainerEM()
//        intent2.putExtra(Intent.EXTRA_SUBJECT, "Unfortunately, I Had to Cancel my Registration");
//        intent2.putExtra(Intent.EXTRA_TEXT, "Sorry " + name + "I had to cancel my session at " + date); // session.getTra() session.getDat()
//        intent2.setType("message/rfc822");
//
//    }
}
