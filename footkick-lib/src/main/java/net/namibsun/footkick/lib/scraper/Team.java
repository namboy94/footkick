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
 * Class that models a team in a league
 */
public class Team implements net.namibsun.footkick.lib.scraper.interfaces.LeagueData {

    @SuppressWarnings("WeakerAccess")
    public String position;
    public String teamName;
    public String matches;
    public String wins;
    public String draws;
    public String losses;
    public String goalsFor;
    public String goalsAgainst;
    public String goalDifference;
    public String points;

    /**
     * Constructor that Creates a new Team with the necessary attributes
     * @param position the team's current position
     * @param teamName the team's name
     * @param matches how many matches the team has played so far
     * @param wins how often the team has won
     * @param draws how often the team has drawn
     * @param losses how often the team has lost
     * @param goalsFor how often the team has scored
     * @param goalsAgainst how often the team has conceded a goal
     * @param goalDifference the goal difference of the team
     * @param points how many points the team has earned so far
     */
    Team(String position,
         String teamName,
         String matches,
         String wins,
         String draws,
         String losses,
         String goalsFor,
         String goalsAgainst,
         String goalDifference,
         String points) {

        this.position = position;
        this.teamName = teamName;
        this.matches = matches;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.goalDifference = goalDifference;
        this.points = points;

    }

    /**
     * Turns the data of the Team object into a String array for easy entry into tables
     * @return the data as a String array
     */
    public String[] toStringArray() {
        return new String[] {
            this.position, this.teamName, this.matches, this.wins, this.draws, this.losses,
            this.goalsFor, this.goalsAgainst, this.goalDifference, this.points
        };
    }

}