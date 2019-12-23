package com.stair2.Volunteer;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnect
{
    private static final String userName = "applicationUser";
    private static final String passWord = "whsRsa2020!";
    private static final String serverName = "stair2";
    private static final String databaseName = "PROD";


    private static final String dbPrefix = "jdbc:jtds:sqlserver://";
    private static final String dbUrl = "stair2.database.windows.net:1433";

    private static final String useEncryption = "true";
    private static final String trustCert = "false";
    private static final String hostCertName = "*.database.windows.net";
    private static final String loginTimeout = "30";

    private static Connection dbConnection = null;


    /**
     * Get the database URL.
     * @return URL
     */
    public static String getConnectionUrl()
    {
        String fullUserName = userName + "@" + serverName; //create full username@server here for ease of reading

        return dbPrefix + dbUrl
                + ";user=" + fullUserName
                + ";password=" + passWord
                + ";database=" + databaseName
                + ";databaseName=" + databaseName
                + ";encrypt=" + useEncryption
                + ";trustServerCertificate=" + trustCert
                + ";hostNameInCertificate=" + hostCertName
                + ";loginTimeout=" + loginTimeout
                + ";";

    }

    /**
     * Get a connection to the database
     * @return Connection instance
     */
    public static Connection getConnection()
    {
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

        }
        catch(Exception e)
        {
            Log.e("DatabaseConnect:getConnection", "Exception Thrown", e);
        }

        return c;
    }

    /**
     * Returns the resultset of a query
     * @deprecated
     * This is not an acceptable method for getting query because ResultSet is closed before able to be accessed
     * Manually query the database and access resultset
     *
     * @param query sql query
     * @return ResultSet from database
     */
    public static ResultSet queryDatabase(String query)
    {
        if(dbConnection == null)
            dbConnection = getConnection(); //create a new connection if not already created

        ResultSet requestedResults = null;

        try
        {
            Statement stmt = dbConnection.createStatement();
            requestedResults = stmt.executeQuery(query);

            stmt.close();
            dbConnection.close();
            dbConnection = null;
        }
        catch(SQLException e)
        {
            Log.e("DatabaseConnect:queryDatabase", "Exception Thrown:: THIS METHOD SHOULD NOT BE USED", e);
        }

        return requestedResults;
    }

    /**
     * Update the database, ie insert
     * @param query Sql query to be applied
     */
    public static void updateDatabase(String query)
    {
        if(dbConnection == null)
            dbConnection = getConnection(); //create a new connection if there isnt one (shouldnt be one)

        ResultSet requestedResults = null;

        try
        {
            //create and execute query
            Statement stmt = dbConnection.createStatement();
            stmt.executeUpdate(query);

            //close everything up for safety
            stmt.close();
            dbConnection.close();
            dbConnection = null;
        }
        catch(SQLException e)
        {
            Log.e("DatabaseConnect:UpdateDatabase", "SQL Exception Thrown", e);
        }
    }
}


