import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BetvictorHelper {
    static String currentDirectory = "";
    HttpURLConnection conn = null;

    public String createJsonAndCalculationsFiles() throws IOException {
        String dirName;
        dirName = System.getProperty("user.dir");

        java.nio.file.Path theDummyTextJsonPath = Paths.get(currentDirectory, "dummytext.json");

        try {
            Files.createFile(theDummyTextJsonPath);
        } catch (FileAlreadyExistsException ignored)
        { }

        currentDirectory = System.getProperty("user.dir");
        java.nio.file.Path thePath = Paths.get(currentDirectory, "dummytext_calculations.txt");

        try {
            Files.createFile(thePath);
        } catch (FileAlreadyExistsException ignored)
        { }
        return dirName;
    }


    public Elements cleanTagsFromParagraphs(String htmlTextParagraphs) {
        Document doc = Jsoup.parseBodyFragment(htmlTextParagraphs);
        doc.outputSettings().prettyPrint(false);
        Element bodyParagraph = doc.body();
        return bodyParagraph.getElementsByTag("p");
    }

}
