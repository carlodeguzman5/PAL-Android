package org.evaluation.pal.palcrewevaluation;

import android.nfc.cardemulation.CardEmulation;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

    public RadioGroup generateRadioGroup(FragmentActivity context, RadioType radioType) throws Exception {
        RadioGroup rowRadioGroup = new RadioGroup(context);

        rowRadioGroup.setOrientation(RadioGroup.HORIZONTAL);
        rowRadioGroup.setRight(0);

        if(radioType == RadioType.ONE){
            RadioButton zeroButton = new RadioButton(context);
            zeroButton.setText("0");
            zeroButton.setOnClickListener(new RadioButtonClickListener(0));

            RadioButton oneButton = new RadioButton(context);
            oneButton.setText("1");
            oneButton.setOnClickListener(new RadioButtonClickListener(1));

            rowRadioGroup.addView(zeroButton);
            rowRadioGroup.addView(oneButton);
        }
        else if(radioType == RadioType.THREE){
            RadioButton zeroButton = new RadioButton(context);
            zeroButton.setText("0");
            zeroButton.setOnClickListener(new RadioButtonClickListener(0));

            RadioButton oneButton = new RadioButton(context);
            oneButton.setText("1");
            oneButton.setOnClickListener(new RadioButtonClickListener(1));

            RadioButton twoButton = new RadioButton(context);
            twoButton.setText("2");
            twoButton.setOnClickListener(new RadioButtonClickListener(2));

            RadioButton threeButton = new RadioButton(context);
            threeButton.setText("3");
            threeButton.setOnClickListener(new RadioButtonClickListener(3));

            rowRadioGroup.addView(zeroButton);
            rowRadioGroup.addView(oneButton);
            rowRadioGroup.addView(twoButton);
            rowRadioGroup.addView(threeButton);
        }
        else{
            RadioButton oButton = new RadioButton(context);
            oButton.setText("O");
            oButton.setOnClickListener(new RadioButtonClickListener(1));

            RadioButton bButton = new RadioButton(context);
            bButton.setText("B");
            bButton.setOnClickListener(new RadioButtonClickListener(0));

            RadioButton naButton = new RadioButton(context);
            naButton.setText("N/A");
            naButton.setOnClickListener(new RadioButtonClickListener(0));

            rowRadioGroup.addView(naButton);
            rowRadioGroup.addView(bButton);
            rowRadioGroup.addView(oButton);
        }

        return rowRadioGroup;
    }

    private class RadioButtonClickListener implements View.OnClickListener{
        int score;
        EvaluationActivity.PlaceholderFragment caller;
        RadioButtonClickListener(int score) throws Exception {
            this.score = score;
            //this.caller = caller;
        }
        @Override
        public void onClick(View v) {
            try {
                setScore(score);
                int sco = getScore();
                getScore();
                //EvaluationActivity.PlaceholderFragment.updateScores(caller.getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
