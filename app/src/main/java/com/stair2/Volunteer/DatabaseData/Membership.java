package com.stair2.Volunteer.DatabaseData;

import androidx.annotation.Nullable;

/**
 * Represents a Users relationship to a club
 * @author Zach Taylor
 */
public class Membership
{
    public int userId;
    public int clubId;
    //public String role;

    public Membership(int user, int club/*, String r*/)
    {
        userId = user;
        clubId = club;
        //role = r;
    }

    //Have to have overridden equals so that we can use pattern matching for retrieving clubs
    @Override
    public boolean equals(@Nullable Object obj)
    {
        Membership other = (Membership)obj;
        if(userId == other.userId && clubId == other.clubId)
            return true;

        return false;
    }
}
