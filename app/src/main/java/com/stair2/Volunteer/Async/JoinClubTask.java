package com.stair2.Volunteer.Async;

import android.os.AsyncTask;

import com.stair2.Volunteer.AppState;
import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Membership;

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

        AppState s = new AppState();
        s.LoadAppState();
    }
}
