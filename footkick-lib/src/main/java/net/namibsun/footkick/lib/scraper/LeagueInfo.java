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

package net.namibsun.footkick.lib.scraper;

/**
 * A class that stores the information about a league, like the league name and URL
 */
public class LeagueInfo {

    public String leagueUrl;
    public String leagueName;

    /**
     * Stores the league's name and its URL
     * @param leagueName the league's name
     * @param leagueUrl the league's URL
     */
    public LeagueInfo(String leagueName, String leagueUrl) {
        this.leagueName = leagueName;
        this.leagueUrl = "http://www.livescore.com" + leagueUrl;
    }

}
