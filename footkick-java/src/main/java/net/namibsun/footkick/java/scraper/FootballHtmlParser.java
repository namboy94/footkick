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

package net.namibsun.footkick.java.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Parses www.livescore.com for current Football results
 */
public class FootballHtmlParser {

    Document htmlPage;

    /**
     * Constructor for the FootballHtmlParser class that initializes class variables used by
     * methods to get the league and matchday information
     * @param country The country to be parsed
     * @param league The league to be parsed
     * @throws IOException when the selected country/league could not be read.
     */
    public FootballHtmlParser(String country, String league) throws IOException {

        String livescoreUrl = "http://www.livescore.com/soccer/" + country +  "/" + league + "/";
        this.htmlPage = Jsoup.connect(livescoreUrl).get();

    }

    /**
     * Alternate Constructor that takes a URL directly
     * @param url the URL to parse
     * @throws IOException
     */
    public FootballHtmlParser(String url) throws IOException {
        this.htmlPage = Jsoup.connect(url).get();
    }

    /**
     * Gets the league information for an initialized FootballHtmlParser object
     * @return the league information
     */
    public ArrayList<Team> getLeagueInformation() {
        return FootballHtmlParser.scrapeLeagueInformation(this.htmlPage);
    }

    /**
     * Gets the matchday information for an initialized FootballHtmlParser object
     * @return the matchday information
     */
    public ArrayList<Match> getMatchdayInformation() {
        return FootballHtmlParser.scrapeMatchdayInformation(this.htmlPage);
    }

    /**
     * Scrapes a jsoup Document for league table information
     * @param jsoupDocument the jsoup Document to parse
     * @return an array list of parsed teams
     */
    public static ArrayList<Team> scrapeLeagueInformation(Document jsoupDocument) {

        Elements leagueTeams = jsoupDocument.select(".team");
        Elements leagueStats = jsoupDocument.select(".pts");

        //These Indices start at 1 and 8 to skip the title bar
        int teamPositionIndex = 1;
        int statsPositionIndex = 8;

        ArrayList<Team> teams = new ArrayList<>();

        while (teamPositionIndex < leagueTeams.size() && statsPositionIndex < leagueStats.size()) {
            Team team = new Team(leagueTeams.get(teamPositionIndex).text(),
                    leagueStats.get(statsPositionIndex).text(),
                    leagueStats.get(statsPositionIndex + 1).text(),
                    leagueStats.get(statsPositionIndex + 2).text(),
                    leagueStats.get(statsPositionIndex + 3).text(),
                    leagueStats.get(statsPositionIndex + 4).text(),
                    leagueStats.get(statsPositionIndex + 5).text(),
                    leagueStats.get(statsPositionIndex + 6).text(),
                    leagueStats.get(statsPositionIndex + 7).text());
            teams.add(team);
            teamPositionIndex++;
            statsPositionIndex += 8;
        }

        return teams;
    }

    /**
     * Scrapes a jsoup Document object for matchday information
     * @param jsoupDocument the jsoup Document to be parsed
     * @return an array list of parsed matches
     */
    public static ArrayList<Match> scrapeMatchdayInformation(Document jsoupDocument) {

        ArrayList<Match> matches = new ArrayList<>();

        Elements matchdayTeams = jsoupDocument.select(".ply");
        Elements matchdayTimes = jsoupDocument.select(".min");
        Elements matchdayScores = jsoupDocument.select(".sco");

        for (int i = 0; i < matchdayTeams.size(); i += 2) {
            Match match = new Match(matchdayTeams.get(i).text(),
                    matchdayTeams.get(i + 1).text(),
                    matchdayTimes.get(i / 2).text(),
                    matchdayScores.get(i / 2).text());
            matches.add(match);

        }

        return matches;
    }

    /**
     * Gets the league information for a given country and league in a static context
     * @param country the country to be searched for
     * @param league the league to be searched for
     * @return an array list of teams in the league table. If empty, the parsing failed
     */
    public static ArrayList<Team> getLeagueInformation(String country, String league) {

        String livescoreUrl = "http://www.livescore.com/soccer/" + country +  "/" + league + "/";

        try {
            Document jsoupDocument = Jsoup.connect(livescoreUrl).get();
            return FootballHtmlParser.scrapeLeagueInformation(jsoupDocument);
        } catch (IOException e) {
            return new ArrayList<>();
        }

    }

    /**
     * Gets the matchday information or a given country and league in a static context
     * @param country the country to be searched for
     * @param league the league to be searched for
     * @return an array list of matches on the current match day. If empty, the parsing failed
     */
    public static ArrayList<Match> getMatchdayInformation(String country, String league) {

        String livescoreUrl = "http://www.livescore.com/soccer/" + country +  "/" + league + "/";

        try {
            Document jsoupDocument = Jsoup.connect(livescoreUrl).get();
            return FootballHtmlParser.scrapeMatchdayInformation(jsoupDocument);
        } catch (IOException e) {
            return new ArrayList<>();
        }

    }
}
