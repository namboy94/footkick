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
import net.namibsun.footkick.java.scraper.Match;
import net.namibsun.footkick.java.scraper.Team;
import net.namibsun.footkick.java.structures.League;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An activity that displays a league table and a matchday
 */
public class LeagueActivity extends AppCompatActivity{

    /**
     * Method run on creation of the Activity
     *
     * @param savedInstanceState the current instance state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize the Activity and load the activity_league.xml layout file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);

        //Populate the table layouts
        final Bundle bundle = this.getIntent().getExtras();
        new TablePopulator().execute(bundle.getString("league"), bundle.getString("link"));

        //Initialize the switch button
        final Button switchButton = (Button) this.findViewById(R.id.switchButton);
        switchButton.setText(R.string.matchday);
        //Define the on click listener
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switch between tables
                String buttonText = switchButton.getText().toString();
                if (buttonText.equals("Matchday")) {
                    switchButton.setText(R.string.leaguetable);
                } else {
                    switchButton.setText(R.string.matchday);
                }
                ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.leagueViewSwitcher);
                switcher.showNext();
            }
        });
    }

    /**
     * This method populates the league table and matchday Views using the specified country and league
     * @param league the league to populate the views with
     * @param link the link to the league's website
     */
    private void populateData(String league, String link) {

        try {
            League leagueData = new League(link);
            ArrayList<Team> teams = leagueData.getTeams();
            ArrayList<Match> matches = leagueData.getMatches();

            this.fillLeagueTable(teams);
            this.fillMatchday(matches);

        } catch (IOException e) {
            Notifiers.showConnectionErrorDialog(this);
        }

    }

    /**
     * Fills the league table with the provided team objects
     * @param teams the teams to be displayed
     */
    private void fillLeagueTable(ArrayList<Team> teams) {

        final TableLayout table = (TableLayout) this.findViewById(R.id.leagueTableTable);

        int position = 1;
        for (Team team : teams) {
            final TableRow teamRow = new TableRow(this);

            String[] data = {
                    "" + position, team.teamName, team.matches, team.wins, team.draws, team.losses, team.goalsFor,
                    team.goalsAgainst, team.goalDifference, team.points
            };

            for (String dataElement : data) {
                TextView dataText = new TextView(this);
                dataText.setText(dataElement);
                dataText.setPadding(5,0,5,0);
                teamRow.addView(dataText);
            }
            position++;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    table.addView(teamRow);
                }
            });
        }
    }

    /**
     * Filles the Matchday table with the parsed matches
     * @param matches the matches to be displayed
     */
    private void fillMatchday(ArrayList<Match> matches) {

        final TableLayout matchDayTable = (TableLayout) this.findViewById(R.id.matchDayTable);

        for (Match match : matches) {
            final TableRow matchRow = new TableRow(this);

            String[] matchData = {
                    match.time, match.homeTeam, match.score, match.awayTeam
            };

            for (String dataElement: matchData) {
                TextView dataText = new TextView(this);
                dataText.setText(dataElement);
                matchRow.addView(dataText);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    matchDayTable.addView(matchRow);
                }
            });
        }
    }

    /**
     * An Async task that loads the league information in the background
     */
    private class TablePopulator extends AsyncTask<String, Void, Void> {

        /**
         * Runs in the background
         * @param params the country and league identifiers
         * @return Void
         */
        @Override
        protected Void doInBackground(String... params) {
            LeagueActivity.this.populateData(params[0], params[1]);
            return null;
        }
    }
}
