package com.stair2.Volunteer.Async;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.stair2.Volunteer.Callback.AsyncUserResponse;
import com.stair2.Volunteer.DatabaseConnect;
import com.stair2.Volunteer.DatabaseData.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Task checking if a login is valid, and returning the user that logs in
 * @author Zach Taylor
 */
public class CheckLoginTask extends AsyncTask<String, Integer, User>
{

    public AsyncUserResponse delegate = null;

    private final String tableName = "users";
    private final String userField = "userName";
    private final String passField = "passwordHash";

    //NOTE:: TASKS THAT QUERY THE DATABASE FOR A RESULTSET HAVE TO BE DONE MANUALLY EVERY TIME

    @Override
    protected User doInBackground(String... strings)
    {
        String uname = strings[0];
        String passhash = strings[1];

        //set threadpolicy to allow for access out
        StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(pol);

        User result = null;
        Connection c = null;

        try
        {
            //loads the driver class for use in connecting to db
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

            //make the connection
            String connectionUrl = DatabaseConnect.getConnectionUrl();
            c = DriverManager.getConnection(connectionUrl);

            String sqlquery = String.format("SELECT COUNT(*) FROM %1$s WHERE %2$s = '%3$s' AND %4$s = '%5$s';", tableName, userField, uname, passField, passhash);

            Statement stmt = c.createStatement();
            ResultSet results = stmt.executeQuery(sqlquery);

            //returns true if login exists, false if it doesnt
            results.next();
            if(results.getInt(1) == 1)
            {
                String userQuery = String.format("SELECT userId, firstName, lastName, userName FROM %1$s WHERE %2$s = '%3$s' AND %4$s = '%5$s';", tableName, userField, uname, passField, passhash);

                Statement userStmt = c.createStatement();
                ResultSet u = userStmt.executeQuery(userQuery);

                u.next();

                result = new User(u.getInt("userId"), u.getString("firstName"), u.getString("lastName"), u.getString("userName"));

                u.close();
                userStmt.close();
            }
            else
                result = null;



            //make sure to close all the connections
            results.close();
            stmt.close();
            c.close();
        }
        catch(Exception e)
        {
            Log.e("CheckLoginTask:doInBackground", "Exception Thrown", e);
        }

        return result;
    }

    @Override
    protected void onPostExecute(User user)
    {
        //send result boolean to the delegate
        delegate.processFinish(user);
    }
}
