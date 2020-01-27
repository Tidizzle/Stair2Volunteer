package com.stair2.Volunteer.DatabaseData;

import java.sql.Time;
import java.util.Date;

/**
 * Object to hold pertinent event information
 * @author Zach Taylor
 */
public class Event implements Comparable<Event>
{
    public int eventId;
    public int ownerId;
    public String title;
    public String description;
    public String location;
    public Date date;
    public Time time;
    public int length;

    public Event(int id, int owner, String eventtitle, String desc, String loc, Date dt, Time tm, int len)
    {
        eventId = id;
        ownerId = owner;
        title = eventtitle;
        description = desc;
        location = loc;
        date = dt;
        time = tm;
        length = len;
    }

    public String parseDate()
    {
        String raw = date.toString();
        String year = raw.substring(0,4);
        String month = raw.substring(5,7);
        String day = raw.substring(8);

        return month + "/" + day + "/" + year;
    }

    public String parseTime()
    {
        String raw = time.toString();
        int hour = Integer.parseInt(raw.substring(0,2));
        String minute = raw.substring(3,5);

        if(hour > 12)
            return (hour-12) + ":" + minute + "pm";
        else if (hour == 12)
            return hour + ":" + minute + "pm";
        else
            return hour + ":" + minute + "am";

    }

    @Override
    public int compareTo(Event o) {

        if(date.before(o.date))
            return -1;
        else if (date.equals(o.date))
            return 0;
        else
            return 1;
    }
}
