package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("Home");

        //refresh appstate in case new user was created
        AppState state = new AppState();
        state.LoadAppState();

        AppState.InterpretState();

        ((TextView)findViewById(R.id.home_CompleteEventsText)).setText(AppState.completesignups + "  Completed");
        ((TextView)findViewById(R.id.home_UpcomingEventsText)).setText(AppState.incompletesignups + "  Upcoming");

    }

}
