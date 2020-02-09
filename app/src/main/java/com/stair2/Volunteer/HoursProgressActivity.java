package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.*;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stair2.Volunteer.DatabaseData.Club;
import com.stair2.Volunteer.DatabaseData.Endorsement;
import com.stair2.Volunteer.DatabaseData.Signup;

import java.util.ArrayList;


public class HoursProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours_progress);

        ArrayList<Club> usersClubs = AppState.state.getClubs(AppState.LoggedInUser.userId);
        ArrayList<ProgressItem> progresses = new ArrayList<>();

        for(int i = 0; i < usersClubs.size(); i++)
        {
            progresses.add(new ProgressItem(usersClubs.get(i), ProgressItem.getSignups(usersClubs.get(i).clubId,AppState.LoggedInUser.userId)));
        }

        generateCards(progresses);
    }

    public int clamp(double i, double l, double u)
    {
        if(i >= l && i <= u)
            return (int)i;
        else if (i<l)
            return (int)l;
        else if (i>u)
            return (int)u;

        return 0;
    }

    public void generateCards(ArrayList<ProgressItem> prog)
    {
        LinearLayout rootContainer = ((LinearLayout)findViewById(R.id.hoursProgress_Container));
        Context appContext = getApplicationContext();

        for(int i = 0; i < prog.size(); i++)
        {
            ProgressItem pi = prog.get(i);

            int amt;

            if(pi.parentClub.requiredHours > 0)
            {
                double hours = pi.getHoursProgress();
                double raw = hours / (double)pi.parentClub.requiredHours;
                raw *= 100;

                amt = clamp(raw, 0, 100);

            }
            else
                amt = 100;


            //card
            CardView card = new CardView(appContext);

            LayoutParams cp = new LayoutParams(LayoutParams.MATCH_PARENT, 250);
            cp.bottomMargin = 20;

            card.setLayoutParams(cp);
            card.setRadius(25f);
            card.setElevation(15f);
            card.setPreventCornerOverlap(true);

            //card lin layout
            LinearLayout cardLayout = new LinearLayout(appContext);
            LayoutParams clp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            cardLayout.setLayoutParams(clp);
            cardLayout.setOrientation(LinearLayout.VERTICAL);

            //title text
            TextView titleText = new TextView(appContext);
            LayoutParams ttp = new LayoutParams(LayoutParams.MATCH_PARENT, 75);
            titleText.setLayoutParams(ttp);
            titleText.setPadding(20,0,0,0);
            titleText.setText(pi.parentClub.clubName);
            titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            //create top divider
            View topDiv = new View(appContext);
            LayoutParams divparams = new LayoutParams(LayoutParams.MATCH_PARENT, 5);
            divparams.setMargins(0,10,0,4);
            topDiv.setLayoutParams(divparams);
            topDiv.setBackgroundColor(Color.parseColor("#cecece"));
            topDiv.setAlpha(0.5f);

            //create horiz lin layout
            LinearLayout contentlayout = new LinearLayout(appContext);
            LayoutParams contentlp = new LayoutParams(LayoutParams.MATCH_PARENT, 220);
            contentlp.setMarginEnd(20);
            contentlp.setMarginStart(20);
            contentlayout.setLayoutParams(contentlp);
            contentlayout.setOrientation(LinearLayout.VERTICAL);

            //create progress bar
            ProgressBar progressBar = (ProgressBar)this.getLayoutInflater().inflate(R.layout.progress_bar, null,false);
            progressBar.setProgress(amt,true);



            //create label text view
            TextView labelText = (TextView)this.getLayoutInflater().inflate(R.layout.progress_text, null,false);
            labelText.setText(Integer.toString(pi.getHoursProgress())+ "/" + Integer.toString(pi.parentClub.requiredHours) + " hr");

            contentlayout.addView(labelText);
            contentlayout.addView(progressBar);


            cardLayout.addView(titleText);
            cardLayout.addView(topDiv);
            cardLayout.addView(contentlayout);

            card.addView(cardLayout);
            rootContainer.addView(card);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}

class ProgressItem
{
    Club parentClub;
    ArrayList<Signup> signups;

    public ProgressItem(Club c, ArrayList<Signup> s)
    {
        parentClub = c;
        signups = s;
    }

    public int getHoursProgress()
    {
        int currentTotal = 0;

        for(int i = 0; i < signups.size(); i++)
        {
            currentTotal += signups.get(i).hourAmt;
        }

        return currentTotal;
    }

    public static ArrayList<Signup> getSignups(int clubId, int userId )
    {


        ArrayList<Integer> sponsoredEventIds = new ArrayList<>();

        for(int i = 0; i < AppState.state.endorsements.size(); i++)
        {
            Endorsement e = AppState.state.endorsements.get(i);

            if(e.clubId == clubId)
                sponsoredEventIds.add(e.eventId);
        }

        ArrayList<Signup> retlist = new ArrayList<>();

        for(int i = 0; i < AppState.state.signups.size(); i++)
        {
            Signup s = AppState.state.signups.get(i);

            if(sponsoredEventIds.contains(s.eventId) && s.userId == userId)
                retlist.add(s);
        }

        return retlist;
    }
}
