package net.namibsun.footkick.android.widgets;

import android.content.Context;
import android.widget.Button;

/**
 * A Button that stores a country and league combination
 */
public class CountryLeagueButton extends Button {

    String country;
    String league;

    public CountryLeagueButton(Context context, String country, String league) {
        super(context);
        this.setText(country + " / " + league);
        this.country = country;
        this.league = league;
    }

}
