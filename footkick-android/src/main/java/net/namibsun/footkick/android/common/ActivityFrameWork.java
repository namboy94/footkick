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

package net.namibsun.footkick.android.common;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.samples.quickstart.analytics.AnalyticsApplication;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public abstract class ActivityFrameWork extends AppCompatActivity {

    protected boolean connectionLost = false;
    protected Tracker analyticsTracker;
    protected int layoutFile = -1;
    protected String screenName = "Generic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (this.layoutFile != -1) {
            this.setContentView(this.layoutFile);
        }

        AnalyticsApplication application = (AnalyticsApplication) this.getApplication();
        this.analyticsTracker = application.getDefaultTracker();
        this.analyticsTracker.enableAdvertisingIdCollection(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        analyticsTracker.setScreenName(this.screenName);
        analyticsTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    protected abstract void getInternetData() throws IOException;

    protected void runInternetGetter(final ActivityFrameWork activity) {

        class AsyncDataGetter extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {
                boolean finished = false;
                while (!finished) {
                    try {
                        activity.getInternetData();
                        activity.connectionLost = false;
                        finished = true;
                    } catch (IOException e) {
                        if (!activity.connectionLost) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Notifiers.showConnectionErrorDialog(activity);
                                }
                            });
                            activity.connectionLost = true;
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                return null;
            }

        }
        new AsyncDataGetter().execute();
    }
}
