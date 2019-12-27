package com.stair2.Volunteer.DatabaseData;

import java.util.Date;

public class Event
{
    public int eventId;
    public int ownerId;
    public String title;
    public String description;
    public String location;
    public Date date;

    public Event(int id, int owner, String eventtitle, String desc, String loc, Date dt)
    {
        eventId = id;
        ownerId = owner;
        title = eventtitle;
        description = desc;
        location = loc;
        date = dt;
    }
}
