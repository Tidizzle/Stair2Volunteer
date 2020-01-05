package com.stair2.Volunteer.DatabaseData;

import java.lang.reflect.Member;
import java.util.ArrayList;

/**
 * Database state is the object responsible for holding the databases data at the point in time it was accessed
 * @author Zach Taylor
 */
public class DatabaseState
{
    public ArrayList<User> users;
    public ArrayList<Club> clubs;
    public ArrayList<Event> events;
    public ArrayList<Signup> signups;
    public ArrayList<Membership> memberships;

    public DatabaseState()
    {
        users = new ArrayList<User>();
        clubs = new ArrayList<Club>();
        events = new ArrayList<Event>();
        signups = new ArrayList<Signup>();
        memberships = new ArrayList<Membership>();
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

    public Club getClubFromId(int clubId)
    {
        for(int i = 0; i < clubs.size(); i++)
        {
            if(clubs.get(i).clubId == clubId)
                return clubs.get(i);
        }

        return null;
    }

    public ArrayList<User> getUserListFromClubId(int clubId)
    {
        ArrayList userlist = new ArrayList<User>();

        for(int i = 0; i < memberships.size(); i++)
        {
            Membership m = memberships.get(i);

            if(m.clubId == clubId)
                userlist.add(getUserFromId(m.userId));

        }

        return userlist;
    }

    public User getUserFromId(int userId)
    {
        for(int i = 0; i < users.size(); i++)
        {
            if(users.get(i).userId == userId)
                return users.get(i);
        }

        return null;
    }
}
