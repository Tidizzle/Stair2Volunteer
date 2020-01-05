package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stair2.Volunteer.Async.LeaveClubTask;
import com.stair2.Volunteer.DatabaseData.Club;
import com.stair2.Volunteer.DatabaseData.Membership;

public class ClubDetailActivity extends AppCompatActivity {

    private int clubId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail);

        setTitle("Club Detail");

        clubId = getIntent().getIntExtra("clubId", 0);
        Club requestedClub = AppState.state.getClubFromId(clubId);

        ((TextView)findViewById(R.id.clubDetail_ClubTitle)).setText(requestedClub.clubName);
        ((TextView)findViewById(R.id.clubDetail_Description)).setText(requestedClub.clubDesc);
        ((TextView)findViewById(R.id.clubDetail_Website)).setText(requestedClub.websiteUrl);
        ((TextView)findViewById(R.id.clubDetail_ReqHours)).setText("Required Hours: " + Integer.toString(requestedClub.requiredHours));
    }

    public void showMemebers(View view)
    {
        Intent memberDetail = new Intent(this, ClubMembersActivity.class);
        memberDetail.putExtra("clubId", clubId);
        startActivity(memberDetail);
    }

    public void showSponsored(View view)
    {
        //todo: send to sponsored feed when implemented
    }

    public void leaveClub(View view)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Confirm");
        b.setMessage("Are you sure you want to leave?");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //clicked yes
                Membership target = new Membership(AppState.LoggedInUser.userId, clubId);
                LeaveClubTask t = new LeaveClubTask();
                t.execute(target);
            }
        });
        b.setNegativeButton("No", null);
        b.show();
    }
}
