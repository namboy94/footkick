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
import android.widget.*;
import net.namibsun.footkick.android.common.ActivityFrameWork;
import net.namibsun.footkick.android.widgets.CountryButton;
import net.namibsun.footkick.lib.scraper.Country;
import net.namibsun.footkick.lib.scraper.CountryLister;

import java.io.IOException;
import java.util.ArrayList;


/**
 * The Main Activity class.
 * It displays a list of countries as buttons to load the leagues for that country
 */
public class MainActivity extends ActivityFrameWork {

    /**
     * Initializes the Main Activity with the activity_main.xml layout and sets the activity names.
     * @param savedInstanceState the saved instance state provided for the activity
     */
    @Override     
    protected void onCreate(Bundle savedInstanceState) {

        this.layoutFile = R.layout.activity_main;
        this.screenName = "Countries";
        super.onCreate(savedInstanceState);
    }

    /**
     * Populates the country list and removes the loading screen on success
     * Potential Network errors are handled by the Activity FrameWork
     * @throws IOException in case a network error occurs.
     */
    @Override
    protected void getInternetData() throws IOException{

        //Get the country identifiers
        ArrayList<Country> countries = CountryLister.getCountries().getCountries();
        this.removeView(R.id.main_activity_progress);

        final RelativeLayout layout = (RelativeLayout) this.findViewById(R.id.countryHolder);
        int lastId = -1;  //used to identify under which view the next button will be placed
                          //Initialized with a non-existent ID to set the first button to the top

        for (Country country : countries) {

            final CountryButton countryButton =
                    new CountryButton(this, country.countryName, country.countryUrl);
            final RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            buttonParams.addRule(RelativeLayout.BELOW, lastId);

            if (lastId == -1) {
                lastId++;
            }
            countryButton.setId(lastId + 1);
            lastId = countryButton.getId();

            //Add button to bottom of relative layout
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    layout.addView(countryButton, buttonParams);
                }
            });
        }
    }
}
