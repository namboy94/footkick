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
import net.namibsun.footkick.java.scraper.Team;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that contains all the information about a league
 */
public class League {

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

        FootballHtmlParser parser = new FootballHtmlParser(country, league);
        this.leagueTable = new LeagueTable(parser.getLeagueInformation());
        this.matchDay = new MatchDay(parser.getMatchdayInformation());

    }

    /**
     * Alternate Constructor that can be used to directly parse an URL
     * @param url the URL to parse
     * @throws IOException in case a Network error occurs
     */
    public League(String url) throws IOException{

        FootballHtmlParser parser = new FootballHtmlParser(url);
        this.leagueTable = new LeagueTable(parser.getLeagueInformation());
        this.matchDay = new MatchDay(parser.getMatchdayInformation());

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
     * Gets a list of teams in the league table
     * @return the array list of teams
     */
    public ArrayList<Team> getTeams() {
        return this.leagueTable.getTeams();
    }

    /**
     * Gets a list of matches in the matchday
     * @return the array list of matches
     */
    public ArrayList<Match> getMatches() {
        return this.matchDay.getMatches();
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
