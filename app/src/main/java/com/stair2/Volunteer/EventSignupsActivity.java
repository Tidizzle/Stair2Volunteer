package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stair2.Volunteer.DatabaseData.Event;
import com.stair2.Volunteer.DatabaseData.User;

import java.util.ArrayList;

public class EventSignupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_signups);

        int eventId = getIntent().getIntExtra("eventId", 0);
        ArrayList<User> users = AppState.state.getUsersFromEventId(eventId);
        Event e = AppState.state.getEventFromId(eventId);

        setTitle("Event Signups");
        ((TextView)findViewById(R.id.eventSignups_EventName)).setText(e.title);

        LinearLayout cont = findViewById(R.id.eventSignups_UserContainer);

        for(int i = 0; i < users.size(); i++)
        {

            TextView t = new TextView(getApplicationContext());

            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            p.setMarginStart(12);
            p.topMargin = 10;
            t.setLayoutParams(p);

            User target = users.get(i);
            t.setText(target.firstName + " " + target.lastName);
            t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18 );

            cont.addView(t);

        }
    }
}
