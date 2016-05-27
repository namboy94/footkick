package net.namibsun.footkick.structures;

import com.google.common.base.Strings;
import net.namibsun.footkick.scraper.FootballHtmlParser;
import net.namibsun.footkick.scraper.Match;
import net.namibsun.footkick.scraper.Team;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that stores all matches in a particular matchday.
 */
public class MatchDay {

    String country;
    String league;
    ArrayList<Match> matches;

    /**
     * Constructor that automatically parses the matchday for the specified league and country
     * @param country the country to parse
     * @param league the league to parse
     * @throws IOException in case the parsing fails
     */
    public MatchDay(String country, String league) throws IOException {

        this.country = country;
        this.league = league;
        this.matches = FootballHtmlParser.getMatchdayInformation(country, league);

    }

    /**
     * Alternate Constructor that does not parse the matches itself, but rather gets
     * pre-defined matches via constructor
     * @param country the country of the matchday
     * @param league the league of the matchday
     * @param matches the matches of the matchday
     */
    public MatchDay(String country, String league, ArrayList<Match> matches) {

        this.country = country;
        this.league = league;
        this.matches = matches;
    }

    /**
     * Formats the matchday into a monospaced string
     * @return the formatted matchday string
     */
    public String toMonoSpaceString() {

        int longestHomeTeamLength = 0;
        int longestAwayTeamLength = 0;
        for (Match match : this.matches) {
            if (match.homeTeam.length() > longestHomeTeamLength) {
                longestHomeTeamLength = match.homeTeam.length();
            }
            if (match.awayTeam.length() > longestAwayTeamLength) {
                longestAwayTeamLength = match.awayTeam.length();
            }
        }

        String formatted = "";

        for (Match match : this.matches) {
            formatted += Strings.padEnd(match.time, 3, ' ');
            formatted += Strings.padStart(match.homeTeam, longestHomeTeamLength + 1, ' ');
            formatted += " " + Strings.padEnd(match.score, 5, ' ') + " ";
            formatted += Strings.padEnd(match.awayTeam, longestAwayTeamLength + 1, ' ');
            formatted += "\n";
        }

        return formatted;

    }

}
