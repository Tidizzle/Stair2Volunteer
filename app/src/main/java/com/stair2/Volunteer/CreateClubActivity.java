package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class CreateClubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);

        setTitle("Create a new Club");

        if(AppState.LoggedInUser.userId != 0)
            ((LinearLayout)findViewById(R.id.createClub_AdminDebug)).setVisibility(View.GONE);
    }

    public void createClick(View view)
    {
        //TODO: add create club
    }
}
