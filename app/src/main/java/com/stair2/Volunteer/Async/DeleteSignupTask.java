package com.stair2.Volunteer.Async;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.text.style.TtsSpan;

import com.stair2.Volunteer.AppState;
import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Signup;

public class DeleteSignupTask extends AsyncTask<Signup, Void, Integer>
{
    @Override
    protected Integer doInBackground(Signup... signups) {

        Signup s = signups[0];

        String query = String.format("DELETE FROM signups WHERE userId = '%1$s' AND eventId = '%2$s';", s.userId, s.eventId);
        DatabaseConnect.updateDatabase(query);

        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        //reload the appstate to update the app with the latest info
        AppState s = new AppState();
        s.LoadAppState();
    }
}
