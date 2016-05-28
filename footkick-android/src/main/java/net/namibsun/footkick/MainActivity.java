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

package net.namibsun.footkick;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import net.namibsun.footkick_java.structures.League;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //REMOVE THIS
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            League league = new League("germany", "bundesliga");
            TableLayout table = (TableLayout) findViewById(R.id.table);
            TableRow newRow = new TableRow(table.getContext());
            TextView text = new TextView(table.getContext());
            text.setText(league.toMonoSpaceString());
            newRow.addView(text);
            table.addView(newRow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
