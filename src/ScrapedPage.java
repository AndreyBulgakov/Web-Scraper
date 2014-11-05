import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Андрей on 04.11.2014.
 */
public class ScrapedPage {
    private String url;
    private int countCharacters;
    private ArrayList<String> sentences;
    private Map<String, Integer> countWords;

    public void setCountWords(Map<String, Integer> countWords) {
        this.countWords = countWords;
    }

    public void setSentences(ArrayList<String> sentences) {
        this.sentences = sentences;
    }

    public void setCountCharacters(int countCharacters) {
        this.countCharacters = countCharacters;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public int getCountCharacters() {
        return countCharacters;
    }

    public ArrayList<String> getSentences() {
        return sentences;
    }

    public Map<String, Integer> getCountWords() {
        return countWords;
    }

    @Override
    public String toString() {
        String words = "";
        for (Map.Entry<String, Integer> entry : countWords.entrySet()) {
            words += entry.getKey() + " - " + entry.getValue() + "\n";
        }
        String extractedSentences  = "";
        for (String sentence : sentences) {
            extractedSentences += sentence + "\n";
        }
        return "--------------------"
                + "\n"
                + "URL:" + url + "\n"
                + "Count of character: " + countCharacters
                + "\n"
                + "Count of searching words in url: \n" + words
                + "Extracted sentences or something that looks like sentences. (The number of sentences are not the same as number of found words)"
                + "\n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                + "\n"
                + extractedSentences
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                + "\n"
                + "--------------------";

    }
}
