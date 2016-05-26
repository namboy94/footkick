package net.namibsun.footkick;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * The Main Java Class
 */
public class Main {

    public static void main(String[] args) throws Exception{

        String livescoreUrl = "http://www.livescore.com/soccer/germany/bundesliga/";
        Document jsoupDocument = Jsoup.connect(livescoreUrl).get();

        Elements leagueTeams = jsoupDocument.select(".team");
        Elements leagueScorings = jsoupDocument.select(".pts");
        Elements matchdayTeam = jsoupDocument.select(".ply");
        Elements matchdayTimes = jsoupDocument.select(".min");
        Elements matchdayScores = jsoupDocument.select(".sco");

    }

}
