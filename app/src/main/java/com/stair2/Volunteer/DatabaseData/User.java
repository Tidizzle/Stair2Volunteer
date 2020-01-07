package com.stair2.Volunteer.DatabaseData;


/**
 * User holds pertinent user information
 * @author Zach Taylor
 */
public class User
{
    public String firstName;
    public String lastName;
    public String userName;
    public int userId;


    public User(int uid, String first, String last, String uname)
    {
        firstName = first;
        lastName = last;
        userName = uname;
        userId = uid;
    }


}
