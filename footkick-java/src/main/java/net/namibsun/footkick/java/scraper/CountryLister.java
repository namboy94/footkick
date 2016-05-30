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

package net.namibsun.footkick.java.scraper;

import net.namibsun.footkick.java.structures.CountryList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A class that checks which countries are available for scraping
 */
public class CountryLister {

    /**
     * Parses www.livescore.com to get all available countries
     * @return a CountryList object containing information about all available countries
     * @throws IOException if the information getting fails
     */
    public static CountryList getCountries() throws IOException{

        String url = "http://www.livescore.com/";
        Document jsoupDocument = Jsoup.connect(url).get();

        ArrayList<ArrayList<Country>> countries = new ArrayList<>();
        Elements countryBlocks = jsoupDocument.select("ul");

        //Remove fluff
        countryBlocks.remove(18);
        countryBlocks.remove(1);
        countryBlocks.remove(0);

        for (Element countryBlock : countryBlocks) {
            Document singleCountriesDocument = Jsoup.parse(countryBlock.toString());
            Elements singleCountries = singleCountriesDocument.select("li");
            ArrayList<Country> blockCountries = new ArrayList<>();

            for (Element country: singleCountries) {
                String link = country.toString().split("<a href=\"")[1].split("\"")[0];
                Country countryObject = new Country(country.text(), link);
                blockCountries.add(countryObject);
            }

            countries.add(blockCountries);
        }

        return new CountryList(countries);

    }
}
