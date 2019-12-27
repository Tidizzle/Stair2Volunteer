package com.stair2.Volunteer.Callback;

import com.stair2.Volunteer.DatabaseData.DatabaseState;

public interface AsyncDatabaseStateResponse {
     void ProcessFinish(DatabaseState state);
}
