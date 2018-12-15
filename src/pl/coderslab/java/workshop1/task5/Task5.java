package pl.coderslab.java.workshop1.task5;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Task5 {
    private static String[] excludedWords = {"oraz", "ponieważ", "azaliż", "lecz", "albo", "gdyż"};

    public static void main(String[] args) {
        try {
            ArrayList<String> popularWords = getPopularWords("http://www.onet.pl");
            Path popularWordsFile = Paths.get("popular_words.txt");
            Files.write(popularWordsFile, popularWords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<String> getPopularWords(String url) throws IOException {
        Connection connect = Jsoup.connect(url);
        ArrayList<String> result = new ArrayList<>();

        Document document = connect.get();
        Elements elements = document.select("span.title");
        for (Element elem : elements) {
            String text = elem.text().replaceAll("\"", "");
            StringTokenizer tokenizer = new StringTokenizer(text, ".,; !?:()'…[]");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().toLowerCase();
                if (token.length() >= 3 && !result.contains(token)) {
                    try {
                        Integer.parseInt(token);
                    } catch (NumberFormatException e) {
                        result.add(token);
                    }
                }
            }
        }
        return result;
    }
}
