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
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import net.namibsun.footkick.R;
import net.namibsun.footkick.java.scraper.Team;
import net.namibsun.footkick.java.scraper.FootballHtmlParser;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //REMOVE THIS
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //REMOVE THIS


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Team> teams = FootballHtmlParser.getLeagueInformation("germany", "bundesliga");

        ScrollView scrollView = (ScrollView) this.findViewById(R.id.scrollview);

        TableLayout table = new TableLayout(this);
        table.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                           ViewGroup.LayoutParams.MATCH_PARENT));
        table.setStretchAllColumns(true);

        for (Team team : teams) {
            TableRow newRow = new TableRow(this);
            TextView teamName = new TextView(this);
            teamName.setText(team.teamName);
            TextView teamPoints = new TextView(this);
            teamPoints.setText(team.points);

            newRow.addView(teamName);
            newRow.addView(teamPoints);

            table.addView(newRow);

        }

        scrollView.addView(table);

    }
}
