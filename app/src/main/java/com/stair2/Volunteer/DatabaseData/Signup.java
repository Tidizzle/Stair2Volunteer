package com.stair2.Volunteer.DatabaseData;

import androidx.annotation.Nullable;

/**
 * Signup contains relational information connecting an event and a user together
 * @author Zach Taylor
 */
public class Signup
{
    public int userId;
    public int eventId;
    public boolean confirmation;
    public int hourAmt;

    public Signup(int user, int event, int confirm, int hour)
    {
        userId = user;
        eventId = event;
        hourAmt = hour;

        if(confirm == 1)
            confirmation = true;
        else
            confirmation = false;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        Signup other = (Signup)obj;

        if(other.userId == userId && other.eventId == eventId)
            return true;

        return false;
    }
}
