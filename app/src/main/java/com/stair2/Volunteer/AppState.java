package com.stair2.Volunteer;

import android.widget.EditText;

import com.stair2.Volunteer.Async.GetDatabaseStateTask;
import com.stair2.Volunteer.Callback.AsyncDatabaseStateResponse;
import com.stair2.Volunteer.DatabaseData.*;

/**
 * Static class stores the database state for the app
 * @author Zach Taylor
 */
public class AppState implements AsyncDatabaseStateResponse
{

    public static User LoggedInUser;
    public static int completesignups = 0;
    public static int incompletesignups = 0;

    public static DatabaseState state;

    public static void InterpretState()
    {
        completesignups = state.getCompleteSignups(LoggedInUser.userId);
        incompletesignups = state.getIncompleteSignups(LoggedInUser.userId);


    }


    public GetDatabaseStateTask dbtask = new GetDatabaseStateTask();

    public void LoadAppState()
    {
        dbtask.delegate = this;

        dbtask.execute();
    }

    @Override
    public void ProcessFinish(DatabaseState st)
    {
        state = st;
    }
}
