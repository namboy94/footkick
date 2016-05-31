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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import net.namibsun.footkick.android.common.Notifiers;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.samples.quickstart.analytics.AnalyticsApplication;
import net.namibsun.footkick.android.widgets.CountryButton;
import net.namibsun.footkick.java.scraper.Country;
import net.namibsun.footkick.java.scraper.CountryLister;

import java.io.IOException;
import java.util.ArrayList;


/**
 * The Main Activity class.
 * It displays a list of countries as buttons to load the leagues for that country
 */
public class MainActivity extends AppCompatActivity {

    private boolean connectionLost = false;
    private Tracker analyticsTracker;

    /**
     * Initializes the Main Activity with a loading screen and starts the country getting process.
     * The method inflates the activity_main.xml layout file
     * @param savedInstanceState - The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Creates the new Activity and sets the content view to that of activity_main.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new CountryGetter().execute();

        AnalyticsApplication application = (AnalyticsApplication) this.getApplication();
        this.analyticsTracker = application.getDefaultTracker();
        this.analyticsTracker.enableAdvertisingIdCollection(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        analyticsTracker.setScreenName("Mainactivity");
        analyticsTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    /**
     * Populates the country list and removes the loading screen
     */
    private void populateCountryList() {

        //final ViewSwitcher switcher = (ViewSwitcher) this.findViewById(R.id.loadingScreenSwitcher);
        final RelativeLayout layout = (RelativeLayout) this.findViewById(R.id.countryHolder);

        //get an array list of country identifiers
        ArrayList<Country> countries;
        try {
            countries = CountryLister.getCountries().getCountries();
            this.connectionLost = false;
        } catch (IOException e) {
            if (!this.connectionLost) {
                this.connectionLost = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Notifiers.showConnectionErrorDialog(MainActivity.this);
                    }
                });
            }


            //Handle Dropped Connections
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            this.populateCountryList();
            return;
        }

        //Add the buttons
        int lastId = R.id.countryText;
        for (Country country : countries) {

            final CountryButton countryButton = new CountryButton(this, country.countryName, country.countryUrl);
            countryButton.setId(View.generateViewId());
            final RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            buttonParams.addRule(RelativeLayout.BELOW, lastId);
            lastId = countryButton.getId();


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    layout.addView(countryButton, buttonParams);
                }
            });
        }
        final ViewSwitcher switcher = (ViewSwitcher) this.findViewById(R.id.loadingScreenSwitcher);
        //Switch loading screen away
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switcher.showNext();
            }
        });
    }

    /**
     * An Async task that loads the available countries and adds them to the list,
     * then switches the loading screen away
     */
    private class CountryGetter extends AsyncTask<Void, Void, Void> {

        /**
         * Runs in the background
         * @param params Void
         * @return Void
         */
        @Override
        protected Void doInBackground(Void... params) {
            MainActivity.this.populateCountryList();
            return null;
        }
    }
}
