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
import net.namibsun.footkick.java.scraper.Match;
import net.namibsun.footkick.java.scraper.Team;
import net.namibsun.footkick.java.structures.League;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An activity that displays a league table and a matchday
 */
public class LeagueActivity extends ActivityFrameWork{

    private GestureDetector gestureDetector;
    private String leagueLink;

    protected void initialize() {
        Bundle bundle = this.getIntent().getExtras();

        this.layoutFile = R.layout.activity_league;
        this.analyticsName = bundle.getString("league");
        this.screenName = bundle.getString("league");
        this.leagueLink = bundle.getString("link");

    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ViewSwitcher switcher = (ViewSwitcher) this.findViewById(R.id.leagueViewSwitcher);
        ViewSwiper swipeDetector = new ViewSwiper(this, switcher);
        this.gestureDetector = new GestureDetector(this, swipeDetector);
    }

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

    protected void getInternetData() throws IOException {

        League leagueData = new League(this.leagueLink);
        ArrayList<Team> teams = leagueData.getTeams();
        ArrayList<Match> matches = leagueData.getMatches();

        this.fillLeagueTable(teams);
        this.fillMatchday(matches);

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

    private void fillLeagueTable(ArrayList<Team> teams) {

        final TableLayout table = (TableLayout) this.findViewById(R.id.leagueTableTable);
        int position = 1;

        for (Team team : teams) {
            final TableRow teamRow = new TableRow(this);
            teamRow.setBackgroundResource(this.getRowColour(position - 1, teams.size()));

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

    private void fillMatchday(ArrayList<Match> matches) {




        final TableLayout matchDayTable = (TableLayout) this.findViewById(R.id.matchDayTable);
        int row = 0;

        for (Match match : matches) {

            final TableRow matchRow = new TableRow(this);
            matchRow.setBackgroundResource(this.getRowColour(row, matches.size()));
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

    private int getRowColour(int rowNumber, int totalRows) {
        int[] tableColours = new int[]{ R.color.colorTableSecondaryRow, R.color.colorTablePrimaryRow };
        if (totalRows % 2 == 0) {
            tableColours = new int[]{ R.color.colorTablePrimaryRow, R.color.colorTableSecondaryRow };
        }
        return tableColours[rowNumber % 2];
    }



}
