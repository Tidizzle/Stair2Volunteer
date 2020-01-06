package com.stair2.Volunteer;

import android.widget.EditText;

import com.stair2.Volunteer.Async.GetDatabaseStateTask;
import com.stair2.Volunteer.Callback.AsyncDatabaseStateResponse;
import com.stair2.Volunteer.DatabaseData.*;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Static class stores the database state for the app
 * @author Zach Taylor
 */
public class AppState implements AsyncDatabaseStateResponse
{

    public static User LoggedInUser;
    public static int completesignups = 0;
    public static int incompletesignups = 0;

    private static final int GUID_LENGTH = 6;

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

    public static ArrayList<Club> getClubsNotJoined(int userid)
    {
        //collect all of the users memberships
        ArrayList<Membership> usersMemberships = new ArrayList<Membership>();
        for(int i = 0; i < state.memberships.size(); i++)
        {
            if(state.memberships.get(i).userId == userid)
                usersMemberships.add(state.memberships.get(i));
        }

        //collect all of the clubs that match the pattern
        ArrayList<Club> inverseUsersClubs = new ArrayList<>();
        for(int i = 0; i < state.clubs.size();i++)
        {
            Membership pattern = new Membership(userid,state.clubs.get(i).clubId);

            if(!usersMemberships.contains(pattern))
                inverseUsersClubs.add(state.clubs.get(i));
        }

        return inverseUsersClubs;
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

    /**
     * Create a new modified GUID for use as userid/eventid/clubid
     * length defined by GUID_LENGTH
     *
     * @return x digit UID, length defined by GUID_LENGTH
     */
    public static int genNewGUID()
    {
        //create a random uuid, convert to a char array for manipulation
        UUID source = UUID.randomUUID();
        char[] sourceArr = source.toString().toCharArray();
        String output = "";

        //add every digit to an array
        for(int i = 0; i < sourceArr.length; i++)
        {
            char t = sourceArr[i];

            if(Character.isDigit(t))
                if(output.length() > 0 || t != '0') //exclude 0's from the first digit for proper parsing
                    output += t;

            if(output.length() == GUID_LENGTH) //break when reaches x digits
                break;
        }

        return Integer.parseInt(output);
    }

    @Override
    public void ProcessFinish(DatabaseState st)
    {
        state = st;
    }
}
