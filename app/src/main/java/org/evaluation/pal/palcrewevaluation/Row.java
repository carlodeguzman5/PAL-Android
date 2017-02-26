package org.evaluation.pal.palcrewevaluation;

/**
 * Created by carlo on 2/25/2017.
 * This class represents each row in the Dimensions
 * This the base class of the rows
 */

abstract class Row {
    String rowName;

    protected String getRowName(){
        return rowName;
    }
}
