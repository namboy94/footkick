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

import net.namibsun.footkick.structures.LeagueTable;

/**
 * The Main Java Class
 */
public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length != 3){
            System.out.println("Invalid amount of parameters");
            System.out.println("Usage: <country>, <league>, [table|matchday]");
        }
        else if (!args[2].toLowerCase().equals("matchday") && !args[2].toLowerCase().equals("table")) {
            System.out.println("Invalid mode");
            System.out.println("Please use either 'table' or 'matchday'");
        }
        else {
            String country = args[0];
            String league = args[1];
            String mode = args[2];

            if (mode.equals("table")) {
                LeagueTable leagueTable = new LeagueTable(country, league);
                System.out.println(leagueTable.toMonoSpaceString());
            }
        }


    }

}