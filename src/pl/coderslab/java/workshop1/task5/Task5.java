package pl.coderslab.java.workshop1.task5;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Task5 {
    private static String[] EXCLUDED_WORDS = {"oraz", "ponieważ", "azaliż", "lecz", "albo", "gdyż"};
    private static ArrayList<String> excludedWords = new ArrayList<>();

    public static void main(String[] args) {
        excludedWords.addAll(Arrays.asList(EXCLUDED_WORDS));

        try {
            ArrayList<String> popularWords = getPopularWords("http://www.onet.pl");
            Path popularWordsFile = Paths.get("popular_words.txt");
            Files.write(popularWordsFile, popularWords);

            ArrayList<String> filteredWords = new ArrayList<>(popularWords);
            filteredWords.removeAll(excludedWords);
            Path filteredWordsFile = Paths.get("filtered_words.txt");
            Files.write(filteredWordsFile, filteredWords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<String> getPopularWords(String url) throws IOException {
        Connection connect = Jsoup.connect(url);
        ArrayList<String> result = new ArrayList<>();

        Document document = connect.get();
        ArrayList<Element> elements = document.select("span.title");
        for (Element elem : elements) {
            String text = elem.text();
            StringTokenizer tokenizer = new StringTokenizer(text, "\".,; !?:()'…[]");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().toLowerCase();
                if (token.length() >= 3 && !result.contains(token)) {
                    try {
                        Integer.parseInt(token);
                        Double.parseDouble(token);
                    } catch (NumberFormatException e) {
                        result.add(token);
                    }
                }
            }
        }
        return result;
    }
}
