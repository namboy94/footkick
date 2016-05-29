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
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

    /**
     * Method run on creation of the Activity
     *
     * @param savedInstanceState the current instance state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.populateData(savedInstanceState.getString("country"), savedInstanceState.getString("league"));
    }

    private void populateData(String country, String league) {

        try {
            League leagueData = new League(country, league);
            ArrayList<Team> teams = leagueData.getTeams();
            ArrayList<Match> matches = leagueData.getMatches();

            this.fillLeagueTable(teams);
            this.fillMatchday(matches);

        } catch (IOException e) {
        }

    }

    private void fillLeagueTable(ArrayList<Team> teams) {

        ScrollView scroller = this.findViewById(R.id.leagueTableScroller);
        TableLayout table = new TableLayout(this);

        int position = 1;
        for (Team team : teams) {
            TableRow teamRow = new TableRow(this);

            String[] data = {
                    "" + position, team.teamName, team.matches, team.wins, team.draws, team.losses, team.goalsFor,
                    team.goalsAgainst, team.goalDifference, team.points
            };

            for (String dataElement : data) {
                TextView dataText = new TextView(this);
                dataText.setText(dataElement);
                teamRow.addView(dataText);
            }

            table.addView(teamRow);
            position++;
        }

        scroller.addView(table);

    }

    private void fillMatchday(ArrayList<Match> matches) {

        ScrollView scroller = this.findViewById(R.id.matchDayScroller);
        TableLayout matchDayTable = new TableLayout(this);

        for (Match match : matches) {
            TableRow matchRow = new TableRow(this);

            String[] matchData = {
                    match.time, match.homeTeam, match.score, match.awayTeam
            };

            for (String dataElement: matchData) {
                TextView dataText = new TextView(this);
                dataText.setText(dataElement);
                matchRow.addView(dataText);
            }

            matchDayTable.addView(matchRow);
        }
        scroller.addView(matchDayTable);

    }

}
