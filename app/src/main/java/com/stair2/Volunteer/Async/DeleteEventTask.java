package com.stair2.Volunteer.Async;

import android.os.AsyncTask;

import com.stair2.Volunteer.AppState;
import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Event;

public class DeleteEventTask extends AsyncTask<Event, Void, Integer>
{
    @Override
    protected Integer doInBackground(Event... events) {
        Event e = events[0];

        String eventsQuery = String.format("DELETE FROM events WHERE eventId = %1$s;", e.eventId);
        String signupsQuery = String.format("DELETE FROM signups WHERE eventId = %1$s;", e.eventId);
        String endorseQuery = String.format("DELETE FROM endorsements WHERE eventId = %1$s;", e.eventId);

        DatabaseConnect.updateDatabase(eventsQuery);
        DatabaseConnect.updateDatabase(signupsQuery);
        DatabaseConnect.updateDatabase(endorseQuery);

        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        //reload the appstate to update the app with the latest info
        AppState s = new AppState();
        s.LoadAppState();
    }
}
