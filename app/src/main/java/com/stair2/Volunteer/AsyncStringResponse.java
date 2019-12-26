package com.stair2.Volunteer;

/**
 * Allows for an async task to return a string via a callback method.
 */
public interface AsyncStringResponse
{
    void processFinish(String output);
}
