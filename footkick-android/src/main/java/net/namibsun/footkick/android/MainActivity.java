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

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import net.namibsun.footkick.android.widgets.CountryLeagueButton;
import net.namibsun.footkick.java.scraper.LeagueLister;


/**
 * The Main Activity class.
 * It displays a list of country/league combinations as buttons to load the standings
 * for that country/league
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Creates a new ScrollView containing a RelativeLayout that shows the country/league buttons.
     * The method inflates the activity_main.xml layout file
     * @param savedInstanceState - The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Creates the new Activity and sets the content view to that of activity_main.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get an array of league identifiers
        String[] leagues = LeagueLister.getLeagues();

        //Initialize the ScollView and RelativeLayout
        ScrollView scroller = (ScrollView) this.findViewById(R.id.mainScroller);
        RelativeLayout leagueHolder = new RelativeLayout(this);
        leagueHolder.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                     ViewGroup.LayoutParams.MATCH_PARENT));

        //Add the buttons
        Button lastButton = null;
        for (String league : leagues) {

            String country = league.split(" ")[0];
            String leagueName = league.split(" ")[1];
            final MainActivity activity = this;

            CountryLeagueButton leagueButton = new CountryLeagueButton(this, country, leagueName);
            leagueButton.setId(View.generateViewId());
            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            if (lastButton != null) {
                Log.e("Here",  "hee");
                Log.e("LAST ID", "" + lastButton.getId());
                buttonParams.addRule(RelativeLayout.BELOW, lastButton.getId());
            }

            leagueHolder.addView(leagueButton, buttonParams);
            lastButton = leagueButton;
        }

        //Add the buttons to the scroller
        scroller.addView(leagueHolder);
    }
}
