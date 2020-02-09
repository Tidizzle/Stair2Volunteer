package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.stair2.Volunteer.DatabaseData.Club;
import com.stair2.Volunteer.DatabaseData.Event;
import com.stair2.Volunteer.DatabaseData.Signup;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        setTitle("Feed");

        //get potential filter id
        int clubFilter = getIntent().getIntExtra("clubFilter", 0);
        ArrayList<Event> eventList = new ArrayList<>();

        //create the chips now so they exist when we check the filtered club if applicable
        createChips(AppState.state.clubs);

        if(clubFilter != 0)
        {
            eventList = AppState.state.getSponsoredEventsFromClubId(clubFilter);
            checkFilterChip(clubFilter);
        }
        else
            eventList = AppState.state.events;


        ArrayList<Event> finalEventList = new ArrayList<>();

        for(int i = 0; i < eventList.size(); i++)
        {
            Event e = eventList.get(i);
            Signup s = new Signup(AppState.LoggedInUser.userId, e.eventId, 1, e.length);

            if(!AppState.state.signups.contains(s))
                finalEventList.add(e);
        }


        createEventCards(finalEventList);

    }

    public void createEventCards(ArrayList<Event> list)
    {
        Context appContext = getApplicationContext();
        LinearLayout rootContainer = ((LinearLayout)findViewById(R.id.feed_itemContainer));

        if(list.size() == 0)
        {
            TextView noEventMsg = new TextView(appContext);
            LinearLayout.LayoutParams msgParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    60
            );
            msgParams.topMargin = 130;
            msgParams.gravity = Gravity.CENTER_HORIZONTAL;
            noEventMsg.setLayoutParams(msgParams);
            noEventMsg.setText("No Events Sponsored.");
            noEventMsg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            noEventMsg.setTypeface(noEventMsg.getTypeface(), Typeface.BOLD);
            noEventMsg.setGravity(Gravity.CENTER_HORIZONTAL);

            rootContainer.addView(noEventMsg);
        }
        else
        {
            for(int i = 0; i < list.size(); i++)
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
                cardTitle.setText(list.get(i).title);
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
                desc.setText(list.get(i).description);

                //create location textview
                TextView location = new TextView(appContext);
                LinearLayout.LayoutParams locparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                location.setLayoutParams(locparams);
                location.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                location.setText(list.get(i).location);

                boolean isSponsored = false;
                TextView spon = new TextView(appContext);
                //some events arent sponsored, make sure to check for it here
                if(AppState.state.getSponsorFromEventId(list.get(i).eventId) != null)
                {
                    isSponsored = true;
                    //create sponsor textview
                    spon = new TextView(appContext);
                    LinearLayout.LayoutParams sponparams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    spon.setLayoutParams(sponparams);
                    spon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                    spon.setText("Sponsor: " + AppState.state.getSponsorFromEventId(list.get(i).eventId).clubName);

                }


                //create timedate textview
                TextView timedate = new TextView(appContext);
                LinearLayout.LayoutParams tdparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                timedate.setLayoutParams(tdparams);
                timedate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                timedate.setText(list.get(i).parseDate() + ", " + list.get(i).parseTime());

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
                btn.setText("Info");
                btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                btn.setAllCaps(false);
                btn.setBackgroundColor(Color.TRANSPARENT);
                btn.setTag(list.get(i).eventId);
                btn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v)
                    {
                        viewEventInfo(v);
                    }
                });

                //add it all together
                cardContainer.addView(cardTitle);
                cardContainer.addView(topDiv);
                cardContainer.addView(desc);
                cardContainer.addView(location);
                if(isSponsored)
                    cardContainer.addView(spon);
                cardContainer.addView(timedate);
                cardContainer.addView(botDiv);
                cardContainer.addView(btn);

                newCard.addView(cardContainer);
                rootContainer.addView(newCard);
            }
        }



    }

    //Check the chip that corresponds to the club that was filtered for
    public void checkFilterChip(int clubId)
    {
        //get the group and num of chips for the loop
        ChipGroup grp = ((ChipGroup)findViewById(R.id.feed_filterchips));
        int numChips = grp.getChildCount();

        for(int i = 0; i < numChips; i++)
        {
            Chip c = ((Chip)grp.getChildAt(i));

            //if the tag is the same as the filter clubs id check that chip
            if(Integer.parseInt(c.getTag().toString()) == clubId)
            {
                grp.check(c.getId());
                break;
            }

        }
    }

    public void createChips(ArrayList<Club> clubs)
    {
        Context c = getApplicationContext();
        ChipGroup root = ((ChipGroup)findViewById(R.id.feed_filterchips));

        for(int i = 0; i < clubs.size(); i++)
        {
            Club club = clubs.get(i);

            Chip nChp = (Chip)this.getLayoutInflater().inflate(R.layout.event_chip, null, false);
            nChp.setText(club.clubName);
            nChp.setTag(club.clubId);
            nChp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chipClick(v);
                }
            });

            root.addView(nChp);
        }
    }

    public void chipClick(View v)
    {
        if(((Chip)v).isChecked())
        {
            int clubId = Integer.parseInt(v.getTag().toString());

            ArrayList<Event> filteredEvents = AppState.state.getSponsoredEventsFromClubId(clubId);
            LinearLayout itemContainer = ((LinearLayout)findViewById(R.id.feed_itemContainer));

            if(itemContainer.getChildCount() > 0)
                itemContainer.removeAllViews();

            createEventCards(filteredEvents);
        }
        else
        {
            LinearLayout itemContainer = ((LinearLayout)findViewById(R.id.feed_itemContainer));

            if(itemContainer.getChildCount() > 0)
                itemContainer.removeAllViews();

            createEventCards(AppState.state.events);

        }
    }


    public void viewEventInfo(View view)
    {
        int eventId = Integer.parseInt(view.getTag().toString());

        Intent eventDetail = new Intent(this, EventDetailActivity.class);
        eventDetail.putExtra("eventId", eventId);
        eventDetail.putExtra("detailType", EventDetailActivity.TYPE_NOTSIGNEDUP);
        startActivity(eventDetail);
    }

}
