import javax.swing.text.BadLocationException;
import javax.swing.text.html.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScraperMain {
    public static void main(String[] args) throws IOException, BadLocationException {
        WebScraper scraper = new WebScraper(args);
        System.out.println(scraper);
    }
}
