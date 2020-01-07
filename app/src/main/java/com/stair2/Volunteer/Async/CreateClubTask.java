package com.stair2.Volunteer.Async;

import android.os.AsyncTask;

import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Club;
import com.stair2.Volunteer.DatabaseData.Membership;

/**
 * Updates the database with a new club
 * @author Zach Taylor
 */

public class CreateClubTask extends AsyncTask<Club, Void, Membership>
{
    @Override
    protected Membership doInBackground(Club... clubs)
    {
        Club nclub = clubs[0];

        String query = String.format("INSERT INTO clubs VALUES(%1$s, %2$s, '%3$s', '%4$s', '%5$s', %6$s);", nclub.clubId, nclub.ownerId, nclub.clubName, nclub.clubDesc, nclub.websiteUrl, nclub.requiredHours);
        DatabaseConnect.updateDatabase(query);

        //return the membership that is to be created by OnPostExecute
        return new Membership(nclub.ownerId, nclub.clubId);

    }

    @Override
    protected void onPostExecute(Membership m) {

        //create the membership for the owner of the club
        JoinClubTask membershipcreate = new JoinClubTask();
        membershipcreate.execute(m);
    }
}
