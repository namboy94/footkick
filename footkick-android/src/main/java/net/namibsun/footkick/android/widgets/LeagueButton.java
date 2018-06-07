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

package net.namibsun.footkick.android.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import net.namibsun.footkick.android.content.LeagueActivity;

/**
 * A Button that stores a country and league combination and automatically defines an on click listener
 * to open that country/league's content activity
 */
@SuppressLint("ViewConstructor")
public class LeagueButton extends android.support.v7.widget.AppCompatButton {

    /**
     * Creates a new CountryLeagueButton and enables the OnclickListener
     * @param context the context from which the button will be called
     * @param countryName the name of the league's country
     * @param leagueName the name of the league
     * @param leagueLink the link to the site of the league
     */
    @SuppressLint("SetTextI18n")
    public LeagueButton(final Context context,
                        final String countryName, final String leagueName, final String leagueLink) {

        //Initializes button
        super(context);
        this.setText(leagueName);

        //Set the OnclickListener
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leagueActivity = new Intent(context, LeagueActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("league", leagueName);
                bundle.putString("link", leagueLink);
                bundle.putString("country", countryName);
                leagueActivity.putExtras(bundle);
                context.startActivity(leagueActivity);
            }
        });
    }
}
