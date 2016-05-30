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
     * Gets the available leagues and eturns them as Strings formatted like this:
     *          'country league'
     * @return the array of leagues
     */
    public static String[] getLeagues() {

        return new String[] {
                "germany bundesliga",
                "england premier-league",
                "spain primera-division",
                "italy serie-a",
                "namibia premier-league"
        };
    }

    public static CountryList getCountries() throws IOException{

        String url = "http://www.livescore.com/";
        Document jsoupDocument = Jsoup.connect(url).get();

        ArrayList<ArrayList<Country>> countries = new ArrayList<>();
        Elements countryBlocks = jsoupDocument.select("ul");
        countryBlocks.remove(18);
        countryBlocks.remove(1);
        countryBlocks.remove(0);

        int i = 0;
        for (Element countryBlock : countryBlocks) {
            System.out.println("NEWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW" + i);
            i++;
            Document singleCountriesDocument = Jsoup.parse(countryBlock.toString());
            Elements singleCountries = singleCountriesDocument.select("li");
            ArrayList<Country> blockCountries = new ArrayList<>();

            for (Element country: singleCountries) {
                System.out.println(country.toString());
                String link = country.toString().split("<a href=\"")[1].split("\"")[0];
                Country countryObject = new Country(country.text(), link);
                blockCountries.add(countryObject);
            }

            countries.add(blockCountries);
        }

        return new CountryList(countries);

    }
}
