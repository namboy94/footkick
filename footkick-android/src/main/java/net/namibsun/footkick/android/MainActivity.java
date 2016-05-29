/*
Copyright 2016 Hermann Krumrey

This file is part of footkick.

    footkick is a program that lets a user view football (soccer)
    match standings and league tables

    footkick is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    footkick is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with footkick. If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.footkick.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import net.namibsun.footkick.android.content.LeagueActivity;
import net.namibsun.footkick.android.widgets.CountryLeagueButton;
import net.namibsun.footkick.java.scraper.LeagueLister;

import java.io.BufferedOutputStream;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //REMOVE THIS
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //REMOVE THIS


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String[] leagues = LeagueLister.getLeagues();
        ScrollView scroller = (ScrollView) this.findViewById(R.id.mainScroller);

        RelativeLayout leagueHolder = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        leagueHolder.setLayoutParams(params);

        Button lastButton = null;

        for (String league : leagues) {
            final String country = league.split(" ")[0];
            final String leagueName = league.split(" ")[1];
            CountryLeagueButton leagueButton = new CountryLeagueButton(this, country, leagueName);

            final MainActivity activity = this;

            leagueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent leagueActivity = new Intent(activity, LeagueActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("country", country);
                    bundle.putString("league", leagueName);
                    leagueActivity.putExtras(bundle);
                    startActivity(leagueActivity);
                }
            });


            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            leagueButton.setId(View.generateViewId());
            if (lastButton != null) {
                Log.e("Here",  "hee");
                Log.e("LAST ID", "" + lastButton.getId());
                buttonParams.addRule(RelativeLayout.BELOW, lastButton.getId());
            }
            leagueHolder.addView(leagueButton, buttonParams);
            lastButton = leagueButton;
        }

        scroller.addView(leagueHolder);


        /*
        Intent leagueActivity = new Intent(this, LeagueActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("country", "germany");
        bundle.putString("league", "bundesliga");
        leagueActivity.putExtras(bundle);
        startActivity(leagueActivity);
        */

    }
}
