package com.example.gamecollections;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AndroidGamesFragment extends Fragment {
    private Context context;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private CardView cardView;

    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference userIdRef;
    private DatabaseReference gameRef;
    private FirebaseUser currentUser;

    private AndroidGameAdapter androidGameAdapter;

    public AndroidGamesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("GameCollectionUsers");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userIdRef = userRef.child(currentUser.getUid());
        gameRef = userIdRef.child("AndroidGameInformation");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_android_games, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fabAndroid);
        recyclerView = view.findViewById(R.id.recyclerViewAndroid);

        gameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<GameInfo> gameInfoList = new ArrayList<>();
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    GameInfo gameInfo = d.getValue(GameInfo.class);
                    gameInfoList.add(gameInfo);
                }
                androidGameAdapter = new AndroidGameAdapter(context, gameInfoList);
                LinearLayoutManager llm = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(androidGameAdapter);
                androidGameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_games);

                final EditText gameNameET, releaseYearET, genreET;
                Button cancelBtn, saveBtn;
                gameNameET = dialog.findViewById(R.id.gameNameD);
                releaseYearET = dialog.findViewById(R.id.releseY);
                genreET = dialog.findViewById(R.id.genreG);
                cancelBtn = dialog.findViewById(R.id.cancel);
                saveBtn = dialog.findViewById(R.id.save);

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String gameName = gameNameET.getText().toString();
                        final String releaseYear = releaseYearET.getText().toString();
                        final String genre = genreET.getText().toString();
                        String pcGameID = gameRef.push().getKey();
                        GameInfo gameInfo = new GameInfo(pcGameID,gameName,releaseYear,genre);

                        gameRef.child(pcGameID).setValue(gameInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "PCGame Info Added", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                androidGameAdapter.notifyDataSetChanged();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Check Again", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.show();
            }
        });
    }
}
