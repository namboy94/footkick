package net.namibsun.footkick.java.scraper;

/**
 * A class that stores the information about a league, like the league name and URL
 */
public class LeagueInfo {

    public String leagueUrl;
    public String leagueName;

    public LeagueInfo(String leagueName, String leagueUrl) {
        this.leagueName = leagueName;
        this.leagueUrl = "http://www.livescore.com" + leagueUrl;
    }

}
