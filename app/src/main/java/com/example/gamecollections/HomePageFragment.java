package com.example.gamecollections;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HomePageFragment extends Fragment {

    private Context context;
    private TextView pcGamesTV, androidGamesTV;

    private PCGamesListener pcGamesListener;
    private AndroidGamesListener androidGamesListener;

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        pcGamesListener = (PCGamesListener) context;
        androidGamesListener = (AndroidGamesListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pcGamesTV = view.findViewById(R.id.pcGames);
        androidGamesTV = view.findViewById(R.id.androidGames);

        pcGamesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pcGamesListener.onPCGamesClickSuccessful();
            }
        });
        androidGamesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidGamesListener.onAndroidGamesClickSuccess();
            }
        });
    }

    public interface PCGamesListener{
        void onPCGamesClickSuccessful();
    }

    public interface AndroidGamesListener{
        void onAndroidGamesClickSuccess();
    }
}
