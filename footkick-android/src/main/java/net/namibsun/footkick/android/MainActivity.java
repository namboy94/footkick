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
import android.view.View;
import android.widget.*;
import net.namibsun.footkick.android.common.ActivityFrameWork;
import net.namibsun.footkick.android.widgets.CountryButton;
import net.namibsun.footkick.java.scraper.Country;
import net.namibsun.footkick.java.scraper.CountryLister;

import java.io.IOException;
import java.util.ArrayList;


/**
 * The Main Activity class.
 * It displays a list of countries as buttons to load the leagues for that country
 */
public class MainActivity extends ActivityFrameWork {

    protected int layoutFile = R.layout.activity_main;
    protected String screenName = "Countries";
    protected String analyticsName = "Countries";

    /**
     * Initializes the Main Activity with a loading screen and starts the country getting process.
     * The method inflates the activity_main.xml layout file
     * @param savedInstanceState - The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //Initialize Analytics etc.
        this.runInternetDataGetter(this);  //Populate the activity data
    }

    /**
     * Populates the country list and removes the loading screen on success
     * Potential Network errors are handled by the Activity FrameWork
     */
    @Override
    protected void getInternetData() throws IOException{

        ArrayList<Country> countries = CountryLister.getCountries().getCountries(); //Get the country identifiers

        final ViewSwitcher switcher = (ViewSwitcher) this.findViewById(R.id.loadingScreenSwitcher);
        final RelativeLayout layout = (RelativeLayout) this.findViewById(R.id.countryHolder);
        int lastId = -1;  //used to identify under which view the next button will be placed
                          //Initialized with a non-existent ID to set the first button to the top

        for (Country country : countries) {

            final CountryButton countryButton = new CountryButton(this, country.countryName, country.countryUrl);
            final RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            countryButton.setId(View.generateViewId());
            buttonParams.addRule(RelativeLayout.BELOW, lastId);
            lastId = countryButton.getId();

            //Add button to bottom of relative layout
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    layout.addView(countryButton, buttonParams);
                }
            });
        }
        //Switch loading screen away
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switcher.showNext();
            }
        });
    }

}
