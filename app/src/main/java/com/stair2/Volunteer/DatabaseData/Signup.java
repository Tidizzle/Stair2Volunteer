package com.stair2.Volunteer.DatabaseData;

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
}
