package net.namibsun.footkick.java.scraper;

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
