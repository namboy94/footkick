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

package net.namibsun.footkick.java;

import net.namibsun.footkick.java.scraper.Country;
import net.namibsun.footkick.java.scraper.CountryLister;
import net.namibsun.footkick.java.structures.CountryList;
import net.namibsun.footkick.java.structures.League;
import net.namibsun.footkick.java.structures.LeagueTable;
import net.namibsun.footkick.java.structures.MatchDay;

import java.util.ArrayList;

/**
 * The Main Java Class
 */
public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length == 1 && args[0].equals("list")) {
            CountryList countries = CountryLister.getCountries();
            int i = 0;
            for (Country country: countries.getCountries()) {
                System.out.println(country.countryName + " @ " + country.countryUrl + "    " +i);
                i++;
            }
        }
        else if (args.length != 3){
            System.out.println("Invalid amount of parameters");
            System.out.println("Usage: <country>, <league>, [table|matchday|summary]");
        }
        else if (!args[2].toLowerCase().equals("matchday")
                && !args[2].toLowerCase().equals("table")
                && !args[2].toLowerCase().equals("summary")) {
            System.out.println("Invalid mode");
            System.out.println("Please use either 'table', 'matchday' or 'summary'");
        }
        else {
            String country = args[0];
            String league = args[1];
            String mode = args[2];

            switch (mode) {
                case "table":
                    LeagueTable leagueTable = new LeagueTable(country, league);
                    System.out.println(leagueTable.toMonoSpaceString());
                    break;
                case "matchday":
                    MatchDay matchDay = new MatchDay(country, league);
                    System.out.println(matchDay.toMonoSpaceString());
                    break;
                case "summary":
                    League leagueObject = new League(country, league);
                    System.out.println(leagueObject.toMonoSpaceString());
                    break;
            }
        }

    }

}