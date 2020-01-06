package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stair2.Volunteer.Async.LeaveClubTask;
import com.stair2.Volunteer.DatabaseData.Club;
import com.stair2.Volunteer.DatabaseData.Membership;
import com.stair2.Volunteer.DatabaseData.User;


import java.util.ArrayList;
import java.util.Comparator;

import android.widget.LinearLayout.LayoutParams;

public class ClubMembersActivity extends AppCompatActivity {

    int clubId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_members);

        setTitle("Club Members");

        clubId = getIntent().getIntExtra("clubId", 0);
        Club requestedClub = AppState.state.getClubFromId(clubId);

        int manageMembers = getIntent().getIntExtra("manage", 0);

        ArrayList<User> users = AppState.state.getUserListFromClubId(clubId);
        LinearLayout cont = ((LinearLayout)findViewById(R.id.clubMembers_MemberContainer));

        ((TextView)findViewById(R.id.clubMembers_ClubName)).setText(requestedClub.clubName);

        for(int i = 0; i < users.size(); i++)
        {
            if(manageMembers == 0)
            {
                TextView t = new TextView(getApplicationContext());

                LayoutParams p = new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT
                );
                p.setMarginStart(12);
                p.topMargin = 10;
                t.setLayoutParams(p);

                User target = users.get(i);
                if(target.userId == requestedClub.ownerId)
                    t.setText(target.firstName + " " + target.lastName + " - Club Owner");
                else
                    t.setText(target.firstName + " " + target.lastName + " - Member");
                t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18 );

                cont.addView(t);
            }
            else
            {
                if(users.get(i).userId != requestedClub.ownerId)
                {
                    LinearLayout horz = new LinearLayout(getApplicationContext());
                    horz.setOrientation(LinearLayout.HORIZONTAL);

                    LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                    horz.setLayoutParams(lp);

                    //text view
                    TextView t = new TextView(getApplicationContext());

                    LayoutParams p = new LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT
                    );
                    p.setMarginStart(12);
                    p.topMargin = 10;
                    t.setLayoutParams(p);

                    User target = users.get(i);
                    t.setText(target.firstName + " " + target.lastName);

                    t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18 );

                    //button
                    Button b = new Button(getApplicationContext());
                    LayoutParams bp = new LayoutParams(
                            250,
                            150
                    );
                    bp.setMarginStart(20);
                    b.setLayoutParams(bp);
                    b.setText("Remove");
                    b.setAllCaps(false);
                    b.setTag(target.userId);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeUser(v);
                        }
                    });

                    horz.addView(t);
                    horz.addView(b);
                    cont.addView(horz);

                }

            }

        }

    }

    public void removeUser(View v)
    {
        int userid = Integer.parseInt(v.getTag().toString());
        Membership t = new Membership(userid, clubId);

        AppState.state.memberships.remove(t);

        LeaveClubTask leavetask = new LeaveClubTask();
        leavetask.execute(t);

        Intent restart = getIntent();
        finish();
        startActivity(restart);
    }
}
