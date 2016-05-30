package net.namibsun.footkick.java.structures;

import net.namibsun.footkick.java.scraper.Country;
import java.util.ArrayList;

/**
 * A class that stores all countries scraped with the LeagueLister class
 * It stores the names and links for the countries and stores them in individual categories
 */
public class CountryList {

    ArrayList<Country> international;
    ArrayList<Country> major;
    ArrayList<Country> otherInternational;
    ArrayList<Country> europe;
    ArrayList<Country> southAmerica;
    ArrayList<Country> concacaf;
    ArrayList<Country> asia;
    ArrayList<Country> oceania;
    ArrayList<Country> africa;
    ArrayList<Country> other;

    /**
     * Creates an new CountryList
     * @param countries an arraylist of arraylists of countries representing all countries found by
     *                  the LeagueLister
     */
    public CountryList(ArrayList<ArrayList<Country>> countries) {
        this.international = countries.get(0);
        this.major = countries.get(1);
        this.otherInternational = countries.get(2);
        this.europe = countries.get(3);
        this.europe.addAll(countries.get(4));  // Merge West and East Europe
        this.southAmerica = countries.get(5);
        this.concacaf = countries.get(6);
        this.asia = countries.get(7);
        this.oceania = countries.get(8);
        this.africa = countries.get(9);
        this.other = countries.get(10);

        // Remove the 'More countries entry'
        for (int i = 0; i < this.other.size(); i++) {
            if (this.other.get(i).countryName.contains("More countries")) {
                this.other.remove(i);
                break;
            }
        }

    }

    /**
     * Gets a List of all Countries as a single array list
     * @return the list of countries
     */
    public ArrayList<Country> getCountries() {
        ArrayList<Country> returnList = this.major;
        returnList.addAll(this.international);
        returnList.addAll(this.otherInternational);
        returnList.addAll(this.europe);
        returnList.addAll(this.southAmerica);
        returnList.addAll(this.concacaf);
        returnList.addAll(this.asia);
        returnList.addAll(this.oceania);
        returnList.addAll(this.other);
        return returnList;
    }
}
