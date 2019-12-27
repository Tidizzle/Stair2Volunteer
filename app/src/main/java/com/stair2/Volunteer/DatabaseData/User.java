package com.stair2.Volunteer.DatabaseData;

import java.util.UUID;

/**
 * User holds pertinent user information including a guidGeneration method
 * @author Zach Taylor
 */
public class User
{
    public String firstName;
    public String lastName;
    public String userName;
    public int userId;

    private static final int GUID_LENGTH = 6;

    public User(int uid, String first, String last, String uname)
    {
        firstName = first;
        lastName = last;
        userName = uname;
        userId = uid;
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
}
