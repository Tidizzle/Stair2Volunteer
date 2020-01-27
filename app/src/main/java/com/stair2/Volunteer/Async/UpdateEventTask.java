package com.stair2.Volunteer.Async;

import android.os.AsyncTask;

import com.stair2.Volunteer.AppState;
import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Event;

public class UpdateEventTask extends AsyncTask<Event, Void, Integer>
{
    @Override
    protected Integer doInBackground(Event... events) {
        Event e = events[0];

        String query = String.format("UPDATE events SET title='%1$s', eventDesc='%2$s', location='%3$s', eventDate='%4$s', eventTime='%5$s', length='%6$s' WHERE eventId='%7$s';",
                e.title, e.description, e.location, e.date.toString(), e.time.toString(),
                e.length, e.eventId);

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
