package net.namibsun.footkick.java.scraper;

import net.namibsun.footkick.java.structures.CountryList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A class that checks which leagues are available for scraping
 */
public class LeagueLister {

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
