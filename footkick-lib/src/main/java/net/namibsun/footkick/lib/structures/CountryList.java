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

package net.namibsun.footkick.lib.structures;

import net.namibsun.footkick.lib.scraper.Country;
import java.util.ArrayList;

/**
 * A class that stores all countries scraped with the CountryLister class
 * It stores the names and links for the countries and stores them in individual categories
 */
public class CountryList {

    private ArrayList<CountryCategory> countryCategories = new ArrayList<>();

    /**
     * Creates an new CountryList
     * @param countries an arraylist of arraylists of countries representing all countries found by
     *                  the CountryLister
     */
    public CountryList(ArrayList<ArrayList<Country>> countries) {

        String[] categoryIdentifiers = new String[] {
                "International", "Major", "Minor International", "Western Europe", "Eastern Europe", "South America",
                "North/Middle America", "Asia", "Oceania", "Africa", "Other"
        };

        for (int i = 0; i < 11; i++) {
            this.countryCategories.add(new CountryCategory(categoryIdentifiers[i], countries.get(i)));
        }

    }

    /**
     * Gets a List of all Countries as a single array list
     * @return the list of countries
     */
    public ArrayList<Country> getCountries() {
        ArrayList<Country> returnList = new ArrayList<>();

        for (CountryCategory category: this.countryCategories) {
            returnList.addAll(category.countries);
        }

        return returnList;
    }

    /**
     * Getter method for the countryCategories variable
     * @return all country categories
     */
    @SuppressWarnings("unused")
    public ArrayList<CountryCategory> getCountryCategories() {
        return this.countryCategories;
    }
}
