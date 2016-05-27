package net.namibsun.footkick;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * The Main Java Class
 */
public class Main {

    public static void main(String[] args) throws Exception{

        String livescoreUrl = "http://www.livescore.com/soccer/germany/bundesliga/";
        Document jsoupDocument = Jsoup.connect(livescoreUrl).get();

        Elements leagueTeams = jsoupDocument.select(".team");
        Elements leagueStats = jsoupDocument.select(".pts");
        Elements matchdayTeams = jsoupDocument.select(".ply");
        Elements matchdayTimes = jsoupDocument.select(".min");
        Elements matchdayScores = jsoupDocument.select(".sco");

        //These Indices start at 1 and 8 to skip the title bar
        int teamPositionIndex = 1;
        int statsPositionIndex = 7;

        ArrayList<Team> teams = new ArrayList<Team>();
        ArrayList<Match> matches = new ArrayList<Match>();

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
            teams.add(team);
            teamPositionIndex++;
            statsPositionIndex += 8;
        }

        for (int i = 0; i < matchdayTeams.size(); i += 2) {
            Match match = new Match(matchdayTeams.get(i).text(),
                                    matchdayTeams.get(i + 1).text(),
                                    matchdayTimes.get(i / 2).text(),
                                    matchdayScores.get(i / 2).text());
            matches.add(match);

        }

        for (Match match : matches) {
            System.out.println(match.awayTeam + match.homeTeam + match.score + match.time);
        }

    }

    private static class Match {

        String homeTeam;
        String awayTeam;
        String time;
        String score;

        Match(String homeTeam, String awayTeam, String time, String score) {
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.time = time;
            this.score = score;
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