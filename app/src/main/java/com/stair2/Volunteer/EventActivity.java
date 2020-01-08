package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.stair2.Volunteer.DatabaseData.Event;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        ArrayList<Event> events = AppState.state.getEvents(AppState.LoggedInUser.userId);

        if(events.size() == 0)
            GenNoEventCard();
        else
            GenEventCards(events);
    }

    public void GenNoEventCard()
    {
        CardView card = new CardView(getApplicationContext());

        LayoutParams clp = new LayoutParams(LayoutParams.MATCH_PARENT, 200 );
        clp.topMargin = 25;
        card.setLayoutParams(clp);

        card.setRadius(25f);
        card.setElevation(15f);
        card.setPreventCornerOverlap(true);

        TextView content = new TextView(getApplicationContext());
        LayoutParams tlp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        tlp.setMargins(75,0,75,0);
        content.setLayoutParams(tlp);
        content.setGravity(Gravity.CENTER);
        content.setText("You have not created an event yet. Click the plus to create.");
        content.setTextSize(18f);

        card.addView(content);
        ((LinearLayout)findViewById(R.id.event_eventContainer)).addView(card);
    }

    public void GenEventCards(ArrayList<Event> es)
    {
        Context context = getApplicationContext();
        LinearLayout rootCont = ((LinearLayout)findViewById(R.id.event_eventContainer));

        for(int i = 0; i < es.size(); i++)
        {
            Event event = es.get(i);


            //card
            CardView card = new CardView(context);
            LayoutParams clp = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    165
            );
            clp.bottomMargin = 14;
            card.setLayoutParams(clp);

            card.setRadius(15f);
            card.setElevation(15f);
            card.setPreventCornerOverlap(true);

            //card container
            LinearLayout cardCont = new LinearLayout(context);
            LayoutParams cclp = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            );
            cardCont.setLayoutParams(cclp);

            cardCont.setOrientation(LinearLayout.VERTICAL);

            //title textview
            TextView title = new TextView(context);
            LayoutParams tlp = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    25
            );
            title.setLayoutParams(tlp);

            title.setPadding(10,0,0,0);
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
            LayoutParams dlp = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    75
            );
            dlp.setMarginStart(20);
            dlp.setMarginEnd(20);
            desc.setLayoutParams(dlp);

            desc.setText(event.description);

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
            LayoutParams blp = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    40
            );
            blp.setMarginStart(20);
            blp.setMarginEnd(20);

            managebtn.setLayoutParams(blp);
            managebtn.setText("Manage");
            managebtn.setBackgroundColor(Color.TRANSPARENT);

            //stack all views
            cardCont.addView(title);
            cardCont.addView(topDiv);
            cardCont.addView(desc);
            cardCont.addView(botdiv);
            cardCont.addView(managebtn);

            card.addView(cardCont);

            rootCont.addView(card);

        }
    }
}