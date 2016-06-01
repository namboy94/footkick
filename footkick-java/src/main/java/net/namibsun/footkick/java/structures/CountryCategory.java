package net.namibsun.footkick.java.structures;

import net.namibsun.footkick.java.scraper.Country;

import java.util.ArrayList;

/**
 * Class that models a category of countries, for example Europe, Africa, Rest of World, etc.
 */
public class CountryCategory {

    public String categoryName;
    public ArrayList<Country> countries = new ArrayList<>();

    /**
     * Creates a new Country Category. This defines the category name as well as the
     * countries held within it.
     * @param categoryName the category name
     * @param countries lists of countries to add to this category
     */
    @SafeVarargs
    public CountryCategory(String categoryName, ArrayList<Country> ... countries) {

        this.categoryName = categoryName;

        for (ArrayList<Country> countrylist : countries) {
            this.countries.addAll(countrylist);
        }
    }
}
