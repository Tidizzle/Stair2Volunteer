package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stair2.Volunteer.DatabaseData.Event;
import com.stair2.Volunteer.DatabaseData.Signup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ViewSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sign_up);

        ArrayList<Signup> signups = AppState.state.getSignupsFromUserId(AppState.LoggedInUser.userId);

        setTitle("Your Sign Ups");

        if(signups.size() == 0)
            GenNoSignupCard();
        else
            Gen(signups);
    }

    public void Gen(ArrayList<Signup> signups)
    {
        java.util.Date today = new Date();

        ArrayList<Event> passed = new ArrayList<>();
        ArrayList<Event> upcoming = new ArrayList<>();

        for(int i = 0; i < signups.size(); i++)
        {
            Signup s = signups.get(i);
            Event e = AppState.state.getEventFromId(s.eventId);
            java.util.Date edate = new Date(e.date.getTime());

            if(edate.before(today))
                passed.add(e);
            else
                upcoming.add(e);

        }

        Collections.sort(upcoming);
        Collections.sort(passed, Collections.<Event>reverseOrder());



        GenUpcomingCards(upcoming);
        GenPassedCards(passed);
    }

    public void GenUpcomingCards(ArrayList<Event> es)
    {
        Context context = getApplicationContext();
        LinearLayout rootCont = ((LinearLayout)findViewById(R.id.signups_upcomingContainer));

        for(int i = 0; i < es.size(); i++)
        {
            Event event = es.get(i);


            //card
            CardView card = new CardView(context);
            LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    400
            );
            clp.bottomMargin = 25;
            card.setLayoutParams(clp);

            card.setRadius(15f);
            card.setElevation(15f);
            card.setPreventCornerOverlap(true);

            //card container
            LinearLayout cardCont = new LinearLayout(context);
            LinearLayout.LayoutParams cclp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            cardCont.setLayoutParams(cclp);

            cardCont.setOrientation(LinearLayout.VERTICAL);

            //title textview
            TextView title = new TextView(context);
            LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    55
            );
            tlp.topMargin = 10;
            title.setLayoutParams(tlp);

            title.setPadding(20,0,0,0);
            title.setText(event.title);
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            title.setGravity(Gravity.BOTTOM);

            //divider
            View topDiv = new View(context);
            LinearLayout.LayoutParams divparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            divparams.setMargins(0,10,0,4);
            topDiv.setLayoutParams(divparams);
            topDiv.setBackgroundColor(Color.parseColor("#cecece"));
            topDiv.setAlpha(0.5f);

            //desc textview
            TextView desc = new TextView(context);
            LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    150
            );
            dlp.setMarginStart(20);
            dlp.setMarginEnd(20);
            desc.setLayoutParams(dlp);

            desc.setText(event.description);

            //time textview
            TextView time = new TextView(context);
            LinearLayout.LayoutParams tdlp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    50
            );
            tdlp.setMarginStart(20);
            tdlp.setMarginEnd(20);
            time.setLayoutParams(tdlp);
            time.setText(event.parseDate() + " at " + event.parseTime());

            //divider
            View botdiv = new View(context);
            LinearLayout.LayoutParams bdlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            bdlp.setMargins(0,10,0,4);
            //botdiv.setLayoutParams(bdlp);
            botdiv.setLayoutParams(divparams);
            botdiv.setBackgroundColor(Color.parseColor("#cecece"));
            botdiv.setAlpha(0.5f);

            //manage button
            Button managebtn = new Button(context);
            LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    100
            );
            blp.setMarginStart(20);
            blp.setMarginEnd(20);

            managebtn.setLayoutParams(blp);
            managebtn.setText("Info");
            managebtn.setBackgroundColor(Color.TRANSPARENT);
            managebtn.setTag(event.eventId);
            managebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manageOnClick(v);
                }
            });

            //stack all views
            cardCont.addView(title);
            cardCont.addView(topDiv);
            cardCont.addView(desc);
            cardCont.addView(time);
            cardCont.addView(botdiv);
            cardCont.addView(managebtn);

            card.addView(cardCont);

            rootCont.addView(card);

        }
    }

    public void GenPassedCards(ArrayList<Event> es)
    {
        Context context = getApplicationContext();
        LinearLayout rootCont = ((LinearLayout)findViewById(R.id.signups_passedContainer));

        for(int i = 0; i < es.size(); i++)
        {
            Event event = es.get(i);


            //card
            CardView card = new CardView(context);
            LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    400
            );
            clp.bottomMargin = 25;
            card.setLayoutParams(clp);

            card.setRadius(15f);
            card.setElevation(15f);
            card.setPreventCornerOverlap(true);

            //card container
            LinearLayout cardCont = new LinearLayout(context);
            LinearLayout.LayoutParams cclp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            cardCont.setLayoutParams(cclp);

            cardCont.setOrientation(LinearLayout.VERTICAL);

            //title textview
            TextView title = new TextView(context);
            LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    55
            );
            tlp.topMargin = 10;
            title.setLayoutParams(tlp);

            title.setPadding(20,0,0,0);
            title.setText(event.title);
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            title.setGravity(Gravity.BOTTOM);

            //divider
            View topDiv = new View(context);
            LinearLayout.LayoutParams divparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            divparams.setMargins(0,10,0,4);
            topDiv.setLayoutParams(divparams);
            topDiv.setBackgroundColor(Color.parseColor("#cecece"));
            topDiv.setAlpha(0.5f);

            //desc textview
            TextView desc = new TextView(context);
            LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    150
            );
            dlp.setMarginStart(20);
            dlp.setMarginEnd(20);
            desc.setLayoutParams(dlp);

            desc.setText(event.description);

            //time textview
            TextView time = new TextView(context);
            LinearLayout.LayoutParams tdlp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    50
            );
            tdlp.setMarginStart(20);
            tdlp.setMarginEnd(20);
            time.setLayoutParams(tdlp);
            time.setText(event.parseDate() + " at " + event.parseTime());

            //divider
            View botdiv = new View(context);
            LinearLayout.LayoutParams bdlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            bdlp.setMargins(0,10,0,4);
            //botdiv.setLayoutParams(bdlp);
            botdiv.setLayoutParams(divparams);
            botdiv.setBackgroundColor(Color.parseColor("#cecece"));
            botdiv.setAlpha(0.5f);

            //manage button
            Button managebtn = new Button(context);
            LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    100
            );
            blp.setMarginStart(20);
            blp.setMarginEnd(20);

            managebtn.setLayoutParams(blp);
            managebtn.setText("Info");
            managebtn.setBackgroundColor(Color.TRANSPARENT);
            managebtn.setTag(event.eventId);
            managebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manageOnClick(v);
                }
            });

            //stack all views
            cardCont.addView(title);
            cardCont.addView(topDiv);
            cardCont.addView(desc);
            cardCont.addView(time);
            cardCont.addView(botdiv);
            cardCont.addView(managebtn);

            card.addView(cardCont);

            rootCont.addView(card);

        }
    }

    public void GenNoSignupCard()
    {
        CardView card = new CardView(getApplicationContext());

        LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200 );
        clp.topMargin = 25;
        card.setLayoutParams(clp);

        card.setRadius(25f);
        card.setElevation(15f);
        card.setPreventCornerOverlap(true);

        TextView content = new TextView(getApplicationContext());
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        tlp.setMargins(75,0,75,0);
        content.setLayoutParams(tlp);
        content.setGravity(Gravity.CENTER);
        content.setText("You have not signed up for an event yet. Visit the feed to sign up.");
        content.setTextSize(18f);

        card.addView(content);
        ((LinearLayout)findViewById(R.id.signups_upcomingContainer)).addView(card);


    }

    public void manageOnClick(View view)
    {
        int eventId = (int)view.getTag();

        Intent detailI = new Intent(this, EventDetailActivity.class);
        detailI.putExtra("eventId", eventId);
        detailI.putExtra("detailType", EventDetailActivity.TYPE_SIGNEDUP);
        startActivity(detailI);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
