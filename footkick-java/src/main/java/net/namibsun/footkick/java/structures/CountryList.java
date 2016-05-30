package net.namibsun.footkick.java.structures;

import net.namibsun.footkick.java.scraper.Country;
import net.namibsun.footkick.java.scraper.FootballHtmlParser;
import net.namibsun.footkick.java.scraper.LeagueInfo;
import net.namibsun.footkick.java.scraper.LeagueLister;

import java.io.IOException;
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

    public CountryList(ArrayList<ArrayList<Country>> countries) {
        this.international = countries.get(0);
        this.major = countries.get(1);
        this.otherInternational = countries.get(2);
        this.europe = countries.get(3);
        this.europe.addAll(countries.get(4));
        this.southAmerica = countries.get(5);
        this.concacaf = countries.get(6);
        this.asia = countries.get(7);
        this.oceania = countries.get(8);
        this.africa = countries.get(9);
        this.other = countries.get(10);

        for (int i = 0; i < this.other.size(); i++) {
            if (this.other.get(i).countryName.contains("More countries")) {
                this.other.remove(i);
                break;
            }
        }

    }

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

    public static void main(String[] args) {

        try {
            CountryList list = LeagueLister.getCountries();
            for (Country country : list.getCountries()) {
                System.out.println(country.countryName + "   @   " + country.countryUrl);
                for (LeagueInfo l : country.getLeagues()) {
                    System.out.println(l.leagueName + "   @   " + l.leagueUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
