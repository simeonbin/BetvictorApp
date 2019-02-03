import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class CountWordsInAString {
    static Integer numOfOccurrences = 0;

    public static int countWordsUsingStringTokenizer(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return 0;
        }

        StringTokenizer tokens = new StringTokenizer(sentence); return tokens.countTokens();
    }

    public static int countWordsUsingSplit(String input) {

        if (input == null || input.isEmpty()) {
            return 0;
        }
        String[] words = input.split("\\s+");
        return words.length;
    }

    public static ΗistoryOfComputations readDummyTextCalcParams (String strCalc) {

        String[] arrDummyTCalc = new String[10];
        ΗistoryOfComputations hOComp = new ΗistoryOfComputations();

        arrDummyTCalc = strCalc.trim().split(",");

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse(arrDummyTCalc[0], formatter);
        hOComp.setLdtCalculation(localDateTime);

        Long timestampDateTime = Long.valueOf(arrDummyTCalc[1]);
        hOComp.setTsCalculation(timestampDateTime);

        hOComp.setWordMostFrequent(arrDummyTCalc[2]);

        Integer numOfOccur = Integer.valueOf(arrDummyTCalc[3]);
        hOComp.setNumOfOccur(numOfOccur);

        Double avgPSize = Double.valueOf(arrDummyTCalc[4]);
        hOComp.setAveragePSize(avgPSize);

        Double avgPElapsedT = Double.valueOf(arrDummyTCalc[5]);
        hOComp.setAveragePElapsedTime(avgPElapsedT);

        Long totalProcessingT = Long.valueOf(arrDummyTCalc[6]);
        hOComp.setTimeElapsedTotalProcessingText(totalProcessingT);

        return hOComp;
    }

    public static double averageParaSize(ArrayList<Integer> countWordsInPara)
    {
        double average = 0.0;
        for (int i = 0; i < countWordsInPara.size(); i++)  {
            average += countWordsInPara.get(i);
        }
        return average/countWordsInPara.size();
    }
    public static double averageElapsedTimePara(ArrayList<Long> countWordsInPara)
    {
        double average = 0.0;
        for (int i = 0; i < countWordsInPara.size(); i++)  {
            average += countWordsInPara.get(i);
        }
        return average/countWordsInPara.size();
    }
    public static String wordMostFrequentInDummyText(String text) {

        String[] words = text.split("\\s+");
        HashMap<String, Integer> occurrences = new HashMap<String, Integer>();

        for (String word: words) {
            int value = 0;
            if  (occurrences.containsKey(word)) {
                value = occurrences.get(word);
            }
            occurrences.put(word, value + 1);
        }

        Map.Entry<String,Integer> tempResult = occurrences.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .findFirst().get();

        numOfOccurrences = (Integer) ( ((Map.Entry) tempResult).getValue() );
        return (String) ( ((Map.Entry) tempResult).getKey() );

    }


}
