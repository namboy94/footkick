package net.namibsun.footkick.structures;

import com.google.common.base.Strings;
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

        String formatted = "#    " + Strings.padEnd("Team Name", longestTeamName, ' ');
        formatted += "  P     W     D     L     GF    GA    GD    Pts\n";
        String divider = "  ";

        int position = 0;
        for (Team team : this.teams) {

            position++;

            String line = "\n" + Strings.padEnd(("" + position), 3, ' ') + divider;
            line += Strings.padEnd(team.teamName, longestTeamName, ' ') + divider;
            line += Strings.padEnd(team.matches, 4, ' ') + divider;
            line += Strings.padEnd(team.wins, 4, ' ') + divider;
            line += Strings.padEnd(team.draws, 4, ' ') + divider;
            line += Strings.padEnd(team.losses, 4, ' ') + divider;
            line += Strings.padEnd(team.goalsFor, 4, ' ') + divider;
            line += Strings.padEnd(team.goalsAgainst, 4, ' ') + divider;
            line += Strings.padEnd(team.goalDifference, 4, ' ') + divider;
            line += Strings.padEnd(team.points, 4, ' ') + divider;

            formatted += line;
        }

        return formatted;

    }

}
