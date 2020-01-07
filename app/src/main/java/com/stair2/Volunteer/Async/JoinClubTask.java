package com.stair2.Volunteer.Async;

import android.os.AsyncTask;

import com.stair2.Volunteer.AppState;
import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Membership;

/**
 * Updates the database with a membership of a new user joining a club
 * @author Zach Taylor
 */
public class JoinClubTask extends AsyncTask<Membership, Void, Integer>
{
    @Override
    protected Integer doInBackground(Membership... memberships) {

        Membership m = memberships[0];

        String query = String.format("INSERT INTO memberships VALUES(%1$s, %2$s);", m.clubId, m.userId);

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
