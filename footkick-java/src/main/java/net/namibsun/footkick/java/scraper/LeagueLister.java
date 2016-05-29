package net.namibsun.footkick.java.scraper;

/**
 * A class that checks which leagues are available for scraping
 */
public class LeagueLister {

    public static String[] getLeagues() {

        return new String[]{
                "germany bundesliga",
                "england premier-league",
                "italy serie-a"
        };

    }

}
