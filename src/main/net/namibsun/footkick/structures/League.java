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

}
