package com.stair2.Volunteer.Async;

import android.os.AsyncTask;

import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Endorsement;

public class CreateEndorsementTask extends AsyncTask<Endorsement, Void, Integer>
{
    @Override
    protected Integer doInBackground(Endorsement... endorsements) {
        Endorsement e = endorsements[0];

        String query = String.format("INSERT INTO endorsements VALUES(%1$s, %2$s);", e.clubId, e.eventId);

        DatabaseConnect.updateDatabase(query);

        return 0;
    }
}
