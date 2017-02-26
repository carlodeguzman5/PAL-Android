package org.evaluation.pal.palcrewevaluation;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by carlo on 2/25/2017.
 * This class represents a dimension in the evaluation
 */

public class Dimension {
    private List<Row> rowList;
    private String dimensionName;

    public Dimension(String dimensionName){
        this.dimensionName = dimensionName;
    }

    public List<Row> getRowList(){
        return rowList;
    }

    public void setRowList(List<Row> rowList){
        this.rowList = rowList;
    }

    public String getDimensionName(){
        return dimensionName;
    }

    public int getRowCount(){
        return rowList.size();
    }

    public boolean isCompleted(){
        for(Row r : rowList){
            if(r.getClass() == Aspect.class){
                Aspect a = (Aspect) r;
                if (!a.isAnswered()){
                    return false;
                }
            }
        }
        return true;
    }

    public int getTotalScore(){
        int score = 0;
        for(Row r : rowList){
            if(r.getClass() == Aspect.class) {
                Aspect a = (Aspect) r;
                score += a.getScore();
            }
        }
        return score;
    }

    public int getTotalMaxScore(){
        int maxScore = 0;
        for(Row r : rowList){
            if(r.getClass() == Aspect.class) {
                Aspect a = (Aspect) r;
                maxScore += a.getMaxScore();
            }
        }
        return maxScore;
    }

    public int getScoreByCategory(Category category){
        int score = 0;
        if (category == Category.Safety) {
            for(Row row : rowList){
                if(row.getClass() == Aspect.class){
                    Aspect aspect = (Aspect) row;
                    if(aspect.getCategory() == category){
                        score += aspect.getScore();
                    }
                }
            }
        }
        return score;
    }

    public int getMaxScoreByCategory(Category category){
        int maxScore = 0;
        if (category == Category.Safety) {
            for(Row row : rowList){
                if(row.getClass() == Aspect.class){
                    Aspect aspect = (Aspect) row;
                    if(aspect.getCategory() == category){
                        maxScore += aspect.getMaxScore();
                    }
                }
            }
        }
        return maxScore;
    }

    /* Layout Generation Methods */

    public View getViewPage(FragmentActivity context){

        ScrollView scroller = new ScrollView(context);
        LinearLayout container = new LinearLayout(context);
        container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(16, 16, 16, 16);

        scroller.addView(container);

        TextView sectionTextView = new TextView(context);
        sectionTextView.setText(dimensionName);
        sectionTextView.setTextSize(20.0f);
        container.addView(sectionTextView);

        for(Row row : rowList) {
            LinearLayout rowLayout = new LinearLayout(context);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setPadding(8, 16, 8, 16);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rowLayout.setLayoutParams(params);

            TextView rowLabelTextView = new TextView(context);
            RadioGroup rowRadioGroup = new RadioGroup(context);

            String rowLabel;
            RadioType radioType = RadioType.DEFAULT;

            rowLabel = row.getRowName();
            if(row.getClass() == Aspect.class){ // If row is an instance of the Aspect class
                Aspect aspect = (Aspect) row;
                if(aspect.getMaxScore() == 3){
                    radioType = RadioType.THREE;
                }

                if(aspect.getAspectType() == AspectType.DEFAULT) {
                    rowLabelTextView.setText(rowLabel);
                    rowLabelTextView.setLeft(0);
                    rowLayout.addView(rowLabelTextView);
                }
                else if (aspect.getAspectType() == AspectType.TEXT){
                    EditText rowEditText = new EditText(context);
                    rowEditText.setLayoutParams(params);
                    rowLayout.addView(rowEditText);
                }

                params.weight = 0.80f;
                params.gravity = Gravity.START;
                rowLabelTextView.setLayoutParams(params);

                rowRadioGroup = generateRadioGroup(context, radioType, rowLabel);
                //params.weight = 1f;
                //params.gravity = Gravity.RIGHT;
                //rowRadioGroup.setLayoutParams(params);

                //scoreCategoryMapping.put(rowLabel, rowType);

            }
            else if (row.getClass() == SectionHeader.class){

                rowLabelTextView.setText(rowLabel);
                rowLabelTextView.setTextSize(18);
                rowLabelTextView.setTextColor(Color.BLACK);
                rowLayout.addView(rowLabelTextView);
            }

            rowLayout.addView(rowRadioGroup);
            container.addView(rowLayout);
        }
        return scroller;
    }

    private static RadioGroup generateRadioGroup(final Context context, RadioType radioType, final String rowName){
        RadioGroup rowRadioGroup = new RadioGroup(context);

        rowRadioGroup.setOrientation(RadioGroup.HORIZONTAL);
        rowRadioGroup.setRight(0);

        if(radioType == RadioType.ONE){
            RadioButton zeroButton = new RadioButton(context);
            zeroButton.setText("0");
            zeroButton.setOnClickListener(new EvaluationActivity.RadioButtonClickListener());

            RadioButton oneButton = new RadioButton(context);
            oneButton.setText("1");
            oneButton.setOnClickListener(new EvaluationActivity.RadioButtonClickListener());

            rowRadioGroup.addView(zeroButton);
            rowRadioGroup.addView(oneButton);
        }
        else if(radioType == RadioType.THREE){
            RadioButton zeroButton = new RadioButton(context);
            zeroButton.setText("0");
            zeroButton.setOnClickListener(new EvaluationActivity.RadioButtonClickListener());

            RadioButton oneButton = new RadioButton(context);
            oneButton.setText("1");
            oneButton.setOnClickListener(new EvaluationActivity.RadioButtonClickListener());

            RadioButton twoButton = new RadioButton(context);
            twoButton.setText("2");
            twoButton.setOnClickListener(new EvaluationActivity.RadioButtonClickListener());

            RadioButton threeButton = new RadioButton(context);
            threeButton.setText("3");
            threeButton.setOnClickListener(new EvaluationActivity.RadioButtonClickListener());

            rowRadioGroup.addView(zeroButton);
            rowRadioGroup.addView(oneButton);
            rowRadioGroup.addView(twoButton);
            rowRadioGroup.addView(threeButton);
        }
        else{
            RadioButton oButton = new RadioButton(context);
            oButton.setText("O");
            oButton.setOnClickListener(new EvaluationActivity.RadioButtonClickListener());

            RadioButton bButton = new RadioButton(context);
            bButton.setText("B");
            bButton.setOnClickListener(new EvaluationActivity.RadioButtonClickListener());

            RadioButton naButton = new RadioButton(context);
            naButton.setText("N/A");
            naButton.setOnClickListener(new EvaluationActivity.RadioButtonClickListener());

            rowRadioGroup.addView(naButton);
            rowRadioGroup.addView(bButton);
            rowRadioGroup.addView(oButton);
        }

        return rowRadioGroup;
    }



}
