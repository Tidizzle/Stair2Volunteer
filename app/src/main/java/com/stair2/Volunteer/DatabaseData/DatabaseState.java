package com.stair2.Volunteer.DatabaseData;

import java.util.ArrayList;

public class DatabaseState
{
    public ArrayList<User> users;
    public ArrayList<Club> clubs;
    public ArrayList<Event> events;
    public ArrayList<Signup> signups;

    public DatabaseState()
    {
        users = new ArrayList<User>();
        clubs = new ArrayList<Club>();
        events = new ArrayList<Event>();
        signups = new ArrayList<Signup>();
    }

    public int getIncompleteSignups(int userId)
    {
        int count = 0;

        for(int i = 0; i < signups.size(); i++)
        {
            Signup s = signups.get(i);

            if(s.userId == userId && s.confirmation == false)
            {
                count+=1;
            }
        }

        return count;
    }

    public int getCompleteSignups(int userId)
    {
        int count = 0;

        for(int i = 0; i < signups.size(); i++)
        {
            Signup s = signups.get(i);

            if(s.userId == userId && s.confirmation == true)
            {
                count+=1;
            }
        }

        return count;
    }
}
