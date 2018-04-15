package com.dhaval.enerbet;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<ChallengesClass> challengesClasslist;
    ViewGroup buffer;
     public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
         TextView textView_name,textView_bet,textView_players,textView_pot;
         LinearLayout linearLayout_card;
         ImageView imageView_card;
         ViewHolder(View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.challenge_name);
             textView_bet = itemView.findViewById(R.id.bet_amount2);
             textView_pot = itemView.findViewById(R.id.pot_value);
             textView_players = itemView.findViewById(R.id.player_count2);
             linearLayout_card = itemView.findViewById(R.id.card_layout);
             imageView_card = itemView.findViewById(R.id.image_card);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<ChallengesClass> challengesClasslist) {
        this.challengesClasslist = challengesClasslist;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        buffer = parent;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_card, parent, false);
        return new MyAdapter.ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {

        final ChallengesClass challengesClass = challengesClasslist.get(position);
        holder.textView_name.setText(challengesClass.getName());
       holder.textView_bet.setText("Bet: $"+challengesClass.getBet());
      holder.textView_players.setText("Players: "+challengesClass.getPlayers());
        int pot = challengesClass.getBet() * challengesClass.getPlayers();
                holder.textView_pot.setText("Pot value: "+pot);
                int imageid = buffer.getResources().getIdentifier("image"+(position+1), "drawable", "com.dhaval.enerbet");
                holder.imageView_card.setBackgroundResource(imageid);
             //   holder.linearLayout_card.setAlpha((float) 0.6);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(buffer.getContext(), Challenge.class);
                intent.putExtra("position", position);
                intent.putExtra("name", challengesClass.getName());
                intent.putExtra("bet", challengesClass.getBet());
                intent.putExtra("players", challengesClass.getPlayers());
                intent.putExtra("description", challengesClass.getDescription());
                intent.putExtra("startdate", challengesClass.getStart_date());
                intent.putExtra("enddate", challengesClass.getEnd_date());
                buffer.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return challengesClasslist.size();
    }
}