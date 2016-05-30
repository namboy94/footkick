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

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Class that contains static methods to show the user messages
 */
public class Notifiers {

    /**
     * Shows connection error dialog
     */
    public static void showConnectionErrorDialog(AppCompatActivity activity){

        AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(activity);
        errorDialogBuilder.setTitle("Connection Error");
        errorDialogBuilder.setMessage("No connection to server");
        errorDialogBuilder.setCancelable(true);
        errorDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        errorDialogBuilder.create();
        errorDialogBuilder.show();
    }

}
