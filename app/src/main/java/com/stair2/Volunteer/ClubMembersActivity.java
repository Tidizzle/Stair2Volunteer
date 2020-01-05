package com.stair2.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stair2.Volunteer.DatabaseData.Club;
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

        ArrayList<User> users = AppState.state.getUserListFromClubId(clubId);
        LinearLayout cont = ((LinearLayout)findViewById(R.id.clubMembers_MemberContainer));

        ((TextView)findViewById(R.id.clubMembers_ClubName)).setText(requestedClub.clubName);

        for(int i = 0; i < users.size(); i++)
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
    }
}
