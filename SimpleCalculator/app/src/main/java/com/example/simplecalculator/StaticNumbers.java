package com.example.simplecalculator;

public class StaticNumbers {
    public static final int NUMBER_OF_BUTTONS = 11;
    public static final int NUMBER_OF_OPERATIONS = 9;

    public static final int[] BUTTON_NUMBERS_ID = {
            R.id.number0,
            R.id.number1,
            R.id.number2,
            R.id.number3,
            R.id.number4,
            R.id.number5,
            R.id.number6,
            R.id.number7,
            R.id.number8,
            R.id.number9,
            R.id.dotId
    };

    public static final int[] OPERATIONS_ID = {
            R.id.add,
            R.id.substraction,
            R.id.multiplication,
            R.id.division,
            R.id.percentage,
            R.id.result,
            R.id.add_or_substraction_symbol,
            R.id.backspace,
            R.id.clear
    };

    /*
    public static boolean dotFlag = false;

    public static void dotFlagStatus(boolean flag){
        dotFlag = flag;
    }

    public boolean getDotFlag()
    {
        return dotFlag;
    }
    */
}
