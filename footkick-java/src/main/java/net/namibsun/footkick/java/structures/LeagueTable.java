/*
Copyright 2016 Hermann Krumrey

This file is part of footkick.

    footkick is a program that lets a user view football (soccer)
    match standings and league tables

    footkick is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    footkick is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with footkick. If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.footkick.java.structures;

import net.namibsun.footkick.java.scraper.FootballHtmlParser;
import net.namibsun.footkick.java.scraper.Team;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that stores the league table standings of a football league
 */
public class LeagueTable {

    ArrayList<Team> teams;

    /**
     * Constructor that automatically parses the league table for the specified country an league
     * @param country the country to be used
     * @param league the league to be used
     * @throws IOException if the parsing failed
     */
    public LeagueTable(String country, String league) throws IOException{

        this.teams = FootballHtmlParser.getLeagueInformation(country, league);

    }

    /**
     * Alternate constructor that does not parse the league table itself, but rather gets the
     * team objects given via parameter
     * @param teams the teams in the league table
     */
    public LeagueTable(ArrayList<Team> teams) {
        this.teams = teams;
    }

    /**
     * Getter method for the teams in the league
     * @return an array list of teams in the league
     */
    public ArrayList<Team> getTeams() {
        return this.teams;
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
