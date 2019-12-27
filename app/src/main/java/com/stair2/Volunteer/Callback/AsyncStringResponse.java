package com.stair2.Volunteer.Callback;

/**
 * Allows for an async task to return a string via a callback method.
 * @author Zach Taylor
 */
public interface AsyncStringResponse
{
    void processFinish(String output);
}
