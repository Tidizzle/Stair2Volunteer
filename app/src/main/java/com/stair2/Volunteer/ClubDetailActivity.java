package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stair2.Volunteer.Async.CreateClubTask;
import com.stair2.Volunteer.Async.DeleteClubTask;
import com.stair2.Volunteer.Async.LeaveClubTask;
import com.stair2.Volunteer.DatabaseData.Club;
import com.stair2.Volunteer.DatabaseData.Membership;

public class ClubDetailActivity extends AppCompatActivity {

    private int clubId;
    private int DetailType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail);

        setTitle("Club Detail");

        //retrieve the club id from the sender
        clubId = getIntent().getIntExtra("clubId", 0);
        Club requestedClub = AppState.state.getClubFromId(clubId);

        //get the detailtype to determine how the screen should be set up
        DetailType = getIntent().getIntExtra("detailType", 0);


        ((TextView)findViewById(R.id.clubDetail_ClubTitle)).setText(requestedClub.clubName);
        ((TextView)findViewById(R.id.clubDetail_Description)).setText(requestedClub.clubDesc);
        ((TextView)findViewById(R.id.clubDetail_ReqHours)).setText("Required Hours: " + Integer.toString(requestedClub.requiredHours));

        if(requestedClub.websiteUrl == null)
            ((TextView)findViewById(R.id.clubDetail_Website)).setText("No Website listed");
        else
            ((TextView)findViewById(R.id.clubDetail_Website)).setText(requestedClub.websiteUrl);

        //change the visible buttons based on whos looking at the screen
        //0 == detail view, 1 == manage view
        if(DetailType == 0)
        {
            findViewById(R.id.clubDetail_ManageMembers).setVisibility(View.GONE);
            findViewById(R.id.clubDetail_EditDetails).setVisibility(View.GONE);
            findViewById(R.id.clubDetail_DeleteClub).setVisibility(View.GONE);
        }
        else
        {
            findViewById(R.id.clubDetail_Members).setVisibility(View.GONE);
            findViewById(R.id.clubDetail_LeaveClub).setVisibility(View.GONE);
            findViewById(R.id.clubDetail_SponsoredEvents).setVisibility(View.GONE);
        }
    }

    //move to the members activity
    public void showMemebers(View view)
    {
        Intent memberDetail = new Intent(this, ClubMembersActivity.class);
        memberDetail.putExtra("clubId", clubId);
        startActivity(memberDetail);
    }

    public void showSponsored(View view)
    {
        Intent filteredFeed = new Intent(this, FeedActivity.class);
        filteredFeed.putExtra("clubFilter", clubId);
        startActivity(filteredFeed);
    }

    //alert the user and then move along with the leave
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

                AppState.state.memberships.remove(target);

                moveBack();
            }
        });
        b.setNegativeButton("No", null);
        b.show();
    }

    //move back to the clubactivity class and delete this from the application stack
    public void moveBack()
    {

        finish();
    }

    //move to the members activity as manage
    public void manageMembers(View view)
    {
        Intent managedetail = new Intent(this, ClubMembersActivity.class);
        managedetail.putExtra("clubId", clubId);
        managedetail.putExtra("manage", 1);
        startActivity(managedetail);
    }

    //move to the create page as an edit
    public void editDetails(View view)
    {
        Intent editdetails = new Intent(this, CreateClubActivity.class);
        editdetails.putExtra("clubId", clubId);
        startActivity(editdetails);
    }

    //delete the club with a prompt
    public void deleteClub(View view)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Confirm");
        b.setMessage("Are you sure you want to delete this club?");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //clicked yes
                contDelete();
            }
        });
        b.setNegativeButton("No", null);
        b.show();
    }

    //continue with delete if the user selects it
    public void contDelete()
    {
        Club target = AppState.state.getClubFromId(clubId);
        DeleteClubTask t = new DeleteClubTask();
        t.execute(target);

        finish();
    }
}
