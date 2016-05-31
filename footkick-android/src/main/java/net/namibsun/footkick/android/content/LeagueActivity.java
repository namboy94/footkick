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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import net.namibsun.footkick.android.R;
import net.namibsun.footkick.java.scraper.Match;
import net.namibsun.footkick.java.scraper.Team;
import net.namibsun.footkick.java.structures.League;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An activity that displays a league table and a matchday
 */
public class LeagueActivity extends AppCompatActivity{

    private ViewSwitcher viewSwitcher;
    private GestureDetector gestureDetector;

    private Animation slide_in_left, slide_out_right;
    private Animation slide_in_right, slide_out_left;

    private int[] tableColours = new int[] {
            R.color.colorEvenRow, R.color.colorOddRow
    };

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

        viewSwitcher = (ViewSwitcher) this.findViewById(R.id.leagueViewSwitcher);
        this.slide_in_left = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        this.slide_in_right = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        this.slide_out_left = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        this.slide_out_right = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);

        //Populate the table layouts
        Bundle bundle = this.getIntent().getExtras();
        new TablePopulator().execute(bundle.getString("league"), bundle.getString("link"));

        try {
            //noinspection ConstantConditions
            this.getSupportActionBar().setTitle(bundle.getString("league"));
        } catch (NullPointerException e) {
            //noinspection ConstantConditions
            this.getActionBar().setTitle(bundle.getString("league"));
        }

        SwipeDetector swipeDetector = new SwipeDetector();
        this.gestureDetector = new GestureDetector(this, swipeDetector);
    }

    /**
     * Calls the gesture detector to swipe to the next or previous view
     * @param event hte motion event that triggered this method call
     * @return if the event was accepted or not, I guess?
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return this.gestureDetector.onTouchEvent(ev);
    }

    /**
     * This method populates the league table and matchday Views using the specified country and league
     * @param link the link to the league's website
     */
    private void populateData(String link) {

        try {
            League leagueData = new League(link);
            ArrayList<Team> teams = leagueData.getTeams();
            ArrayList<Match> matches = leagueData.getMatches();

            this.fillLeagueTable(teams);
            this.fillMatchday(matches);

        } catch (IOException e) {
            //Notifiers.showConnectionErrorDialog(this);
            //Handle Dropped Connections
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            this.populateData(link);
        }
    }

    /**
     * Fills the league table with the provided team objects
     * @param teams the teams to be displayed
     */
    private void fillLeagueTable(ArrayList<Team> teams) {

        // Switch to matchday if no league table available
        if (teams.size() == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LeagueActivity.this.viewSwitcher.showNext();
                }
            });
            return;
        }

        final TableLayout table = (TableLayout) this.findViewById(R.id.leagueTableTable);

        int position = 1;

        for (Team team : teams) {
            final TableRow teamRow = new TableRow(this);
            teamRow.setBackgroundResource(this.tableColours[position % 2]);

            String[] data = {
                    "" + position, team.teamName, team.matches, team.wins, team.draws, team.losses, team.goalsFor,
                    team.goalsAgainst, team.goalDifference, team.points
            };

            for (String dataElement : data) {
                TextView dataText = new TextView(this);
                dataText.setText(dataElement);
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

        int row = 1;
        for (Match match : matches) {
            final TableRow matchRow = new TableRow(this);
            matchRow.setBackgroundResource(this.tableColours[row % 2]);
            row++;

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
            LeagueActivity.this.populateData(params[1]);
            return null;
        }
    }

    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Swipe left (next)

            if (Math.abs(velocityX) > 3000.0) {
                if (e1.getX() > e2.getX()) {
                    LeagueActivity.this.viewSwitcher.setInAnimation(LeagueActivity.this.slide_in_right);
                    LeagueActivity.this.viewSwitcher.setOutAnimation(LeagueActivity.this.slide_out_left);
                }
                else {
                    LeagueActivity.this.viewSwitcher.setInAnimation(LeagueActivity.this.slide_in_left);
                    LeagueActivity.this.viewSwitcher.setOutAnimation(LeagueActivity.this.slide_out_right);
                }
                LeagueActivity.this.viewSwitcher.showNext();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

    }
}
