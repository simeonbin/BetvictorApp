import java.time.LocalDateTime;

public class ΗistoryOfComputations implements Comparable<ΗistoryOfComputations> {


    LocalDateTime ldtCalculation;
    Long tsCalculation;
    String wordMostFrequent;
    Integer numOfOccur;
    Double averagePSize;
    Double averagePElapsedTime; //in Nanos
    Long timeElapsedTotalProcessingText; // in Millis

    public LocalDateTime getLdtCalculation() {
        return ldtCalculation;
    }

    public Long getTsCalculation() {
        return tsCalculation;
    }

    public String getWordMostFrequent() {
        return wordMostFrequent;
    }

    public Integer getNumOfOccur() {
        return numOfOccur;
    }

    public Double getAveragePSize() {
        return averagePSize;
    }

    public Double getAveragePElapsedTime() {
        return averagePElapsedTime;
    }

    public Long getTimeElapsedTotalProcessingText() {
        return timeElapsedTotalProcessingText;
    }

    public void setLdtCalculation(LocalDateTime ldtCalculation) {
        this.ldtCalculation = ldtCalculation;
    }

    public void setTsCalculation(Long tsCalculation) {
        this.tsCalculation = tsCalculation;
    }

    public void setWordMostFrequent(String wordMostFrequent) {
        this.wordMostFrequent = wordMostFrequent;
    }

    public void setNumOfOccur(Integer numOfOccur) {
        this.numOfOccur = numOfOccur;
    }

    public void setAveragePSize(Double averagePSize) {
        this.averagePSize = averagePSize;
    }

    public void setAveragePElapsedTime(Double averagePElapsedTime) {
        this.averagePElapsedTime = averagePElapsedTime;
    }

    public void setTimeElapsedTotalProcessingText(Long timeElapsedTotalProcessingText) {
        this.timeElapsedTotalProcessingText = timeElapsedTotalProcessingText;
    }

    @Override
    public String toString() {
        return "ΗistoryOfComputations{" +
                "ldtCalculation=" + ldtCalculation +
                ", tsCalculation=" + tsCalculation +
                ", wordMostFrequent='" + wordMostFrequent + '\'' +
                ", numOfOccur=" + numOfOccur +
                ", averagePSize=" + averagePSize +
                ", averagePElapsedTime=" + averagePElapsedTime +
                ", timeElapsedTotalProcessingText=" + timeElapsedTotalProcessingText +
                '}';
    }

    @Override
    public int compareTo(ΗistoryOfComputations o) {
        return getTsCalculation().compareTo(o.getTsCalculation());
    }

}
