package core.clusterer;

public class CustomLogScoreCalculator {


    public CustomLogScoreCalculator() {
    }

    public double[] calculate(ApacheLog apacheLog) {
        double[] score = new double[2];

        if (apacheLog.getHttpMethod().equalsIgnoreCase("POST")) {
            score[0] = 0;
            score[1] = 100;
        } else {
            score[0] = 100;
            score[1] = 0;
        }

        score[0] += apacheLog.findOccurrencesNumberOfSpecialCharacters();
        score[0] += Math.pow(2, apacheLog.findOccurrencesNumberOfSQLKeywords());
        score[0] += apacheLog.findOccurrencesNumberOfWhiteSpaces();
        score[0] += Math.sqrt(apacheLog.getPayloadLength());


        return score;
    }
}
