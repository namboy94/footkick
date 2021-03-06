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
package net.namibsun.footkick.lib;

import org.junit.Test;
import java.io.IOException;
import net.namibsun.footkick.lib.structures.LeagueTable;
import net.namibsun.footkick.lib.scraper.Country;
import net.namibsun.footkick.lib.scraper.CountryLister;


/**
 * Basic test to make sure that the scraper works
 */
public class IntegrationTest {

    /**
     * Tests fetching the Bundesliga Scores
     * @throws IOException if a, well, IOException occurs
     */
    @Test
    public void testFetchinBundesligaResults() throws IOException {
        LeagueTable table = new LeagueTable("germany", "bundesliga");
        System.out.println(table.toMonoSpaceString());

        for (Country c : CountryLister.getCountries().getCountries()) {
            System.out.println(c.countryName);
        }
    }
}
