package com.stair2.Volunteer;

import android.widget.EditText;

import com.stair2.Volunteer.Async.GetDatabaseStateTask;
import com.stair2.Volunteer.Callback.AsyncDatabaseStateResponse;
import com.stair2.Volunteer.DatabaseData.*;

import java.util.ArrayList;

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

    public static ArrayList<Club> getClubs(int userid)
    {
        //collect all of the users memberships
        ArrayList<Membership> usersMemberships = new ArrayList<Membership>();
        for(int i = 0; i < state.memberships.size(); i++)
        {
            if(state.memberships.get(i).userId == userid)
                usersMemberships.add(state.memberships.get(i));
        }

        //collect all of the clubs that match the pattern
        ArrayList<Club> usersClubs = new ArrayList<>();
        for(int i = 0; i < state.clubs.size();i++)
        {
            Membership pattern = new Membership(userid,state.clubs.get(i).clubId);

            if(usersMemberships.contains(pattern))
                usersClubs.add(state.clubs.get(i));
        }

        return usersClubs;
    }

    public static ArrayList<Club> getOwnedClubs(int userid)
    {
        //collect all of the users memberships
        ArrayList<Membership> usersMemberships = new ArrayList<Membership>();
        for(int i = 0; i < state.memberships.size(); i++)
        {
            if(state.memberships.get(i).userId == userid)
                usersMemberships.add(state.memberships.get(i));
        }

        //collect all of the clubs that match the pattern
        ArrayList<Club> usersClubs = new ArrayList<>();
        for(int i = 0; i < state.clubs.size();i++)
        {
            Membership pattern = new Membership(userid,state.clubs.get(i).clubId);

            if(usersMemberships.contains(pattern))
                usersClubs.add(state.clubs.get(i));
        }

        return usersClubs;
    }


    @Override
    public void ProcessFinish(DatabaseState st)
    {
        state = st;
    }
}
