package com.stair2.Volunteer.Async;

import android.os.AsyncTask;

import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Club;

/**
 * Updates the database record for an already existing club with changed info
 */
public class UpdateClubTask extends AsyncTask<Club, Void, Integer>
{
    @Override
    protected Integer doInBackground(Club... clubs) {

        Club club = clubs[0];

        String query = String.format("UPDATE clubs SET clubName = '%1$s', clubDesc = '%2$s', siteUrl = '%3$s', reqHours = %4$s WHERE clubId = %5$s;",
                club.clubName,
                club.clubDesc,
                club.websiteUrl,
                club.requiredHours,
                club.clubId
                );

        DatabaseConnect.updateDatabase(query);
        return 0;
    }
}
