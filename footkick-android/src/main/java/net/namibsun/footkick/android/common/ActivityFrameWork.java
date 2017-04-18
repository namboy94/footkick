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
import android.view.View;

import java.io.IOException;

/**
 * This is an Activity class that defines common functionality and attributes for all other
 * activities used in this App. The Activity does the following:
 *
 * 1. Initializes the Google Analytics tracker and automatically tracks whenever the
 *    onContinue method is called,
 *    i.e. whenever that activity is called into the foreground
 * 2. Loads a predetermined XML layout file
 * 3. Sets the ActionBars to the Activity's screenName
 */
public abstract class ActivityFrameWork extends AppCompatActivity {

    /**
     * The Google Analytics tracker
     */
    //protected Tracker analyticsTracker;

    /**
     * The ID of the XML layout file
     */
    protected int layoutFile;

    /**
     * The name to be displayed by the action bar
     */
    protected String screenName;

    /**
     * The name of the activity that will be logged by Google Analytics
     */
    protected String analyticsName;


    /**
     * The constructor (essentially) of the activity. It takes care of initializing the XML file,
     * analytics tracker and action bar title
     * @param savedInstanceState the saved instance sent by the Android OS
     */
    @SuppressWarnings("ConstantConditions") //To appease IntelliJ regarding the setting of the action bar title
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Calls the parent classes' onCreate method
        super.onCreate(savedInstanceState);

        //Loads the layout file, won't load if the layout's ID is -1, i.e. not set.
        if (this.layoutFile != -1) {
            this.setContentView(this.layoutFile);
        }

        //Initializes the analytics tracker
        //AnalyticsApplication application = (AnalyticsApplication) this.getApplication();
        //this.analyticsTracker = application.getDefaultTracker();
        //this.analyticsTracker.enableAdvertisingIdCollection(true); //Enable demographics tracking

        //For compatibility reasons, we try to set the support action bar and the action bar as well
        //One or the other always exists, depending on the version of android
        try {
            this.getSupportActionBar().setTitle(this.screenName);
        } catch (NullPointerException e) {
            this.getActionBar().setTitle(this.screenName);
        }

        this.runInternetDataGetter(this);
    }

    /**
     * Method that gets called whenever the activity comes into focus
     * Google Analytics tracks whenever this is called and sends the name of the activity's
     * analyticsName
     */
    @Override
    protected void onResume() {
        super.onResume();
        //analyticsTracker.setScreenName(this.analyticsName);  // Set the name to be sent to the analytics service
        //analyticsTracker.send(new HitBuilders.ScreenViewBuilder().build()); // And send it
    }

    /**
     * Removes a view from the UI. This method should be run whenever removing views from a different thread.
     * @param viewId the view to hide's View ID
     */
    protected void removeView(int viewId) {
        final View view = this.findViewById(viewId);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        });
    }

    /**
     * This method should be overriden when the class should get information from the internet.
     * @throws IOException when a network error occurs. This will be handled by the
     * runInternetDataGetter method.
     * It is important to note that any actions that affect the UI thread need to be wrapped in a
     * Runnable and called using runOnUiThread
     */
    protected abstract void getInternetData() throws IOException;

    /**
     * This gets the internet data defined in the getInternetData method and handles
     * connection errors by showing the user a notice that the connection was lost, then
     * retrying to connect every 3 seconds.
     * @param activity the activity that calls this method. This needs to be explicitly
     *                 provided due to the internal AsyncDataGetter class needing access to
     *                 the activity's methods and variables, most importantly the
     *                 getInternetData method
     */
    protected void runInternetDataGetter(final ActivityFrameWork activity) {

        /**
         * An AsyncTask that runs in the background, i.e. not on the main thread of
         * the app. It defines a method that wraps the getInternetData in various
         * checks to handle network errors
         */
        class AsyncDataGetter extends AsyncTask<Void, Void, Void> {

            /**
             * Does a background task, in which the app checks for IOExceptions caused by
             * network loss. If this occurs, a message dialog is shown to the user, and the method will
             * automatically retry to get the online data once every three seconds
             * @param params the parameters given, which in this case are Void parameters, hence irrelevant
             * @return just null
             */
            @Override
            protected Void doInBackground(Void... params) {

                boolean connectionLost = false;  //Used to check if the connection was lost
                boolean finished = false;  //Used to check if the operation has finished successfully

                //Try to get the data until we have it
                while (!finished) {
                    try {
                        activity.getInternetData();  //Get the internet data
                        finished = true;  //On success, tell the program that the loop has finished
                    } catch (IOException e) { //If the operation was no successful, handle the lost connection

                        //Show an error dialog in case this is the first loop,
                        //then let the app know that it is known that the connection was lost
                        if (!connectionLost) {
                            activity.runOnUiThread(new Runnable() { //Wrap the dialog shower in an runOnUiThread
                                                                    //Runnable, since this is not the UI thread
                                @Override
                                public void run() {
                                    Notifiers.showConnectionErrorDialog(activity);
                                }
                            });
                            connectionLost = true;
                        }

                        //Now sleep for three seconds before trying again
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
        new AsyncDataGetter().execute();  //Execute the async task.
    }
}
