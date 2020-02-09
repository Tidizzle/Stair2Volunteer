package com.stair2.Volunteer.Async;

import android.os.AsyncTask;

import com.stair2.Volunteer.AppState;
import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Signup;

public class CreateSignupTask extends AsyncTask<Signup, Void, Integer>
{

    @Override
    protected Integer doInBackground(Signup... signups) {
        Signup s = signups[0];

        String query = String.format("INSERT INTO signups VALUES(%1$s, %2$s, %3$s, %4$s);", s.userId, s.eventId, 1, s.hourAmt);

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
