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

        String url = "http://www.livescore.com/soccer/germany/bundesliga/";
        Document document = Jsoup.connect(url).get();

        String question = document.select(".team").text();
        System.out.println("Question: " + question);

        Elements answerers = document.select(".pts");
        for (Element answerer : answerers) {
            System.out.println("Answerer: " + answerer.text());
        }
    }

}
