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


    public User(int uid, String first, String last, String uname)
    {
        firstName = first;
        lastName = last;
        userName = uname;
        userId = uid;
    }


}
