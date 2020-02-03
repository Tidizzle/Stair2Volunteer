package com.stair2.Volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

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
        ((TextView)findViewById(R.id.home_JoinedClubsText)).setText(AppState.joinedclubs + "  Joined Clubs");
        ((TextView)findViewById(R.id.home_OwnedClubText)).setText(AppState.ownedclubs + "  Owned Clubs");

        ((Button)findViewById(R.id.home_userLabel)).setText("      " + AppState.LoggedInUser.firstName + " " + AppState.LoggedInUser.lastName);

    }

    //add the menu item to the activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.homemenu_opendrawer:
                ((DrawerLayout)findViewById(R.id.home_drawer)).openDrawer(GravityCompat.END);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void feedClick(View view)
    {
        Intent feed = new Intent(this, FeedActivity.class);
        startActivity(feed);
    }

    public void eventsClick(View view)
    {
        Intent mngevents = new Intent(this, EventActivity.class);
        startActivity(mngevents);
        ((DrawerLayout)findViewById(R.id.home_drawer)).closeDrawer(GravityCompat.END);

    }

    public void infoClick(View view)
    {
        Intent info = new Intent(this, AboutActivity.class);
        startActivity(info);
        ((DrawerLayout)findViewById(R.id.home_drawer)).closeDrawer(GravityCompat.END);

    }

    public void viewAllClubs(View view)
    {
        Intent clubIntent = new Intent(this, ClubActivity.class);
        startActivity(clubIntent);
    }

    public void viewAllSignups(View view)
    {

    }

    public void yourHoursClick(View view)
    {
        Intent hoursprogress = new Intent(this, HoursProgressActivity.class);
        startActivity(hoursprogress);
    }

    public void logoutClick(View view)
    {

        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

    }

    private void logout()
    {
        Intent logout = new Intent(this, LoginActivity.class);
        logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logout);
    }

}
