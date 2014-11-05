import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Андрей on 04.11.2014.
 */
public class PageScraper extends Scraper implements IGetPage{
    private ScrapedPage page = new ScrapedPage();

    public PageScraper(URL url, String[] words, String[] args) throws IOException, BadLocationException {
        HTMLDocument doc = scrap(url);
        page.setUrl(url.toString());
        for (String arg : args) {
            executeArg(arg, words, doc);
        }
    }
    //Counting number of words in document
    private Map<String, Integer> getCountMap(String[] input, HTMLDocument doc) throws BadLocationException {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        String docText = doc.getText(0, doc.getLength());
        for (String element : input) {
            Pattern pattern = Pattern.compile(element, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(docText);
            int count = 0;
            while(matcher.find())
            {
                count++;
            }
            result.put(element, count);
            count = 0;
        }
        return result;
    }

    //Extract sentences or something that looks like sentences
    private ArrayList<String> getSentences(String[] input, HTMLDocument doc) throws BadLocationException {
        ArrayList<String> result = new ArrayList<String>();
        for (String word : input) {
            result.add("Sentences that contains " + word);
            Pattern pattern = Pattern.compile("([^.\\n]*\\b(" + word + ")\\b)[^.\\n]+\\.?", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(doc.getText(0, doc.getLength()));
            while (matcher.find()) {
                result.add("    "+matcher.group());
            }
        }
        return result;
    }

    private void executeArg(String arg, String[] words, HTMLDocument doc) throws IOException, BadLocationException {
        if (arg.equals("-w")){
            page.setCountWords(getCountMap(words, doc));
            return;
        }
        if (arg.equals("-c")){
            page.setCountCharacters(doc.getText(0, doc.getLength()).replaceAll("\\s", "").length());
            return;
        }
        if (arg.equals("-e")){
            page.setSentences(getSentences(words, doc));
            return;
        }
    }
    @Override
    public ScrapedPage getPage() {
        return page;
    }
}
