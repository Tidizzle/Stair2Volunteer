package com.stair2.Volunteer.DatabaseData;

import android.widget.ArrayAdapter;

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
    public ArrayList<Endorsement> endorsements;

    public DatabaseState()
    {
        users = new ArrayList<User>();
        clubs = new ArrayList<Club>();
        events = new ArrayList<Event>();
        signups = new ArrayList<Signup>();
        memberships = new ArrayList<Membership>();
        endorsements = new ArrayList<Endorsement>();
    }

    /**
     * Returns the number of not-yet-finished events
     * @param userId id of user to search
     * @return number of incomplete events
     */
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

    /**
     * Returns the number of finished events
     * @param userId id of user to search
     * @return number of completed events
     */
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

    public int getJoinedClubCount(int userId)
    {
        int count = 0;

        for(int i = 0;i < memberships.size(); i++)
        {
            Membership m = memberships.get(i);

            if(m.userId == userId && getClubFromId(m.clubId).ownerId != userId)
                count +=1;
        }

        return count;
    }

    public int getOwnedClubCount(int userId)
    {
        int count = 0;

        for(int i = 0; i < clubs.size(); i++)
        {
            Club c = clubs.get(i);

            if(c.ownerId == userId)
                count += 1;
        }

        return count;
    }

    /**
     * Get the club object by the clubId
     * @param clubId ClubId of object
     * @return Club Object associated with clubId
     */
    public Club getClubFromId(int clubId)
    {
        for(int i = 0; i < clubs.size(); i++)
        {
            if(clubs.get(i).clubId == clubId)
                return clubs.get(i);
        }

        return null;
    }

    /**
     * Get the list of users that are members of a club
     * @param clubId Clubid of club to retrieve from
     * @return List of users in specified club
     */
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

    /**
     * Get the User object associated with a userId
     * @param userId Userid to search for
     * @return User object
     */
    public User getUserFromId(int userId)
    {
        for(int i = 0; i < users.size(); i++)
        {
            if(users.get(i).userId == userId)
                return users.get(i);
        }

        return null;
    }

    /**
     * Get the list of clubs that a user is a member of
     * @param userid Userid of person to search
     * @return List of users clubs
     */
    public ArrayList<Club> getClubs(int userid)
    {
        //collect all of the users memberships
        ArrayList<Membership> usersMemberships = new ArrayList<Membership>();
        for(int i = 0; i < memberships.size(); i++)
        {
            if(memberships.get(i).userId == userid)
                usersMemberships.add(memberships.get(i));
        }

        //collect all of the clubs that match the pattern
        ArrayList<Club> usersClubs = new ArrayList<>();
        for(int i = 0; i < clubs.size();i++)
        {
            Membership pattern = new Membership(userid,clubs.get(i).clubId);

            if(usersMemberships.contains(pattern))
                usersClubs.add(clubs.get(i));
        }

        return usersClubs;
    }

    /**
     * Get the list of clubs a user is not a member of
     * @param userid id of user to search
     * @return list of clubs the specified user is not associated with
     */
    public ArrayList<Club> getClubsNotJoined(int userid)
    {
        //collect all of the users memberships
        ArrayList<Membership> usersMemberships = new ArrayList<Membership>();
        for(int i = 0; i < memberships.size(); i++)
        {
            if(memberships.get(i).userId == userid)
                usersMemberships.add(memberships.get(i));
        }

        //collect all of the clubs that match the pattern
        ArrayList<Club> inverseUsersClubs = new ArrayList<>();
        for(int i = 0; i < clubs.size();i++)
        {
            Membership pattern = new Membership(userid,clubs.get(i).clubId);

            if(!usersMemberships.contains(pattern))
                inverseUsersClubs.add(clubs.get(i));
        }

        return inverseUsersClubs;
    }

    /**
     * Get the list of clubs a user is owner of
     * @param userid id of user to search
     * @return List of the users owned clubs
     *
     */
    @Deprecated
    public ArrayList<Club> getOwnedClubs(int userid)
    {
        //collect all of the users memberships
        ArrayList<Membership> usersMemberships = new ArrayList<Membership>();
        for(int i = 0; i < memberships.size(); i++)
        {
            if(memberships.get(i).userId == userid)
                usersMemberships.add(memberships.get(i));
        }

        //collect all of the clubs that match the pattern
        ArrayList<Club> usersClubs = new ArrayList<>();
        for(int i = 0; i < clubs.size();i++)
        {
            Membership pattern = new Membership(userid,clubs.get(i).clubId);

            if(usersMemberships.contains(pattern))
                usersClubs.add(clubs.get(i));
        }

        return usersClubs;
    }

    public ArrayList<Event> getEvents(int userId)
    {
        ArrayList<Event> usersEvents = new ArrayList<>();
        for(int i = 0; i < events.size(); i++)
        {
            Event e = events.get(i);
            if(e.ownerId == userId)
                usersEvents.add(e);
        }

        return usersEvents;
    }

    public Event getEventFromId(int eventId)
    {
        for(int i = 0; i < events.size(); i++)
        {
            Event e = events.get(i);
            if(e.eventId == eventId)
                return e;
        }

        return null;
    }

    public Club getSponsorFromEventId(int eventId)
    {
        for(int i = 0; i < endorsements.size(); i++)
        {
            Endorsement e = endorsements.get(i);
            if(e.eventId == eventId)
                return getClubFromId(e.clubId);
        }

        return null;
    }

    public ArrayList<User> getUsersFromEventId(int eventId)
    {
        ArrayList<Integer> userIds = new ArrayList<Integer>();
        ArrayList<User> retList = new ArrayList<>();

        for(int i = 0; i < signups.size(); i++)
        {
            Signup s = signups.get(i);

            if(s.eventId == eventId)
                userIds.add(s.userId);
        }

        for(int i = 0; i < users.size(); i++)
        {
            User u = users.get(i);
            if(userIds.contains(u.userId))
                retList.add(u);
        }

        return retList;
    }

    public ArrayList<Event> getSponsoredEventsFromClubId(int clubId)
    {
        ArrayList<Integer> end = new ArrayList<>();
        ArrayList<Event> retList = new ArrayList<>();

        for(int i = 0; i < endorsements.size(); i++)
        {
            Endorsement e = endorsements.get(i);

            if(e.clubId == clubId)
                end.add(e.eventId);
        }

        for(int i = 0; i < events.size(); i++)
        {
            Event e = events.get(i);

            if(end.contains(e.eventId))
                retList.add(e);
        }

        return retList;
    }

    public ArrayList<Signup> getSignupsFromUserId(int userId)
    {
        ArrayList<Signup> retlist = new ArrayList<>();

        for(int i = 0; i < signups.size(); i++)
        {
            Signup s = signups.get(i);

            if(s.userId == userId)
                retlist.add(s);
        }

        return retlist;
    }

    public Signup getSignupFromIds(int userId, int eventId)
    {
        for(int i = 0; i < signups.size(); i++)
        {
            Signup s = signups.get(i);

            if(s.userId == userId && s.eventId == eventId)
                return s;
        }

        return null;
    }

}
