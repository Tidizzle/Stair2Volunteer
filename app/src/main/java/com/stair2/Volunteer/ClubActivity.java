package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.stair2.Volunteer.DatabaseData.Club;


import java.util.ArrayList;
import java.util.List;

public class ClubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        setTitle("Clubs");


        int id;
        if(AppState.LoggedInUser == null)
            id = 0;
        else
            id = AppState.LoggedInUser.userId;


        ArrayList<Club> clubs = AppState.getClubs(id);
        createCards(filterMemberOnly(clubs, id));
        createOwnedCards(filterOwnerOnly(clubs, id));

    }

    public ArrayList<Club> filterMemberOnly(ArrayList<Club> dirtyList, int id)
    {
        ArrayList<Club> cleanedList = new ArrayList<Club>();

        for(int i = 0; i < dirtyList.size(); i++)
        {
            Club c = dirtyList.get(i);
            if(c.ownerId != id)
                cleanedList.add(c);
        }

        return cleanedList;
    }

    public ArrayList<Club> filterOwnerOnly(ArrayList<Club> dirtyList, int id)
    {
        ArrayList<Club> cleanedList = new ArrayList<>();

        for(int i = 0; i < dirtyList.size(); i++)
        {
            Club c = dirtyList.get(i);
            if(c.ownerId == id)
                cleanedList.add(c);
        }

        return cleanedList;
    }

    public void createCards(ArrayList<Club> clubs)
    {

        Context appContext = getApplicationContext();
        LinearLayout rootContainer = ((LinearLayout)findViewById(R.id.club_memberClubContainer));

        for(int i = 0; i < clubs.size(); i++)
        {
            //create cardview
            CardView newCard = new CardView(appContext);

            LayoutParams cardParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
            );
            cardParams.bottomMargin = 25;
            newCard.setLayoutParams(cardParams);

            newCard.setElevation(15f);
            newCard.setRadius(25f);
            newCard.setPreventCornerOverlap(true);

            //create the cardview's child container
            LinearLayout cardContainer = new LinearLayout(appContext);
            LayoutParams containerParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            );
            cardContainer.setLayoutParams(containerParams);
            cardContainer.setOrientation(LinearLayout.VERTICAL);

            //create a textview for title
            TextView cardTitle = new TextView(appContext);
            LayoutParams titleParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
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
            LayoutParams descparams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
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
            LayoutParams btnparams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    100
            );
            btnparams.setMarginStart(20);
            btnparams.setMarginEnd(20);
            btn.setLayoutParams(btnparams);
            btn.setText("View");
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            btn.setAllCaps(false);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setTag(clubs.get(i).clubId);
            btn.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v)
                {
                   buttonClick(v);
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

    public void createOwnedCards(ArrayList<Club> clubs)
    {

        Context appContext = getApplicationContext();
        LinearLayout rootContainer = ((LinearLayout)findViewById(R.id.club_yourClubContainer));

        for(int i = 0; i < clubs.size(); i++)
        {
            //create cardview
            CardView newCard = new CardView(appContext);

            LayoutParams cardParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
            );
            cardParams.bottomMargin = 25;
            newCard.setLayoutParams(cardParams);

            newCard.setElevation(15f);
            newCard.setRadius(25f);
            newCard.setPreventCornerOverlap(true);

            //create the cardview's child container
            LinearLayout cardContainer = new LinearLayout(appContext);
            LayoutParams containerParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            );
            cardContainer.setLayoutParams(containerParams);
            cardContainer.setOrientation(LinearLayout.VERTICAL);

            //create a textview for title
            TextView cardTitle = new TextView(appContext);
            LayoutParams titleParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
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
            LayoutParams descparams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
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
            LayoutParams btnparams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    100
            );
            btnparams.setMarginStart(20);
            btnparams.setMarginEnd(20);
            btn.setLayoutParams(btnparams);
            btn.setText("Manage");
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            btn.setAllCaps(false);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setTag(clubs.get(i).clubId);
            btn.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v)
                {
                    manageClick(v);
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

    public void buttonClick(View view)
    {
        int clubId = (int)view.getTag();

        Intent clubDetailIntent = new Intent(this, ClubDetailActivity.class);
        clubDetailIntent.putExtra("clubId", clubId);
        startActivity(clubDetailIntent);
    }

    public void manageClick(View view)
    {
        int clubId = (int)view.getTag();

        Intent clubDetailIntent = new Intent(this, ClubDetailActivity.class);
        clubDetailIntent.putExtra("clubId", clubId);
        clubDetailIntent.putExtra("detailType", 1); //open detail as manage
        startActivity(clubDetailIntent);
    }

    public void joinClubs(View view)
    {
        Intent joinClubActivity = new Intent(this, JoinClubActivity.class);
        startActivity(joinClubActivity);
    }

    public void createClub(View view)
    {
        Intent createClubActivity = new Intent(this, CreateClubActivity.class);
        startActivity(createClubActivity);
    }

    public void refresh(View view)
    {
        Intent i = getIntent();
        startActivity(i);
        finish();
    }


}
