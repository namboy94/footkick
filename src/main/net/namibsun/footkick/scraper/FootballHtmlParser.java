/*
Copyright 2016 Hermann Krumrey

This file is part of jfworks.

    jfworks is a collection of classes and methods that are designed to make
    cross-framework GUI design easier by standardizing a lot of framework-specific
    functionality with a unified interface.

    jfworks is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jfworks is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with jfworks. If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.footkick.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Parses www.livescore.com for current Football results
 */
public class FootballHtmlParser {

    public static ArrayList<Team> getLeagueInformation(String country, String league) {

        String livescoreUrl = "http://www.livescore.com/soccer/" + country +  "/" + league + "/";

        Document jsoupDocument = null;
        try {
            jsoupDocument = Jsoup.connect(livescoreUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert jsoupDocument != null;
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
                                 leagueStats.get(statsPositionIndex + 7).text(),
                                 "" + teamPositionIndex);
            teams.add(team);
            teamPositionIndex++;
            statsPositionIndex += 8;
        }

        return teams;

    }

    public static ArrayList<Match> getMatchdayInformation(String country, String league) {

        ArrayList<Match> matches = new ArrayList<>();
        String livescoreUrl = "http://www.livescore.com/soccer/" + country +  "/" + league + "/";

        Document jsoupDocument = null;
        try {
            jsoupDocument = Jsoup.connect(livescoreUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert jsoupDocument != null;
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
}
