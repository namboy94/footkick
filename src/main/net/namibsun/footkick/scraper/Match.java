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

/**
 * Class that models a single football match
 */
public class Match {

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

}