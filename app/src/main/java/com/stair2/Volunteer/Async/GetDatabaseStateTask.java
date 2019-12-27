package com.stair2.Volunteer.Async;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.util.Log;

import com.stair2.Volunteer.AppState;
import com.stair2.Volunteer.Callback.AsyncDatabaseStateResponse;
import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.Club;
import com.stair2.Volunteer.DatabaseData.DatabaseState;
import com.stair2.Volunteer.DatabaseData.Event;
import com.stair2.Volunteer.DatabaseData.Signup;
import com.stair2.Volunteer.DatabaseData.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Task to download the database information and store it in a databasestate object
 * @author Zach Taylor
 */
public class GetDatabaseStateTask extends AsyncTask<Void, Void, DatabaseState>
{
    public AsyncDatabaseStateResponse delegate = null;

    private final String userQuery = "SELECT userId, firstName, lastName, userName FROM users;";
    private final String clubQuery = "SELECT * FROM clubs;";
    private final String eventQuery = "SELECT * FROM events;";
    private final String signupQuery = "SELECT * FROM signups;";


    @Override
    protected DatabaseState doInBackground(Void... voids)
    {
        //set threadpolicy to allow for access out
        StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(pol);

        DatabaseState newState = new DatabaseState();

        Connection c = null;

        try
        {
            //loads the driver class for use in connecting to db
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

            //make the connection
            String connectionUrl = DatabaseConnect.getConnectionUrl();
            c = DriverManager.getConnection(connectionUrl);


            Statement stmt = c.createStatement();

            ResultSet results = stmt.executeQuery(userQuery);
            while(results.next())
            {
                int userid = results.getInt("userId");
                String fname = results.getString("firstName");
                String lname = results.getString("lastName");
                String uname = results.getString("userName");

                newState.users.add(new User(userid, fname, lname, uname));
            }
            results.close();


            results = stmt.executeQuery(clubQuery);
            while(results.next())
            {
                int clubid = results.getInt("clubId");
                int ownerid = results.getInt("ownerId");
                String clubName = results.getString("clubName");
                String desc = results.getString("clubDesc");

                newState.clubs.add(new Club(clubid, ownerid, clubName, desc));
            }
            results.close();


            results = stmt.executeQuery(eventQuery);
            while(results.next())
            {
                int eventId = results.getInt("eventId");
                int ownerId = results.getInt("ownerId");
                String title = results.getString("title");
                String desc = results.getString("eventDesc");
                String location = results.getString("location");
                Date date = results.getDate("eventDate");

                newState.events.add(new Event(eventId, ownerId, title, desc, location, date));
            }
            results.close();

            results = stmt.executeQuery(signupQuery);
            while(results.next())
            {
                int userId = results.getInt("userId");
                int eventId = results.getInt("eventId");
                int confirm = results.getInt("confirmation");
                int hour = results.getInt("hourAmt");

                newState.signups.add(new Signup(userId, eventId, confirm, hour));
            }
            results.close();


            //make sure to close all the connections
            stmt.close();
            c.close();
        }
        catch(Exception e)
        {
            Log.e("GetDatabaseStateTask:doInBackground", "Exception Thrown", e);
        }

        return newState;
    }

    @Override
    protected void onPostExecute(DatabaseState databaseState)
    {
        delegate.ProcessFinish(databaseState);
    }
}
