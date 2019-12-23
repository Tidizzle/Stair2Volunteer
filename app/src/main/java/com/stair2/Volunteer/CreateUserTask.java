package com.stair2.Volunteer;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import java.sql.ResultSet;
import java.util.UUID;

public class CreateUserTask extends AsyncTask<String, Void, User>
{
    public AsyncUserResponse delegate = null;

    private final String tableName = "users";
    private final String userId = "userId";
    private final String firstName = "firstName";
    private final String lastName = "lastName";
    private final String userName = "userName";
    private final String passHash = "passwordHash";


    @Override
    protected User doInBackground(String... strings)
    {
        //get the strings from the execution
        String fname = strings[0];
        String lname = strings[1];
        String uname = strings[2];
        String phash = strings[3];


        try{
            int guid = User.genNewGUID(); //create a new GUID to be assigned to the user

            String query = String.format(
                    "INSERT INTO %1$s (%2$s,%3$s,%4$s,%5$s,%6$s) VALUES ('%7$s','%8$s','%9$s','%10$s','%11$s');",
                    tableName, userId, firstName, lastName, userName, passHash, guid, fname, lname, uname, phash);

            //send the query and create new user
            DatabaseConnect.updateDatabase(query);
            return new User(guid, fname, lname, uname);

        }
        catch(Exception e)
        {
            Log.e("CreateUserTask:doInBackground", "Exception Thrown", e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(User user)
    {
        //send the result back to the caller
        delegate.processFinish(user);
    }
}
