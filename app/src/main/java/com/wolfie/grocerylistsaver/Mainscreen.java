package com.wolfie.grocerylistsaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class Mainscreen extends AppCompatActivity {
    Spinner category;
    Spinner qty;
    String id1;
ArrayList<String> storeid = new ArrayList<String>();
    Spinner priority;
    EditText mEditText;
    EditText mEditText2;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    ArrayList<String> list = new ArrayList<>();
    ListView lv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        // CURD
        mAuth=FirebaseAuth.getInstance();
        mDatabaseReference =FirebaseDatabase.getInstance().getReference().child("grocery").child(mAuth.getCurrentUser().getUid());
        qty = findViewById(R.id.qty);
        category = findViewById(R.id.category);
        priority = findViewById(R.id.priority);
        mEditText = findViewById(R.id.editText);
        mEditText2=findViewById(R.id.editText2);
        // rest
        lv= findViewById(R.id.list);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
      lv.setAdapter(arrayAdapter);
      lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String idd = storeid.get(position);
              mDatabaseReference.child(idd).removeValue();
              arrayAdapter.remove(arrayAdapter.getItem(position));
              arrayAdapter.notifyDataSetChanged();
               storeid.remove(position);
          }
      });

      mDatabaseReference.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
         String value=dataSnapshot.getValue(curd.class).toString();
         list.add(value);
         arrayAdapter.notifyDataSetChanged();
          }

          @Override
          public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

          }

          @Override
          public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
              arrayAdapter.notifyDataSetChanged();
          }

          @Override
          public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              arrayAdapter.notifyDataSetChanged();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {
              arrayAdapter.notifyDataSetChanged();
          }
      });
        ImageView menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }
    private void show(){

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dv = LayoutInflater.from(this).inflate(R.layout.signout,viewGroup,false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
              Button btn = dv.findViewById(R.id.signout);

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseAuth.getInstance().signOut();
               finish();
               startActivity(new Intent(Mainscreen.this,Login.class));
           }
       });

        builder.setView(dv);
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }


    public void add(View V){
        id1 =UUID.randomUUID().toString();
        String itemname=mEditText.getText().toString();
        String qt=mEditText2.getText().toString();
        String qtt=qty.getSelectedItem().toString();
        String c=category.getSelectedItem().toString();
        String p=priority.getSelectedItem().toString();
        curd cc = new curd(id1,itemname,c,qt,qtt,p);
        storeid.add(id1);
        mDatabaseReference.child(id1).setValue(cc);

        }

    public void deleteItem(String pos) {


    }

    }

