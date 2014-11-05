import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * Created by Андрей on 05.11.2014.
 */
public abstract class Scraper {
    protected HTMLDocument scrap(URL url) throws IOException, BadLocationException{
        //Prepare document
        HTMLEditorKit kit = new HTMLEditorKit();
        HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
        doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
        Reader HTMLReader = new InputStreamReader(url.openConnection().getInputStream());
        kit.read(HTMLReader, doc, 0);
        return doc;
    }
}
