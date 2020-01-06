package com.stair2.Volunteer.Async;

import android.os.AsyncTask;

import com.stair2.Volunteer.AppState;
import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Club;

public class DeleteClubTask extends AsyncTask<Club, Void, Integer>
{
    @Override
    protected Integer doInBackground(Club... clubs) {

        Club target = clubs[0];

        String query = String.format("DELETE FROM clubs WHERE clubId = %1$s;", target.clubId);
        String membershipquery = String.format("DELETE FROM memberships WHERE clubId = %1$s;", target.clubId);
        String endoresequery = String.format("DELETE FROM endorsements WHERE clubId = %1$s;", target.clubId);

        DatabaseConnect.updateDatabase(query);
        DatabaseConnect.updateDatabase(membershipquery);
        DatabaseConnect.updateDatabase(endoresequery);

        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer)
    {
        AppState s = new AppState();
        s.LoadAppState();
    }
}
