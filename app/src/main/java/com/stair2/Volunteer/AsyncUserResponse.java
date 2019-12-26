package com.stair2.Volunteer;

/**
 * Allows for an async task to return a user object via a callback method.
 */
public interface AsyncUserResponse
{
    void processFinish(User u);
}
