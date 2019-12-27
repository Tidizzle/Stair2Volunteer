package com.stair2.Volunteer.Callback;

/**
 * Allows for an async task to return a boolean via a callback method
 */
public interface AsyncBooleanResponse
{
    void processFinish(boolean output);
}
