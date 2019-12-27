package com.stair2.Volunteer.Callback;

import com.stair2.Volunteer.DatabaseData.DatabaseState;

/**
 * Allows an Async Task to return a databasestate object via a callback method
 * @author Zach Taylor
 */
public interface AsyncDatabaseStateResponse {
     void ProcessFinish(DatabaseState state);
}
