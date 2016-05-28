package net.namibsun.footkick.structures;

import net.namibsun.footkick.scraper.FootballHtmlParser;
import net.namibsun.footkick.scraper.Team;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that stores the league table standings of a football league
 */
public class LeagueTable {

    String country;
    String league;
    ArrayList<Team> teams;

    /**
     * Constructor that automatically parses the league table for the specified country an league
     * @param country the country to be used
     * @param league the league to be used
     * @throws IOException if the parsing failed
     */
    public LeagueTable(String country, String league) throws IOException{

        this.country = country;
        this.league = league;
        this.teams = FootballHtmlParser.getLeagueInformation(country, league);

    }

    /**
     * Alternate constructor that does not parse the league table itself, but rather gets the
     * team objects given via parameter
     * @param country the country of the league table
     * @param league the league of the league table
     * @param teams the teams in the league table
     */
    public LeagueTable(String country, String league, ArrayList<Team> teams) {

        this.country = country;
        this.league = league;
        this.teams = teams;

    }

    /**
     * Converts the league into a monospaced string
     * @return the formatted league table string
     */
    public String toMonoSpaceString() {

        int longestTeamName = 0;
        for (Team team : this.teams) {
            if (team.teamName.length() > longestTeamName) {
                longestTeamName = team.teamName.length();
            }
        }

        String formatted = "#    " + League.padRight("Team Name", longestTeamName);
        formatted += "  P     W     D     L     GF    GA    GD    Pts\n";
        String divider = "  ";

        int position = 0;
        for (Team team : this.teams) {

            position++;

            String line = "\n" + League.padRight(("" + position), 3) + divider;
            line += League.padRight(team.teamName, longestTeamName) + divider;
            line += League.padRight(team.matches, 4) + divider;
            line += League.padRight(team.wins, 4) + divider;
            line += League.padRight(team.draws, 4) + divider;
            line += League.padRight(team.losses, 4) + divider;
            line += League.padRight(team.goalsFor, 4) + divider;
            line += League.padRight(team.goalsAgainst, 4) + divider;
            line += League.padRight(team.goalDifference, 4) + divider;
            line += League.padRight(team.points, 4) + divider;

            formatted += line;
        }

        return formatted;

    }

}
