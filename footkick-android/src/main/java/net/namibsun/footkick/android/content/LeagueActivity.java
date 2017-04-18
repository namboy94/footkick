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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.*;
import net.namibsun.footkick.android.R;
import net.namibsun.footkick.android.common.ActivityFrameWork;
import net.namibsun.footkick.android.common.ViewSwiper;
import net.namibsun.footkick.lib.scraper.LeagueData;
import net.namibsun.footkick.lib.scraper.Match;
import net.namibsun.footkick.lib.scraper.Team;
import net.namibsun.footkick.lib.structures.League;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An activity that displays a league table and a matchday
 */
public class LeagueActivity extends ActivityFrameWork{

    /**
     * A gesture detector that detects swipes to the left and right and switches
     * between the matchday and league tables accordingly
     */
    private GestureDetector gestureDetector;

    /**
     * The URL link to the league's livescore.com site
     */
    private String leagueLink;

    /**
     * The current view type to be sent vie Analytics
     */
    private String currentView = "League Table";

    /**
     * Initializes the activity, sets the name of the activity as well as the XML layout
     * file, and initializes the swipe detector.
     * @param savedInstanceState the saved instance sent by the Android OS
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();

        this.layoutFile = R.layout.activity_league;
        this.analyticsName = bundle.getString("country") + " - " + bundle.getString("league");
        this.screenName = bundle.getString("league");
        this.leagueLink = bundle.getString("link");

        super.onCreate(bundle);

        //Create a Runnable that keeps track of which View is currently shown.
        Runnable toggleView = new Runnable() {
            @Override
            public void run() {
                if (LeagueActivity.this.currentView.equals("League Table")) {
                    LeagueActivity.this.currentView = "Matchday";
                }
                else {
                    LeagueActivity.this.currentView = "League Table";
                }
                //analyticsTracker.setScreenName(
                //        LeagueActivity.this.analyticsName + " - " + LeagueActivity.this.currentView);
                //analyticsTracker.send(new HitBuilders.ScreenViewBuilder().build());
            }
        };

        //Initialize Swipe Detector
        ViewSwitcher switcher = (ViewSwitcher) this.findViewById(R.id.leagueViewSwitcher);
        ViewSwiper swipeDetector = new ViewSwiper(this, switcher, toggleView, toggleView);
        this.gestureDetector = new GestureDetector(this, swipeDetector);
    }

    /**
     * Overrides the onTouchEvent method to integrate the swipe detector
     * @param event the motion event that triggered this method call
     * @return a boolean representing if the method triggered something???
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * Overrides the dispatchTouchEvent to enable the Swipe detector on top of a scroll view
     * @param event the motion event that triggered this method call
     * @return a boolean representing if the method triggered something???
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        return this.gestureDetector.onTouchEvent(event);
    }

    /**
     * Populates the tables of the activity with league table and matchday data.
     * @throws IOException in case the connection to livescore.com fails, this is handled
     *                     by the activity framework class
     */
    protected void getInternetData() throws IOException {

        //Get the league data and remove the progress circle
        League leagueData = new League(this.leagueLink);
        ArrayList<Team> teams = leagueData.getTeams();
        ArrayList<Match> matches = leagueData.getMatches();
        this.removeView(R.id.league_activity_progress);

        //Initialize the table header strings
        String[] leagueTableHeader = new String[] {
                this.getString(R.string.table_position), this.getString(R.string.table_team_name),
                this.getString(R.string.table_matches), this.getString(R.string.table_wins),
                this.getString(R.string.table_draws), this.getString(R.string.table_losses),
                this.getString(R.string.table_goals_for), this.getString(R.string.table_goals_agaist),
                this.getString(R.string.table_goal_difference), this.getString(R.string.table_points)
        };
        String[] matchDayHeader = new String[] {
                this.getString(R.string.matchday_time), this.getString(R.string.matchday_home_team),
                this.getString(R.string.matchday_score), this.getString(R.string.matchday_away_team)
        };

        this.fillTable(R.id.leagueTableTable, leagueTableHeader, teams);
        this.fillTable(R.id.matchDayTable, matchDayHeader, matches);

        //If this is a league without a league table (for example, the quarterfinals of a knockout
        // competition) switch over to the matchday view.
        final ViewSwitcher switcher = (ViewSwitcher) this.findViewById(R.id.leagueViewSwitcher);
        if (teams.size() == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switcher.showNext();
                }
            });
        }
    }

    /**
     * Fills the table layout with the data received from livescore.com
     * @param tableId the table to be filled
     * @param header the header of the table
     * @param data the data to be entered
     */
    private void fillTable(int tableId, String[] header, ArrayList<? extends LeagueData> data) {

        int[] tableColours = new int[]{ R.color.colorTableSecondaryRow, R.color.colorTablePrimaryRow };
        if (data.size() % 2 == 0) {
            tableColours = new int[]{ R.color.colorTablePrimaryRow, R.color.colorTableSecondaryRow };
        }

        final TableLayout table = (TableLayout) this.findViewById(tableId);
        this.addRow(table, header, R.color.colorTableHeader);

        for (int i = 0; i < data.size(); i++) {
            String[] rowContent = data.get(i).toStringArray();
            this.addRow(table, rowContent, tableColours[i % 2]);
        }
    }

    /**
     * Adds a TableRow to a TableLayout
     * The TableRow is generated by using an array of strings which will be turned into TextViews
     * @param table the table to which the row will be added
     * @param data the data to generate the TableRow with
     * @param colour the background colour of the TableRow
     */
    private void addRow(final TableLayout table, String[] data, int colour) {
        final TableRow dataRow = new TableRow(this);
        dataRow.setBackgroundResource(colour);
        for (String columnText : data) {
            TextView dataText = new TextView(this);
            dataText.setText(columnText);
            dataRow.addView(dataText);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                table.addView(dataRow);
            }
        });
    }
}
