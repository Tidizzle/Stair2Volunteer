package com.stair2.Volunteer.Async;

import android.os.AsyncTask;

import com.stair2.Volunteer.AppState;
import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Membership;

/**
 * Updates the database by removing a membership
 * @author Zach Taylor
 */
public class LeaveClubTask extends AsyncTask<Membership, Void, Integer>
{
    @Override
    protected Integer doInBackground(Membership... memberships)
    {
        Membership t = memberships[0];

        String query = String.format("DELETE FROM memberships WHERE clubId = %1$s AND userId = %2$s;",t.clubId, t.userId);
        DatabaseConnect.updateDatabase(query);

        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer)
    {
        //reload appstate for new info
        AppState s = new AppState();
        s.LoadAppState();
    }
}
