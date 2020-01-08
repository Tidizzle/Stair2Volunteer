package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.stair2.Volunteer.DatabaseData.Event;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        int eventid = getIntent().getIntExtra("eventId", 0);

        if(eventid != 0)
        {
            //put into edit mode
            Event e = AppState.state.getEventFromId(eventid);

            ((EditText)findViewById(R.id.createEvent_Title)).setText(e.title);
            ((EditText)findViewById(R.id.createEvent_Description)).setText(e.description);
            ((EditText)findViewById(R.id.createEvent_StartTime)).setText(e.parseTime());
            //TODO: Continue creating this
        }
        else
        {
            //put into create mode

        }
    }
}
