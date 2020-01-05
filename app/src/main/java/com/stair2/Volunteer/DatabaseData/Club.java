package com.stair2.Volunteer.DatabaseData;

/**
 * Club holds the information pertinent to each club instance
 * @author Zach Taylor
 */
public class Club
{
    public int clubId;
    public int ownerId;
    public String clubName;
    public String clubDesc;
    public String websiteUrl;
    public int requiredHours;

    public Club(int id, int owner, String name, String desc, String site, int reqhr)
    {
        clubId = id;
        ownerId = owner;
        clubName = name;
        clubDesc = desc;
        websiteUrl = site;
        requiredHours = reqhr;
    }
}
