package net.namibsun.footkick;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * The Main Java Class
 */
public class Main {

    public static void main(String[] args) throws Exception{

        String livescoreUrl = "http://www.livescore.com/soccer/germany/bundesliga/";
        Document jsoupDocument = Jsoup.connect(livescoreUrl).get();

        Elements leagueTeams = jsoupDocument.select(".team");
        Elements leagueStats = jsoupDocument.select(".pts");
        Elements matchdayTeam = jsoupDocument.select(".ply");
        Elements matchdayTimes = jsoupDocument.select(".min");
        Elements matchdayScores = jsoupDocument.select(".sco");

        //These Indices start at 1 and 8 to skip the title bar
        int teamPositionIndex = 1;
        int statsPositionIndex = 7;

        while(teamPositionIndex < leagueTeams.size() && statsPositionIndex < leagueStats.size()) {
            Team team = new Team(leagueStats.get(statsPositionIndex).text(),
                                 leagueStats.get(statsPositionIndex + 1).text(),
                                 leagueStats.get(statsPositionIndex + 2).text(),
                                 leagueStats.get(statsPositionIndex + 3).text(),
                                 leagueStats.get(statsPositionIndex + 4).text(),
                                 leagueStats.get(statsPositionIndex + 5).text(),
                                 leagueStats.get(statsPositionIndex + 6).text(),
                                 leagueStats.get(statsPositionIndex + 7).text(),
                                 leagueStats.get(statsPositionIndex + 8).text(),
                                 "" + teamPositionIndex);
            System.out.println(leagueTeams.get(teamPositionIndex).text());
            System.out.println(team.goalDifference);
            teamPositionIndex++;
            statsPositionIndex += 8;
        }

    }

    private static class Team {

        String teamName;
        String matches;
        String wins;
        String draws;
        String losses;
        String goalsFor;
        String goalsAgainst;
        String goalDifference;
        String points;
        String position;

        Team(String teamName,
             String matches,
             String wins,
             String draws,
             String losses,
             String goalsFor,
             String goalsAgainst,
             String goalDifference,
             String points,
             String position) {

            this.teamName = teamName;
            this.matches = matches;
            this.wins = wins;
            this.draws = draws;
            this.losses = losses;
            this.goalsFor = goalsFor;
            this.goalsAgainst = goalsAgainst;
            this.goalDifference = goalDifference;
            this.points = points;
            this.position = position;

        }

    }

}