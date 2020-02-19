package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.stair2.Volunteer.Async.CreateSignupTask;
import com.stair2.Volunteer.Async.DeleteEventTask;
import com.stair2.Volunteer.Async.DeleteSignupTask;
import com.stair2.Volunteer.DatabaseData.Club;
import com.stair2.Volunteer.DatabaseData.Event;
import com.stair2.Volunteer.DatabaseData.Signup;

public class EventDetailActivity extends AppCompatActivity {

    public static final int TYPE_MANAGE = 1;
    public static final int TYPE_SIGNEDUP = 0;
    public static final int TYPE_NOTSIGNEDUP = 2;

    public int eventId;
    public int detailType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        eventId = getIntent().getIntExtra("eventId", 0);
        Event requestedEvent = AppState.state.getEventFromId(eventId);

        detailType = getIntent().getIntExtra("detailType", 0);


        ((TextView)findViewById(R.id.eventDetail_Title)).setText(requestedEvent.title);
        ((TextView)findViewById(R.id.eventDetail_Description)).setText(requestedEvent.description);
        ((TextView)findViewById(R.id.eventDetail_Location)).setText(requestedEvent.location);


        //get sponsor name
        if(AppState.state.getSponsorFromEventId(eventId) != null)
        {
            Club sponsor = AppState.state.getSponsorFromEventId(eventId);
            ((TextView)findViewById(R.id.eventDetail_Sponsor)).setText("Sponsor: " + sponsor.clubName);
        }
        else
        {
            ((TextView)findViewById(R.id.eventDetail_Sponsor)).setText("Sponsor: None");
        }



        ((TextView)findViewById(R.id.eventDetail_DateTime)).setText(requestedEvent.parseDate() + ", " + requestedEvent.parseTime() +", " + Integer.toString(requestedEvent.length) + " hours");


        if(detailType == TYPE_MANAGE)
        {
            findViewById(R.id.eventDetail_Leave).setVisibility(View.GONE);
            findViewById(R.id.eventDetail_CreateSignup).setVisibility(View.GONE);

        }
        else if(detailType == TYPE_SIGNEDUP)
        {
            findViewById(R.id.eventDetail_EditEvent).setVisibility(View.GONE);
            findViewById(R.id.eventDetail_DeleteEvent).setVisibility(View.GONE);
            findViewById(R.id.eventDetail_CreateSignup).setVisibility(View.GONE);


        }
        else if (detailType == TYPE_NOTSIGNEDUP)
        {

            findViewById(R.id.eventDetail_Leave).setVisibility(View.GONE);
            findViewById(R.id.eventDetail_EditEvent).setVisibility(View.GONE);
            findViewById(R.id.eventDetail_DeleteEvent).setVisibility(View.GONE);
        }

    }

    public void viewSignUps(View view)
    {
        Intent signups = new Intent(this, EventSignupsActivity.class);
        signups.putExtra("eventId", eventId);
        startActivity(signups);
    }

    public void leaveEvent(View view)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Confirm");
        b.setMessage("Are you sure you want to leave this event?");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //clicked yes
                contLeave();
            }
        });
        b.setNegativeButton("No", null);
        b.show();
    }

    public void contLeave()
    {
        Signup s = AppState.state.getSignupFromIds(AppState.LoggedInUser.userId, eventId);
        DeleteSignupTask task = new DeleteSignupTask();
        task.execute(s);

        AppState.state.signups.remove(s);

        Intent i = new Intent(this, ViewSignUpActivity.class);
        startActivity(i);
        finish();
    }

    public void editEvent(View view)
    {
        Intent edit = new Intent(this, CreateEventActivity.class);
        edit.putExtra("eventId", eventId);
        startActivity(edit);
    }

    public void deleteEvent(View view)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Confirm");
        b.setMessage("Are you sure you want to delete this event?");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //clicked yes
                contDelete();
            }
        });
        b.setNegativeButton("No", null);
        b.show();
    }

    public void contDelete()
    {
        Event target = AppState.state.getEventFromId(eventId);
        DeleteEventTask task = new DeleteEventTask();
        task.execute(target);

        AppState.state.events.remove(target);

        Intent i = new Intent(this, EventActivity.class);
        startActivity(i);
        finish();
    }

    public void createSignUp(View view)
    {
        Signup s = new Signup(AppState.LoggedInUser.userId, eventId, 1, AppState.state.getEventFromId(eventId).length);

        CreateSignupTask task = new CreateSignupTask();
        task.execute(s);

        AppState.state.signups.add(s);

        Intent i = new Intent(this, EventDetailActivity.class);
        i.putExtra("eventId", eventId);
        i.putExtra("detailType", EventDetailActivity.TYPE_SIGNEDUP);
        finish();
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
        if(detailType == TYPE_MANAGE)
        {
            Intent b = new Intent(this, EventActivity.class);
            startActivity(b);
        }
        else if (detailType == TYPE_SIGNEDUP)
        {
            Intent b = new Intent(this, ViewSignUpActivity.class);
            startActivity(b);
        }
        else if (detailType == TYPE_NOTSIGNEDUP)
        {
            Intent b = new Intent(this, FeedActivity.class);
            startActivity(b);
        }

        finish();
    }
}
