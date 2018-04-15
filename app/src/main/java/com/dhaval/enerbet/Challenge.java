package com.dhaval.enerbet;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Challenge extends AppCompatActivity {

    Button button_join;
    int i=0;
    TextView textView_timer;
    DatabaseReference mDatabase;
        float hours, minutes, seconds, month, day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        Bundle bundle = getIntent().getExtras();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        final String name = bundle.getString("name");
        String description = bundle.getString("description");
        int bet = bundle.getInt("bet");
        int position = bundle.getInt("position");
        final int players = bundle.getInt("players");
        String startdate = bundle.getString("startdate");
        String enddate = bundle.getString("enddate");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        day = Float.parseFloat(""+ enddate.charAt(3) +""+ enddate.charAt(4));
        month = Float.parseFloat(""+ enddate.charAt(0) +""+ enddate.charAt(1));
         hours = Float.parseFloat(""+ enddate.charAt(11) +""+ enddate.charAt(12));
         minutes = Integer.parseInt(""+ enddate.charAt(14) +""+ enddate.charAt(15));


        TextView textView_name = findViewById(R.id.challenge_name3);
       TextView textView_bet = findViewById(R.id.bet_amount3);
       TextView textView_pot = findViewById(R.id.pot_value3);
       TextView textView_players = findViewById(R.id.player_count3);
        TextView textView_description = findViewById(R.id.description_2);
         textView_timer = findViewById(R.id.timer);
        ImageView image_cha = findViewById(R.id.image_cha);



        textView_bet.setText("Bet: $" + bet);
       textView_name.setText(name);
        textView_description.setText(description);
       textView_players.setText("Players: "+players);
       int pot =  bet * players ;
       textView_pot.setText("Pot value: " + pot);
        button_join = findViewById(R.id.join_challenge);

        int imageid = getResources().getIdentifier("ima"+(position+1), "drawable", "com.dhaval.enerbet");
        image_cha.setBackgroundResource(imageid);

        mDatabase.child("users").child(user.getUid()).child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue() == 0){
                button_join.setEnabled(false);
                button_join.setText("Joined the Challenge");}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    button_join.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           button_join.setEnabled(false);
           button_join.setText("Joined the Challenge");

           mDatabase.child("challenges").child(name).child("players").setValue(players + 1);
           mDatabase.child("users").child(user.getUid()).child(name).setValue(0);
        }
    });

         final Handler handler = new Handler();
         Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SetText();
                handler.postDelayed(this, 1000);
            }
        };


        handler.postDelayed(runnable, 1000);


}

void SetText(){

    Calendar calendar = Calendar.getInstance();
   int h = Math.round(hours -  calendar.get(Calendar.HOUR));
    int m = Math.round(minutes -  calendar.get(Calendar.MINUTE));
    int s = Math.round(seconds -  calendar.get(Calendar.SECOND));
    int d = Math.round(day - calendar.get(Calendar.DAY_OF_MONTH));
    int mo = Math.round(month - calendar.get(Calendar.MONTH));

    if(s<0){
        m = m-1;
        s = s + 60;
    }
    if(m<0){
        h = h-1;
        m = m + 60;
    }
    if(h<0){
        d = d-1;
        h = h +24;
    }

    if(d<0 ){
        h = 0;
        m = 0;
        s = 0;
        d = 0;
    }

    i++;
    textView_timer.setText("Time Remaining for Challenge to end: " + d + " days  " + h + ":" + m + ":" + s);
}

    }



