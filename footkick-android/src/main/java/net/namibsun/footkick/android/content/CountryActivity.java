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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import net.namibsun.footkick.android.R;
import net.namibsun.footkick.android.common.Notifiers;
import net.namibsun.footkick.android.widgets.LeagueButton;
import net.namibsun.footkick.java.scraper.Country;
import net.namibsun.footkick.java.scraper.LeagueInfo;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An activity that displays a list of leagues in a country
 */
public class CountryActivity extends AppCompatActivity{

    private boolean connectionLost = false;

    /**
     * Method run on creation of the Activity, it inflates the activity_country.xml
     * layout file and then adds all league for the selected country to a list
     *
     * @param savedInstanceState the current instance state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize the Activity and load the activity_country.xml layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        //Populate the table layouts
        Bundle bundle = this.getIntent().getExtras();
        new LeagueLister().execute(bundle.getString("country"), bundle.getString("link"));

        try {
            //noinspection ConstantConditions
            this.getSupportActionBar().setTitle(bundle.getString("country"));
        } catch (NullPointerException e) {
            //noinspection ConstantConditions
            this.getActionBar().setTitle(bundle.getString("country"));
        }

    }

    /**
     * This method lists all countries as pressable buttons
     * @param country the country to populate the views with its leagues with
     * @param link the link to the country URL
     */
    private void populateData(String country, String link) {

        //Get the league data
        ArrayList<LeagueInfo> leagues;
        try {
            leagues = new Country(country, link).getLeagues();
            this.connectionLost = false;
        } catch (IOException e) {
            if (!this.connectionLost) {
                this.connectionLost = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Notifiers.showConnectionErrorDialog(CountryActivity.this);
                    }
                });
            }

            //Handle Dropped Connections
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            this.populateData(country, link);
            return;
        }

        //Initialize the ScollView and RelativeLayout
        final RelativeLayout leagueHolder = (RelativeLayout) this.findViewById(R.id.leagueHolder);

        int lastId = R.id.leagueText;
        for (LeagueInfo league: leagues) {
            //Add the buttons

            final LeagueButton leagueButton = new LeagueButton(this, league.leagueName, league.leagueUrl);
            leagueButton.setId(View.generateViewId());
            final RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            buttonParams.addRule(RelativeLayout.BELOW, lastId);
            lastId = leagueButton.getId();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    leagueHolder.addView(leagueButton, buttonParams);
                }
            });
        }
    }

    /**
     * An Async task that loads the country's league information in the background
     */
    private class LeagueLister extends AsyncTask<String, Void, Void> {

        /**
         * Runs in the background
         * @param params the country name and link
         * @return Void
         */
        @Override
        protected Void doInBackground(String... params) {
            CountryActivity.this.populateData(params[0], params[1]);
            return null;
        }
    }
}
