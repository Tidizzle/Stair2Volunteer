package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.CornerTreatment;
import com.stair2.Volunteer.Async.CreateEndorsementTask;
import com.stair2.Volunteer.Async.CreateEventTask;
import com.stair2.Volunteer.Async.UpdateEventTask;
import com.stair2.Volunteer.DatabaseData.Club;
import com.stair2.Volunteer.DatabaseData.Endorsement;
import com.stair2.Volunteer.DatabaseData.Event;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateEventActivity extends AppCompatActivity {

    int eventid;
    int chosenClubId = 0;

    ArrayList<Club> listedChips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);


        eventid = getIntent().getIntExtra("eventId", 0);

        if(eventid != 0)
        {
            //put into edit mode
            Event e = AppState.state.getEventFromId(eventid);

            ((EditText)findViewById(R.id.createEvent_Title)).setText(e.title);
            ((EditText)findViewById(R.id.createEvent_Description)).setText(e.description);
            ((EditText)findViewById(R.id.createEvent_Location)).setText(e.location);
            ((EditText)findViewById(R.id.createEvent_StartTime)).setText(e.parseTime());
            ((EditText)findViewById(R.id.createEvent_StartTime)).setTag(e.time);
            ((EditText)findViewById(R.id.createEvent_Date)).setText(e.parseDate());
            ((EditText)findViewById(R.id.createEvent_Date)).setTag(e.date);
            ((EditText)findViewById(R.id.createEvent_Length)).setText(Integer.toString(e.length));

            ((Button)findViewById(R.id.createEvent_actionButton)).setText("Update");

            setTitle("Edit Event");
            //hide chips because we dont want people to change the sponsor
            findViewById(R.id.createEvent_ChipsLayout).setVisibility(View.INVISIBLE);

        }
        else
        {
            //put into create mode
            setTitle("Create Event");
            ArrayList<Club> clubs = filterOwnerOnly(AppState.state.clubs, AppState.LoggedInUser.userId);
            createChips(clubs);
            listedChips = clubs;
        }


    }

    /**
     * Sort the clubs to only those that the specified id is the owner of
     * @param dirtyList List of unsorted clubs
     * @param id userid to search for
     * @return Cleaned list of clubs
     */
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

    public void createChips(ArrayList<Club> clubs)
    {
        Context c = getApplicationContext();
        ChipGroup root = ((ChipGroup)findViewById(R.id.createEvent_Chips));

        for(int i = 0; i < clubs.size(); i++)
        {
            Club club = clubs.get(i);

            /*Chip nChp = new Chip(this, null, R.style.Widget_MaterialComponents_Chip_Choice);
            LayoutParams lp = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    130
            );
            nChp.setLayoutParams(lp);

            nChp.setText(club.clubName);
            nChp.setShapeAppearanceModel(nChp.getShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 45).build());
            nChp.setCheckable(true);
            nChp.setCheckedIcon(getResources().getDrawable(R.drawable.ic_check_icon));*/

            Chip nChp = (Chip)this.getLayoutInflater().inflate(R.layout.event_chip, null, false);
            nChp.setText(club.clubName);
            nChp.setTag(club.clubId);

            root.addView(nChp);

        }
    }

    public void chipClick(View view)
    {
        chosenClubId = Integer.parseInt(view.getTag().toString());
    }

    public void dateClick(View view)
    {
        DatePickerDialog d = new DatePickerDialog(this);
        d.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateReturned(year, month, dayOfMonth);
            }
        });
        d.show();
    }

    public void dateReturned(int year, int month, int day)
    {
        java.sql.Date date = new java.sql.Date(new GregorianCalendar(year,month,day).getTime().getTime());
        ((EditText)findViewById(R.id.createEvent_Date)).setText(parseDate(date));
        ((EditText)findViewById(R.id.createEvent_Date)).setTag(date);

    }

    public String parseDate(java.sql.Date date)
    {
        String raw = date.toString();
        String year = raw.substring(0,4);
        String month = raw.substring(5,7);
        String day = raw.substring(8);

        return month + "/" + day + "/" + year;
    }

    public void timeClick(View view)
    {
        TimePickerDialog t = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                timeReturned(hourOfDay, minute);
            }
        }, 5, 30, false);
        t.show();
    }

    public void timeReturned(int hour, int minute)
    {
        Time time = new Time(new GregorianCalendar(2010,2,2, hour, minute).getTime().getTime());
        ((EditText)findViewById(R.id.createEvent_StartTime)).setText(parseTime(time));
        ((EditText)findViewById(R.id.createEvent_StartTime)).setTag(time);
    }

    public String parseTime(Time time)
    {
        String raw = time.toString();
        int hour = Integer.parseInt(raw.substring(0,2));
        String minute = raw.substring(3,5);

        if(hour > 12)
            return (hour-12) + ":" + minute + " pm";
        else if (hour == 12)
            return hour + ":" + minute + " pm";
        else
            return hour + ":" + minute + " am";

    }

    public void actionClick(View view)
    {
        if(eventid == 0)
            createEventAction(view);
        else
            updateEventAction(view);
    }

    public void createEventAction(View view)
    {
        String title = ((EditText)findViewById(R.id.createEvent_Title)).getText().toString();
        String desc = ((EditText)findViewById(R.id.createEvent_Description)).getText().toString();
        String loc = ((EditText)findViewById(R.id.createEvent_Location)).getText().toString();

        if(title.length() <= 30 && title.length() > 0)
        {
            if(desc.length() <= 250 && desc.length() > 0)
            {
                if(loc.length() <= 45 && loc.length() > 0)
                {
                    if(findViewById(R.id.createEvent_Date).getTag() != null)
                    {
                        java.sql.Date date = (java.sql.Date)findViewById(R.id.createEvent_Date).getTag();

                        if(findViewById(R.id.createEvent_StartTime).getTag() != null)
                        {
                            Time time = (Time)findViewById(R.id.createEvent_StartTime).getTag();

                            if(((EditText)findViewById(R.id.createEvent_Length)).getText().length() > 0)
                            {
                                int length = Integer.parseInt(((EditText)findViewById(R.id.createEvent_Length)).getText().toString());
                                ChipGroup group = (ChipGroup)findViewById(R.id.createEvent_Chips);

                                int clubid = Integer.parseInt(((Chip)group.findViewById(group.getCheckedChipId())).getTag().toString());
                                Club club = AppState.state.getClubFromId(clubid);

                                Event newEvent = new Event(AppState.genNewGUID(), AppState.LoggedInUser.userId, title, desc, loc, date, time, length);
                                CreateEventTask task = new CreateEventTask();
                                task.execute(newEvent);

                                Endorsement endorse = new Endorsement(club.clubId, newEvent.eventId);
                                CreateEndorsementTask etask = new CreateEndorsementTask();
                                etask.execute(endorse);

                                AppState.state.events.add(newEvent);
                                AppState.state.endorsements.add(endorse);

                                Intent back = new Intent(this, EventActivity.class);
                                startActivity(back);
                                finish();
                            }
                            else
                                Toast.makeText(this, "Please enter the event length!", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(this,"Please select a time!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(this,"Please select a date!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this,"Enter a location no more than 40 characters!", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"Enter a description no more than 250 characters!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Enter a title no more than 30 characters!", Toast.LENGTH_SHORT).show();

    }

    public void updateEventAction(View view)
    {
        String title = ((EditText)findViewById(R.id.createEvent_Title)).getText().toString();
        String desc = ((EditText)findViewById(R.id.createEvent_Description)).getText().toString();
        String loc = ((EditText)findViewById(R.id.createEvent_Location)).getText().toString();

        if(title.length() <= 30 && title.length() > 0)
        {
            if(desc.length() <= 250 && desc.length() > 0)
            {
                if(loc.length() <= 45 && loc.length() > 0)
                {
                    if(findViewById(R.id.createEvent_Date).getTag() != null)
                    {
                        java.sql.Date date = (java.sql.Date)findViewById(R.id.createEvent_Date).getTag();

                        if(findViewById(R.id.createEvent_StartTime).getTag() != null)
                        {
                            Time time = (Time)findViewById(R.id.createEvent_StartTime).getTag();

                            if(((EditText)findViewById(R.id.createEvent_Length)).getText().length() > 0)
                            {
                                int length = Integer.parseInt(((EditText)findViewById(R.id.createEvent_Length)).getText().toString());

                                Event currentEvent = AppState.state.getEventFromId(eventid);

                                Event updatedEvent = new Event(currentEvent.eventId, AppState.LoggedInUser.userId, title, desc, loc, date, time, length);

                                UpdateEventTask task = new UpdateEventTask();
                                task.execute(updatedEvent);

                                AppState.state.events.remove(currentEvent);
                                AppState.state.events.add(updatedEvent);

                                Intent back = new Intent(this, EventActivity.class);
                                startActivity(back);
                                finish();
                            }
                            else
                                Toast.makeText(this, "Please enter the event length!", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(this,"Please select a time!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(this,"Please select a date!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this,"Enter a location no more than 40 characters!", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"Enter a description no more than 250 characters!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Enter a title no more than 30 characters!", Toast.LENGTH_SHORT).show();



    }
}
