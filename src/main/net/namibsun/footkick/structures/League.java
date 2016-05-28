package net.namibsun.footkick.structures;

import net.namibsun.footkick.scraper.FootballHtmlParser;

import java.io.IOException;

/**
 * A class that contains all the information about a league
 */
public class League {

    String country;
    String league;
    LeagueTable leagueTable;
    MatchDay matchDay;

    /**
     * Constructs a new League object that automatically parses the relevant information
     * based on the given country and league
     * @param country the country to be used
     * @param league the league to be used
     * @throws IOException in case the parsing failed
     */
    public League(String country, String league) throws IOException{

        this.country = country;
        this.league = league;

        FootballHtmlParser parser = new FootballHtmlParser(country, league);
        this.leagueTable = new LeagueTable(country, league, parser.getLeagueInformation());
        this.matchDay = new MatchDay(country, league, parser.getMatchdayInformation());

    }

    /**
     * Convert the League into a monospaced string
     * @return the monospaced String
     */
    public String toMonoSpaceString() {

        String formatted = this.matchDay.toMonoSpaceString() + "\n";
        formatted += this.leagueTable.toMonoSpaceString();

        return formatted;

    }

    /**
     * Pads a string to the left by a specified amount with spaces
     * @param string the string to be padded
     * @param amount the amount of characters the string should be (at least)
     * @return the padded String
     */
    public static String padLeft(String string, int amount) {
        return String.format("%1$" + amount + "s", string);
    }

    /**
     * Pads a string to the right by a specified amount with spaces
     * @param string the string to be padded
     * @param amount the amount of characters the string should be (at least)
     * @return the padded String
     */
    public static String padRight(String string, int amount) {
        return String.format("%1$-" + amount + "s", string);
    }

}
