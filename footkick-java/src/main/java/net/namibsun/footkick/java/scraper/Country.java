package net.namibsun.footkick.java.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that stores the name and link for a country.
 * It also has the capability to search for leagues in that country
 */
public class Country {

    public String countryName;
    public String countryUrl;

    /**
     * Construcotr that automatically generates a valid URL from the countryUrl parameter
     * and stores the country name
     * @param countryName the country's name
     * @param countryUrl the country's livescore.com url
     */
    public Country(String countryName, String countryUrl) {
        this.countryName = countryName;
        this.countryUrl = "http://www.livescore.com" + countryUrl;
    }

    /**
     * Parses the country's URL to check which leagues the country has.
     * @return an Array List of LeagueInfo objects representing the available leagues
     * @throws IOException if the connection to the server was unsuccessful
     */
    public ArrayList<LeagueInfo> getLeagues() throws IOException {

        Document jsoupDocument = Jsoup.connect(this.countryUrl).get();
        Elements ulBlocks = jsoupDocument.select("ul");
        Element countryLeagues;

        // Euros and World Cups need to be parsed differently
        if (this.countryName.toLowerCase().startsWith("euro ") ||
                this.countryName.toLowerCase().startsWith("world cup")) {

            countryLeagues = ulBlocks.get(4);

        }
        else {

            ArrayList<Element> normalButtonBlocks = new ArrayList<>();

            for (Element ulBlock : ulBlocks) {
                if (ulBlock.toString().startsWith("<ul class=\"buttons\">")) {
                    normalButtonBlocks.add(ulBlock);
                }
            }

            countryLeagues = normalButtonBlocks.get(1);
        }

        jsoupDocument = Jsoup.parse(countryLeagues.toString());
        Elements leagues = jsoupDocument.select("a");

        ArrayList<LeagueInfo> leagueInfos = new ArrayList<>();

        for (Element league : leagues) {
            leagueInfos.add(new LeagueInfo(league.text(),
                    league.toString().split("<a href=\"")[1].split("\"")[0]));
        }

        return leagueInfos;

    }
}
