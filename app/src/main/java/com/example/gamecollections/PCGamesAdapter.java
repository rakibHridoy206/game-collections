package com.example.gamecollections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PCGamesAdapter extends RecyclerView.Adapter<PCGamesAdapter.ViewHolder> {
    private Context context;
    private List<GameInfo> gameInfoList;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference userIdRef;
    private DatabaseReference pcGameRef;
    private FirebaseUser currentUser;

    public PCGamesAdapter(Context context, List<GameInfo> gameInfoList) {
        this.context = context;
        this.gameInfoList = gameInfoList;
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("GameCollectionUser");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userIdRef = userRef.child(currentUser.getUid());
        pcGameRef = userIdRef.child("PCGameInformation");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_games_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GameInfo gameInfo = gameInfoList.get(position);
        final String gameID = gameInfo.getGameId();
        holder.gameNameTV.setText(gameInfoList.get(position).getGameName());
        holder.releaseYearTV.setText(gameInfoList.get(position).getReleaseYear());
        holder.genreTV.setText(gameInfoList.get(position).getGenre());
    }

    @Override
    public int getItemCount() {
        return gameInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView gameNameTV, releaseYearTV, genreTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameNameTV = itemView.findViewById(R.id.gameName);
            releaseYearTV = itemView.findViewById(R.id.releaseYear);
            genreTV = itemView.findViewById(R.id.genre);
        }
    }
}
