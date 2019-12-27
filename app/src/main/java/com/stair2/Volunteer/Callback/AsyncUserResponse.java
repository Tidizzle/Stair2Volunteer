package com.stair2.Volunteer.Callback;

import com.stair2.Volunteer.DatabaseData.User;

/**
 * Allows for an async task to return a user object via a callback method.
 * @author Zach Taylor
 */
public interface AsyncUserResponse
{
    void processFinish(User u);
}
