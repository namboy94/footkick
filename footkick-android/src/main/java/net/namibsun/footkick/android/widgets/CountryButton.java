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
import android.widget.Button;
import net.namibsun.footkick.android.content.CountryActivity;

/**
 * A Button that stores a country  and automatically defines an on click listener
 * to open that country's content activity
 */
@SuppressLint("ViewConstructor")
public class CountryButton extends Button {

    /**
     * Creates a new CountryButton and enables the OnclickListener
     * @param context the context from which the button will be called
     * @param countryName the country name assigned to this button
     * @param countryLink the country's link on livescore.com
     */
    public CountryButton(final Context context, final String countryName, String countryLink) {

        //Initializes button
        super(context);
        this.setText(countryName);
        final String buttonLink = countryLink.split("http://www.livescore.com")[1];

        //Set the OnclickListener
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent countryActivity = new Intent(context, CountryActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("country", countryName);
                bundle.putString("link", buttonLink);
                countryActivity.putExtras(bundle);
                context.startActivity(countryActivity);
            }
        });
    }
}
