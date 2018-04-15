package com.dhaval.enerbet;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyProfile extends AppCompatActivity {
    DatabaseReference mDatabase;
    EditText editText_money;
    int money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ImageView imageView_profile = findViewById(R.id.profile_pic);

        final TextView textView_money = findViewById(R.id.money);
        Button button_add = findViewById(R.id.button_add);
        editText_money = findViewById(R.id.edit_money);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            setTitle(user.getDisplayName());
            Uri uri = user.getPhotoUrl();
            Picasso.with(this)
                    .load(uri)
                    .resize(600, 600)
                    .centerCrop()
                    .into(imageView_profile);
        }

        mDatabase.child("users").child(user.getUid()).child("money").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                textView_money.setText("Your Wallet Money: $" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               money = Integer.parseInt(editText_money.getText().toString()) + money;
                mDatabase.child("users").child(user.getUid()).child("money").setValue(money);
            }
        });


    }


}
