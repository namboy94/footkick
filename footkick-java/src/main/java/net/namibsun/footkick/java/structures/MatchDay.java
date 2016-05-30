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
import net.namibsun.footkick.java.scraper.Match;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that stores all matches in a particular matchday.
 */
public class MatchDay {

    ArrayList<Match> matches;

    /**
     * Constructor that automatically parses the matchday for the specified league and country
     * @param country the country to parse
     * @param league the league to parse
     * @throws IOException in case the parsing fails
     */
    public MatchDay(String country, String league) throws IOException {
        this.matches = FootballHtmlParser.getMatchdayInformation(country, league);
    }

    /**
     * Alternate Constructor that does not parse the matches itself, but rather gets
     * pre-defined matches via constructor
     * @param matches the matches of the matchday
     */
    public MatchDay(ArrayList<Match> matches) {
        this.matches = matches;
    }

    /**
     * Getter method for the list of matches
     * @return an array list of all matches
     */
    public ArrayList<Match> getMatches() {
        return this.matches;
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
            formatted += League.padRight(match.time, 3);
            formatted += League.padLeft(match.homeTeam, longestHomeTeamLength + 1);
            formatted += " " + League.padRight(match.score, 5) + " ";
            formatted += League.padRight(match.awayTeam, longestAwayTeamLength + 1);
            formatted += "\n";
        }

        return formatted;

    }

}
