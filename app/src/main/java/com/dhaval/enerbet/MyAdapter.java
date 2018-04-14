package com.dhaval.enerbet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<ChallengesClass> challengesClasslist;

     public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
         TextView textView_name,textView_bet,textView_players,textView_pot;
         ViewHolder(View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.challenge_name);
             textView_bet = itemView.findViewById(R.id.bet_amount2);
             textView_pot = itemView.findViewById(R.id.pot_value);
             textView_players = itemView.findViewById(R.id.player_count2);
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
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_card, parent, false);
        return new MyAdapter.ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        ChallengesClass challengesClass = challengesClasslist.get(position);
        holder.textView_name.setText(challengesClass.getName());
       holder.textView_bet.setText("Bet: $"+challengesClass.getBet());
      holder.textView_players.setText("Players: "+challengesClass.getPlayers());
        int pot = challengesClass.getBet() * challengesClass.getPlayers();
                holder.textView_pot.setText("Pot value: "+pot);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return challengesClasslist.size();
    }
}