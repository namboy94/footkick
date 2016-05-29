package net.namibsun.footkick.android.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import net.namibsun.footkick.android.content.LeagueActivity;

/**
 * A Button that stores a country and league combination and automatically defines an on click listener
 * to open that country/league's content activity
 */
@SuppressLint("ViewConstructor")
public class CountryLeagueButton extends Button {

    public String country;
    public String league;

    /**
     * Creates a new CountryLeagueButton and enables the OnclickListener
     * @param context the context from which the button will be called
     * @param country the country assigned to this button
     * @param league the league assigned to this button
     */
    @SuppressLint("SetTextI18n")
    public CountryLeagueButton(final Context context, final String country, final String league) {

        //Initializes button
        super(context);
        this.setText(country + " / " + league);
        this.country = country;
        this.league = league;

        //Set the OnclickListener
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leagueActivity = new Intent(context, LeagueActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("country", country);
                bundle.putString("league", league);
                leagueActivity.putExtras(bundle);
                context.startActivity(leagueActivity);
            }
        });
    }
}
