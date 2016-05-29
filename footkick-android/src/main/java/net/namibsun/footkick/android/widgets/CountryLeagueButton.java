package net.namibsun.footkick.android.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import net.namibsun.footkick.android.content.LeagueActivity;

/**
 * A Button that stores a country and league combination
 */
public class CountryLeagueButton extends Button {

    public String country;
    public String league;

    public CountryLeagueButton(Context context, String country, String league) {
        super(context);
        this.setText(country + " / " + league);
        this.country = country;
        this.league = league;
    }

}
