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

import com.itextpdf.text.pdf.parser.Line;

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
        for(Row row : rowList){
            if(row.getClass() == Aspect.class){
                Aspect aspect = (Aspect) row;
                if(aspect.getCategory() == category){
                    score += aspect.getScore();
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

    public View getViewPage(FragmentActivity context, EvaluationActivity.PlaceholderFragment caller){

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

                try {
                    rowRadioGroup = aspect.generateRadioGroup(context, radioType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public View getSummaryRow(FragmentActivity context){
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.5f
        );
        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.25f
        );

        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setWeightSum(1);

        TextView dimensionNameText = new TextView(context);
        dimensionNameText.setText(this.dimensionName);
        dimensionNameText.setLayoutParams(param);

        TextView serviceScoreText = new TextView(context);
        serviceScoreText.setText(String.valueOf(getScoreByCategory(Category.Service)));
        serviceScoreText.setLayoutParams(param2);

        TextView safetyScoreText = new TextView(context);
        safetyScoreText.setText(String.valueOf(getScoreByCategory(Category.Safety)));
        safetyScoreText.setLayoutParams(param2);

        row.addView(dimensionNameText);
        row.addView(serviceScoreText);
        row.addView(safetyScoreText);

        return row;
    }






}
