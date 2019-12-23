package com.stair2.Volunteer;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class CheckLoginTask extends AsyncTask<String, Integer, Boolean>
{

    public AsyncBooleanResponse delegate = null;

    private final String tableName = "users";
    private final String userField = "userName";
    private final String passField = "passwordHash";

    //NOTE:: TASKS THAT QUERY THE DATABASE FOR A RESULTSET HAVE TO BE DONE MANUALLY EVERY TIME

    @Override
    protected Boolean doInBackground(String... strings)
    {
        String uname = strings[0];
        String passhash = strings[1];

        //set threadpolicy to allow for access out
        StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(pol);

        boolean result = false;
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

            //return true if username isnt taken/false if taken
            results.next();
            if(results.getInt(1) == 1)
                result = true;
            else
                result = false;

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
    protected void onPostExecute(Boolean aBoolean)
    {
        //send result boolean to the delegate
        delegate.processFinish(aBoolean);
    }
}
