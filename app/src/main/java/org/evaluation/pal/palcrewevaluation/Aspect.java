package org.evaluation.pal.palcrewevaluation;

import android.nfc.cardemulation.CardEmulation;

/**
 * Created by carlo on 2/25/2017.
 * This class represents the rows inside Dimensions
 */

public class Aspect extends Row{
    private int score, maxScore;
    private boolean isAnswered;
    private AspectType aspectType;
    private Category category;

    public Aspect(String aspectName, int maxScore, AspectType aspectType, Category category){
        rowName = aspectName;
        this.maxScore = maxScore;
        this.score = 0;
        this.isAnswered = false;
        this.aspectType = aspectType;
        this.category = category;
    }

    public boolean isAnswered(){
        return isAnswered;
    }
    /*Used by UI whenever user interacts with the radio buttons*/
    public void setScore(int score) throws Exception {
        if(score <= maxScore)
            this.score = score;
        else
            throw new Exception("Invalid Score: score cannot be greater than maxScore.");
    }

    public int getScore(){
        return score;
    }

    public int getMaxScore(){
        return maxScore;
    }

    public AspectType getAspectType(){
        return aspectType;
    }

    public Category getCategory(){
        return category;
    }


}
