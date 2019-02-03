import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/betvictor")
public class Betvictor  {
    public static final int NUM_PARA_REQUESTED = 10;
    public static final int NUM_WORDS_IN_PARAGRAPH = 100;
    int start_p; int end_p; int count_min_w; int count_max_w;

    static  List<String> listHistoryOfComputationsAsString = new ArrayList<>();
    List<ΗistoryOfComputations> listHistoryOfComputations = new ArrayList<>();
    List<String> listDummyTextCalc = new ArrayList<>();

    HttpURLConnection conn = null;

    public static final Logger LOGGER = Logger.getLogger(Betvictor.class.getName());

    public Betvictor() { }

    BetvictorHelper bVH = new BetvictorHelper();

    @GET
    @Path("/text")
    @Produces("text/plain")
    public String getDummyText(@QueryParam("p_start") int p_start, @QueryParam("p_end") int p_end, @QueryParam("w_count_min") int w_count_min,
                                    @QueryParam("w_count_max") int w_count_max) throws IOException
    {

        StringBuffer response= new StringBuffer();  String dirName = "";
        BufferedReader br = null; Writer outJsonFile = null; Writer calculationsFile = null;
        List<GenDummyText> listGenDummyText = new ArrayList<>();  GenDummyText dummyTextPara;

        int iParaList = 0; int jPara = 0; int numTotalPara = 0; int numOfOccur = 0;
        double averagePSize = 0; double averagePElapsedTime = 0; double averagePSize_rounded = 0; double averagePElapsedTime_rounded = 0;
        String htmlTextParagraphs = ""; StringBuilder totalDummyText = new StringBuilder();
        String paraDummyText = ""; String output; String historyCalcString = ""; String totalDummyTextStr = "";
       // String[][] tableOfParagraphsInSeparateCells = new String[NUM_PARA_REQUESTED ][NUM_WORDS_IN_PARAGRAPH];
        String[] tableOfParagraphs = new String[NUM_PARA_REQUESTED ];
        ArrayList<Integer> countWordsInPara = new ArrayList<Integer>(); ArrayList<Long> timeElapsedEachParagraph = new ArrayList<Long>();
        String wordMostFreq = ""; long timeElapsedTotalProcessingText = 0; long timeElapsedParagraph = 0;


        start_p = p_start; end_p = p_end;
        count_min_w = w_count_min;  count_max_w = w_count_max;

        showLoggerMessages();
        dirName = bVH.createJsonAndCalculationsFiles();

        for (int i=0; i < tableOfParagraphs.length; i++) {
            tableOfParagraphs[i] = "";
        }

        for (int iPara = p_start; iPara <= p_end; iPara++) {

           try {
               getRandomText(count_min_w, count_max_w, iPara);

               response = new StringBuffer();
               br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

               System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    response.append(output);
                    System.out.println(output);
                }
                System.out.println(response.toString());

               try {
                   Files.write ( Paths.get(dirName, "dummytext.json"), response.toString().getBytes(), StandardOpenOption.APPEND );
               } catch (IOException e)
               //exception handling goes here
               { e.printStackTrace(); }

               Instant startTotalProcessingText = Instant.now(); //Start the Stopwatch of Total Paragraph processing time
                // This is the Response, in all its glory. Includes all Paragraphs of Random Text
                dummyTextPara = JsonToJavaDummyTextConverter.fromJson(response.toString());
                listGenDummyText.add(dummyTextPara);
                // Look at the "text_out" Key and Value of the Response
                htmlTextParagraphs= dummyTextPara.getText_out();

                // Clean the Tags from Paragraphs of "text_out" Key of Random Text Response

                Elements paragraphs = bVH.cleanTagsFromParagraphs(htmlTextParagraphs);

                totalDummyText = new StringBuilder();

               for (Element paragraph : paragraphs) {
                    //Start the Stopwatch of a paragraph processing time
                    Instant startProcessingPara = Instant.now();

                    System.out.println(paragraph.text());
                    //tableOfParagraphsInSeparateCells[iParaList][jPara++] = paragraph.text();
                    tableOfParagraphs[iParaList] += paragraph.text();
                    // Take out periods ('.') from paragraphs
                    paraDummyText = paragraph.text().replace(".", " ");
                    totalDummyText.append(paraDummyText);

                    countWordsInPara.add(CountWordsInAString.countWordsUsingSplit(paraDummyText));

                    //STOP the Stopwatch of this Paragraph processing time
                    Instant finishProcessingPara = Instant.now();
                    // Duration of this paragraph processing time in millis.
                    timeElapsedParagraph = Duration.between(startProcessingPara, finishProcessingPara).toNanos();
                    // timeElapsedEachParagraph in Nanos
                    timeElapsedEachParagraph.add(timeElapsedParagraph);
                }

                //STOP the Stopwatch of Total Paragraph processing time
               Instant finishTotalProcessingText = Instant.now();
               // Duration of Total paragraph processing time in millis.
               timeElapsedTotalProcessingText = Duration.between(startTotalProcessingText, finishTotalProcessingText).toMillis();

                br.close();

                iParaList++;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
           totalDummyTextStr =  totalDummyText.toString();
           averagePSize = CountWordsInAString.averageParaSize(countWordsInPara);
           averagePSize_rounded = (double) Math.round(averagePSize * 100) / 100;
           wordMostFreq = CountWordsInAString.wordMostFrequentInDummyText(totalDummyTextStr);
           numOfOccur = CountWordsInAString.numOfOccurrences;
           //Average Paragraph Processing time in Nanos {10^(-9) seconds}
           averagePElapsedTime = CountWordsInAString.averageElapsedTimePara(timeElapsedEachParagraph);
           averagePElapsedTime_rounded = (double) Math.round(averagePElapsedTime * 100) / 100;

           conn.disconnect();

        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();

        historyCalcString = String.format("%s,%d,%s,%d,%s,%s,%d\n", localDateTime.toString(), timeStampSeconds, wordMostFreq, numOfOccur, averagePSize_rounded,
                                          averagePElapsedTime_rounded, timeElapsedTotalProcessingText);

        try {
            Files.write ( Paths.get(bVH.currentDirectory, "dummytext_calculations.txt"),
                         historyCalcString.getBytes(), StandardOpenOption.APPEND );
           } catch (IOException e)
              //exception handling goes here
              {}

        LOGGER.info("Calculations String: " + historyCalcString);

        return String.format("Hello Betvictor %d %d %d %d", p_start, p_end, w_count_min, w_count_max);

    }


    @GET
    @Path("/history")
    @Produces("text/plain")
    public String getHistoryOfComputations() {

        Betvictor BV = new Betvictor();

        listHistoryOfComputations.clear();
        listDummyTextCalc.clear();

        try {
        List<String> listDummyTextCalc =  Files.readAllLines ( Paths.get(bVH.currentDirectory, "dummytext_calculations.txt")); //, historyCalcString.getBytes(), StandardOpenOption.APPEND );

            for (int iDummyTCalc=0; iDummyTCalc < listDummyTextCalc.size(); iDummyTCalc++) {

                ΗistoryOfComputations hOfComp  = CountWordsInAString.readDummyTextCalcParams(listDummyTextCalc.get(iDummyTCalc));

                  listHistoryOfComputations.add(hOfComp);
            }
        } catch (IOException e)
        //exception handling goes here
        {e.printStackTrace(); }

        Collections.reverse(listHistoryOfComputations);

       for (int iHistory = 0; iHistory < listHistoryOfComputations.size(); iHistory++) {
           LOGGER.info("Computation: " + String.valueOf(iHistory) + ": " + listHistoryOfComputations.get(iHistory).toString() );
       }

       return String.format("Hello Betvictor History");
    }


    public void showLoggerMessages() {

        System.out.println("%h: " + System.getProperty("user.home"));
        System.err.println("%h: " + System.getProperty("user.home"));
        Handler consoleHandler = null;
        consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.ALL);

        LOGGER.info("%h: " + System.getProperty("user.home"));
        LOGGER.setUseParentHandlers(false);
    }
    // public void getRandomText(@QueryParam("w_count_min") int w_count_min, @QueryParam("w_count_max") int w_count_max, int iPara) throws IOException {

    public void getRandomText(int w_count_min, int w_count_max, int iPara) throws IOException {

        String pParagraphNum = new StringBuilder().append("p-").append(iPara).toString();
        //       URL url = new URL("http://www.randomtext.me/api/giberish/pPara/25-25");

        String wWordsMinOfParagraph = new StringBuilder().append("/").append(w_count_min).append("-").append(w_count_max).toString();

        StringBuilder urlBuilder = new StringBuilder("http://www.randomtext.me/api/giberish/");
        urlBuilder.append(pParagraphNum);
        // urlBuilder.append("/25-25");
        urlBuilder.append(wWordsMinOfParagraph);

        //       URL url = new URL("http://www.randomtext.me/api/giberish/pPara/25-25");
        URL url = new URL(urlBuilder.toString());

        conn = (HttpURLConnection) url.openConnection();
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
    }

}

