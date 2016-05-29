package net.namibsun.footkick.java.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


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
}
