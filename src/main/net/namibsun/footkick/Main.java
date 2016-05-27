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

package net.namibsun.footkick;

import net.namibsun.footkick.scraper.*;

import java.util.ArrayList;

/**
 * The Main Java Class
 */
public class Main {

    public static void main(String[] args) throws Exception {

        // Gets Bundesliga
        ArrayList<Team> bundesligaTeams = FootballHtmlParser.getLeagueInformation("germany", "bundesliga");
        ArrayList<Match> bundesligaMatches = FootballHtmlParser.getMatchdayInformation("germany", "bundesliga");

        System.out.println(bundesligaMatches.get(3).homeTeam);
        System.out.println(bundesligaTeams.get(4).position);
        System.out.println(bundesligaTeams.get(17).teamName);


    }

}