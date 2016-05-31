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

package net.namibsun.footkick.android.content;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import net.namibsun.footkick.android.R;
import net.namibsun.footkick.android.common.ActivityFrameWork;
import net.namibsun.footkick.android.widgets.LeagueButton;
import net.namibsun.footkick.java.scraper.Country;
import net.namibsun.footkick.java.scraper.LeagueInfo;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An activity that displays a list of leagues in a country
 */
public class CountryActivity extends ActivityFrameWork{

    /**
     * A string storing the link of this country's livescore.com address
     */
    private String countryLink;

    /**
     * Initializes the needed local variables
     */
    protected void initialize() {
        Bundle bundle = this.getIntent().getExtras();

        this.layoutFile = R.layout.activity_country;
        this.analyticsName = bundle.getString("country");
        this.screenName = bundle.getString("country");
        this.countryLink = bundle.getString("link");
    }

    /**
     * Populates the league list with entries gotten from the internet.
     * Potential network errors are handled by the activity framework class
     * @throws IOException in case a network error occurs
     */
    protected void getInternetData() throws IOException {

        //Get the league data
        ArrayList<LeagueInfo> leagues = new Country(this.screenName, this.countryLink).getLeagues();

        final RelativeLayout leagueHolder = (RelativeLayout) this.findViewById(R.id.leagueHolder);
        int lastId = R.id.leagueText;

        for (LeagueInfo league: leagues) {
            //Add the buttons

            final LeagueButton leagueButton = new LeagueButton(this, league.leagueName, league.leagueUrl);
            final RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            leagueButton.setId(View.generateViewId());
            buttonParams.addRule(RelativeLayout.BELOW, lastId);
            lastId = leagueButton.getId();

            //Add button to relative layout
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    leagueHolder.addView(leagueButton, buttonParams);
                }
            });
        }
    }

}
