import javax.swing.text.BadLocationException;
import javax.swing.text.html.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScraper {
    public static void main(String[] args) throws IOException, BadLocationException {
        ArrayList<URL> urls = new ArrayList<URL>();
        //Is URL?
        if(args[0].matches("^http\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$"))
            urls.add(new URL(args[0]));
        //Is file?
        else if (args[0].matches("^([\\w]\\:)(\\\\[a-z_\\-\\s0-9\\.]+)+\\.(txt)$")) {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            while (reader.ready()) {
                String readed = reader.readLine(); //Reading url from file
                if (readed.codePointAt(0) == 65279) //Byte Order Mark
                    readed = readed.substring(1);
                urls.add(new URL(readed));
            }
        }
        else {
            System.out.println("Bad path");
            return;
        }
        //Creating array of words
        String[] words = args[1].split(",");
        //Prepare document
        HTMLEditorKit kit = new HTMLEditorKit();
        for (URL url : urls) {
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
            Reader HTMLReader = new InputStreamReader(url.openConnection().getInputStream());
            kit.read(HTMLReader, doc, 0);
            System.out.println("--------------------");
            System.out.println("Url:" + url.toString());
            for (int i = 2; i < args.length; i++) {
                executeArg(args[i], url, words, doc);
            }
            System.out.println("--------------------");

            FileWriter writer = new FileWriter("d:\\b.txt");
            writer.write(doc.getText(0, doc.getLength()));
            writer.close();

        }
    }
    //Counting number of words
    public static Map<String, Integer> getCountMap(String[] input, HTMLDocument doc) throws BadLocationException {
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
    public static ArrayList<String> getSentences(String[] input, HTMLDocument doc) throws BadLocationException {
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
    public static void executeArg(String arg, URL url, String[] words, HTMLDocument doc) throws IOException, BadLocationException {
        if (arg.equals("-w")){
            Map<String, Integer> testMap = getCountMap(words, doc);
            System.out.println("Count of searching words in url:");
            for (Map.Entry<String, Integer> entry : testMap.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            return;
        }
        if (arg.equals("-c")){
            System.out.println("Count of character: " + doc.getText(0, doc.getLength()).replaceAll("\\s", "").length());
            return;
        }
        if (arg.equals("-e")){
            System.out.println("Start searching and extract sentences or something that looks like sentences. (The number of sentences are not the same as number of found words)");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            ArrayList<String> sentences= getSentences(words, doc);
            for (String sentence : sentences) {
                System.out.println(sentence);
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }
    }
}
