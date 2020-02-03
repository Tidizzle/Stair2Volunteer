package com.stair2.Volunteer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stair2.Volunteer.Async.JoinClubTask;
import com.stair2.Volunteer.DatabaseData.Club;
import com.stair2.Volunteer.DatabaseData.Membership;

import java.util.ArrayList;

public class JoinClubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_club);

        setTitle("Join a club");

        ArrayList<Club> usersNotJoinedClubs = AppState.state.getClubsNotJoined(AppState.LoggedInUser.userId);
        createCards(usersNotJoinedClubs);
    }

    public void createCards(ArrayList<Club> clubs)
    {

        Context appContext = getApplicationContext();
        LinearLayout rootContainer = ((LinearLayout)findViewById(R.id.joinClub_ClubContainer));

        for(int i = 0; i < clubs.size(); i++)
        {
            //create cardview
            CardView newCard = new CardView(appContext);

            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.bottomMargin = 25;
            newCard.setLayoutParams(cardParams);

            newCard.setElevation(15f);
            newCard.setRadius(25f);
            newCard.setPreventCornerOverlap(true);

            //create the cardview's child container
            LinearLayout cardContainer = new LinearLayout(appContext);
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            cardContainer.setLayoutParams(containerParams);
            cardContainer.setOrientation(LinearLayout.VERTICAL);

            //create a textview for title
            TextView cardTitle = new TextView(appContext);
            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    40
            );
            titleParams.setMarginStart(20);
            titleParams.topMargin = 10;
            cardTitle.setLayoutParams(titleParams);
            cardTitle.setText(clubs.get(i).clubName);
            cardTitle.setTextSize(15f);
            cardTitle.setGravity(Gravity.BOTTOM);
            cardTitle.setTypeface(cardTitle.getTypeface(), Typeface.BOLD);

            //create top divider
            View topDiv = new View(appContext);
            LinearLayout.LayoutParams divparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            divparams.setMargins(0,10,0,4);
            topDiv.setLayoutParams(divparams);
            topDiv.setBackgroundColor(Color.parseColor("#cecece"));
            topDiv.setAlpha(0.5f);

            //create description textview
            TextView desc = new TextView(appContext);
            LinearLayout.LayoutParams descparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            desc.setLayoutParams(descparams);
            desc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            desc.setText(clubs.get(i).clubDesc);

            //create bottom divider
            View botDiv = new View(appContext);
            botDiv.setLayoutParams(divparams);
            botDiv.setBackgroundColor(Color.parseColor("#cecece"));
            botDiv.setAlpha(0.5f);

            //create button
            Button btn = new Button(appContext);
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    100
            );
            btnparams.setMarginStart(20);
            btnparams.setMarginEnd(20);
            btn.setLayoutParams(btnparams);
            btn.setText("Join Club");
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            btn.setAllCaps(false);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setTag(clubs.get(i).clubId);
            btn.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v)
                {
                    joinClub(v);
                }
            });

            //add it all together
            cardContainer.addView(cardTitle);
            cardContainer.addView(topDiv);
            cardContainer.addView(desc);
            cardContainer.addView(botDiv);
            cardContainer.addView(btn);

            newCard.addView(cardContainer);
            rootContainer.addView(newCard);
        }
    }

    public void joinClub(View v)
    {
        int clubId = (int)v.getTag();

        Membership newMembership = new Membership(AppState.LoggedInUser.userId, clubId);
        JoinClubTask task = new JoinClubTask();

        Toast.makeText(this, "Joining Club...", Toast.LENGTH_LONG).show();
        task.execute(newMembership);

        Intent back = new Intent(this, ClubActivity.class);
        startActivity(back);
        finish();

    }

    public void joinClub(int input)
    {
        int clubId = input;

        Membership newMembership = new Membership(AppState.LoggedInUser.userId, clubId);
        JoinClubTask task = new JoinClubTask();

        Toast.makeText(this, "Joining Club...", Toast.LENGTH_LONG).show();
        task.execute(newMembership);

        Intent back = new Intent(this, ClubActivity.class);
        startActivity(back);
        finish();

    }

    public void clubCodeClick(View v)
    {
        String rawCode = ((EditText)findViewById(R.id.joinClub_CodeBox)).getText().toString();
        int clubCode = 0;

        try {
            clubCode = Integer.parseInt(rawCode);
        } catch(Exception e) {
            Toast.makeText(this,"Enter a valid club code!", Toast.LENGTH_LONG).show();
        }

        final Club requested = AppState.state.getClubFromId(clubCode);

        if(requested != null)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm")
                    .setMessage("Are you sure you want to join " + requested.clubName + "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            joinClub(requested.clubId);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
        else
            Toast.makeText(this, "Club not found!", Toast.LENGTH_LONG).show();
    }
}
