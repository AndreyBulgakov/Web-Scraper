import javax.swing.text.BadLocationException;
import java.io.*;
import java.net.URL;
import java.util.*;

public class WebScraper {
    private ArrayList<ScrapedPage> scrapers = new ArrayList<ScrapedPage>();

    public WebScraper(String[] args) throws IOException, BadLocationException {
        ArrayList<URL> urls = new ArrayList<URL>();
        //Is URL?
        if(args[0].matches("^http\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$"))
            urls.add(new URL(args[0]));
        //Is file?
        else if (args[0].matches("^([\\w]\\:)(\\\\[a-z_\\-\\s0-9\\.]+)+\\.(txt)$")) {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            while (reader.ready()) {
                String read = reader.readLine(); //Reading url from file
                if (read.codePointAt(0) == 65279) //Byte Order Mark
                    read = read.substring(1);
                urls.add(new URL(read));
            }
        }
        else {
            System.out.println("Bad path");
            return;
        }
        //Creating array of words
        String[] words = args[1].split(",");
        //Executable arguments
        String[] exargs = new String[args.length - 2];
        for (int i = 0; i < exargs.length; i++) {
            exargs[i] = args[i+2];
        }
        for (URL url: urls) {
            PageScraper scraper = new PageScraper(url, words, exargs);
            scrapers.add(scraper.getPage());
        }
    }

    @Override
    public String toString() {
        String out = "";
        for (ScrapedPage scraped : scrapers) {
            out += scraped.toString() + "\n";
        }
        return out;
    }
}
