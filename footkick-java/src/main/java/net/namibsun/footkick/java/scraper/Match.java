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

/**
 * Class that models a single football match
 */
public class Match implements LeagueData {

    public String homeTeam;
    public String awayTeam;
    public String time;
    public String score;

    /**
     * Constructor that stores the match information in public variables
     * @param homeTeam the home team of the match
     * @param awayTeam the away team of the match
     * @param time the time left until the match starts or the current minute if the match is in progress
     * @param score the current score of the match
     */
    Match(String homeTeam, String awayTeam, String time, String score) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.time = time;
        this.score = score;
    }

    /**
     * Turns the data of the Match object into a String array for easy entry into tables
     * @return the data as a String array
     */
    public String[] toStringArray() {
        return new String[] {
                this.time, this.homeTeam, this.score, this.awayTeam

        };
    }

}