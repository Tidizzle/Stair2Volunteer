package com.stair2.Volunteer.Async;

import android.os.AsyncTask;

import com.stair2.Volunteer.AppState;
import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Event;

public class CreateEventTask extends AsyncTask<Event, Void, Integer>
{
    @Override
    protected Integer doInBackground(Event... events) {
        Event e = events[0];

        String query = String.format("INSERT INTO events VALUES(%1$s, %2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s', %8$s);",
                e.eventId, e.ownerId, e.title, e.description,
                e.location, e.date.toString(), e.time.toString(), e.length);

        DatabaseConnect.updateDatabase(query);

        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {

        //reload the appstate to update the new info
        AppState s = new AppState();
        s.LoadAppState();
    }
}
