package com.example.exoli.myapplication.fragments;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.exoli.myapplication.R;
import com.example.exoli.myapplication.res.DBController;
import com.example.exoli.myapplication.res.GUAdapter;
import com.example.exoli.myapplication.res.GameUser;

import java.util.ArrayList;

public class HighscoresTable extends Fragment{

    DBController dbController;


    public HighscoresTable(){
        //required empty constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.frag_hs_table,container,false);

        ListView listView = (ListView)view.findViewById(R.id.score_table);
        TextView textView = new TextView(getContext());
        textView.setText(R.string.highest_scores);

        dbController = new DBController(getContext());

        Cursor scores = dbController.highestScores();
        ArrayList<GameUser> listDataPlayers = new ArrayList<>();
        while(scores.moveToNext()){

            listDataPlayers.add(new GameUser(scores.getString(DBController.getColNumName()),
                    scores.getFloat(DBController.getColNumScore())));
        }

        GUAdapter adapter = new GUAdapter(getContext(),R.layout.game_user_layout,listDataPlayers);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private String getDifficulty(int diff){
        String str;
        switch (diff){
            case 1:
                str = "Easy";
                break;
            case 2:
                str = "Medium";
                break;
            case 3:
                str = "Hard";
                break;
             default:
                 str = null;
        }
        return str;
    }

}
