package axi.executor.tester.xlsprove;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlReader {
    public static void main(String[] args) throws IOException {
    	File fileHttpSource = new File("wrk\\htmlFileExample.html");  
        //Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
        Document doc = Jsoup.parse(fileHttpSource, null);
        log(doc.title());

        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            log("%s\n\t%s", headline.attr("title"), headline.absUrl("href"));
        }
    }

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }
}
