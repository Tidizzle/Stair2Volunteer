package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stair2.Volunteer.Async.CreateClubTask;
import com.stair2.Volunteer.Async.UpdateClubTask;
import com.stair2.Volunteer.DatabaseData.Club;
import com.stair2.Volunteer.DatabaseData.Membership;

public class CreateClubActivity extends AppCompatActivity {

    boolean iscreating = true;
    int clubId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);

        setTitle("Create a new Club");

        if(AppState.LoggedInUser.userId != 0)
            ((LinearLayout)findViewById(R.id.createClub_AdminDebug)).setVisibility(View.GONE);

        if(getIntent().getIntExtra("clubId", 0) != 0)
        {
            iscreating = false;
            clubId = getIntent().getIntExtra("clubId", 0);
            Club target = AppState.state.getClubFromId(clubId);


            ((EditText)findViewById(R.id.createClub_Name)).setText(target.clubName);
            ((EditText)findViewById(R.id.createClub_Desc)).setText(target.clubDesc);
            ((EditText)findViewById(R.id.createClub_Website)).setText(target.websiteUrl);
            ((EditText)findViewById(R.id.createClub_RequiredHours)).setText(Integer.toString(target.requiredHours));

            setTitle("Edit " + target.clubName);

            ((Button)findViewById(R.id.createClub_ActionButton)).setText("Update");

            ((Button)findViewById(R.id.createClub_ActionButton)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateClub();
                }
            });

        }
    }

    public void updateClub()
    {
        int clubid = getIntent().getIntExtra("clubId", 0);

        Club old = AppState.state.getClubFromId(clubid);

        String clubName = ((EditText)findViewById(R.id.createClub_Name)).getText().toString();
        String clubDesc = ((EditText)findViewById(R.id.createClub_Desc)).getText().toString();
        String website = ((EditText)findViewById(R.id.createClub_Website)).getText().toString();
        int hours = Integer.parseInt(((EditText)findViewById(R.id.createClub_RequiredHours)).getText().toString());

        if(clubName.length() < 30)
        {
            if(clubDesc.length() < 255)
            {
                if(website.length() < 30)
                {
                    if(hours >= 0)
                    {
                        int ownerid = AppState.LoggedInUser.userId;


                        Club updatedClub = new Club(old.clubId, old.ownerId, clubName, clubDesc, website, hours);
                        AppState.state.clubs.remove(old);
                        AppState.state.clubs.add(updatedClub);


                        UpdateClubTask task = new UpdateClubTask();
                        task.execute(updatedClub);
                        Toast.makeText(this, "Updating Club", Toast.LENGTH_LONG).show();

                        Intent clubDetailIntent = new Intent(this, ClubDetailActivity.class);
                        clubDetailIntent.putExtra("clubId", old.clubId);
                        clubDetailIntent.putExtra("detailType", 1); //open detail as manage
                        startActivity(clubDetailIntent);
                        finish();


                    }
                    else
                        Toast.makeText(this, "Hours must be greater than 0!", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(this, "Website can only be 30 characters!", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this, "Description can only be 250 characters!", Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(this, "Club name can only be 30 characters!", Toast.LENGTH_LONG).show();



    }

    public void createClick(View view)
    {
        String clubName = ((EditText)findViewById(R.id.createClub_Name)).getText().toString();
        String clubDesc = ((EditText)findViewById(R.id.createClub_Desc)).getText().toString();
        String website = ((EditText)findViewById(R.id.createClub_Website)).getText().toString();
        int hours = Integer.parseInt(((EditText)findViewById(R.id.createClub_RequiredHours)).getText().toString());
        String owneridstring = ((EditText)findViewById(R.id.createClub_OwnerIdOverride)).getText().toString();

        if(clubName.length() < 30)
        {
            if(clubDesc.length() < 255)
            {
                if(website.length() < 30)
                {
                    if(hours >= 0)
                    {
                        int ownerid = AppState.LoggedInUser.userId;

                        if(!owneridstring.equals(""))
                            ownerid = Integer.parseInt(owneridstring);

                        Club nClub = new Club(AppState.genNewGUID(), ownerid, clubName, clubDesc, website, hours);

                        AppState.state.clubs.add(nClub);
                        AppState.state.memberships.add(new Membership(nClub.clubId, nClub.ownerId));

                        CreateClubTask task = new CreateClubTask();
                        task.execute(nClub);
                        Toast.makeText(this, "Creating new Club", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(this, ClubActivity.class);
                        startActivity(i);
                        finish(); //close current activity

                    }
                    else
                        Toast.makeText(this, "Hours must be greater than 0!", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(this, "Website can only be 30 characters!", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this, "Description can only be 250 characters!", Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(this, "Club name can only be 30 characters!", Toast.LENGTH_LONG).show();



    }

    @Override
    public void onBackPressed()
    {
        if(iscreating){
            Intent back = new Intent(this, ClubActivity.class);
            startActivity(back);
            finish();
        }
        else
        {
            Intent back = new Intent(this, ClubDetailActivity.class);
            back.putExtra("clubId", clubId);
            back.putExtra("detailType", 1);
            startActivity(back);
            finish();
        }
    }
}
