package com.example.simplecalculator;

public class CalculatorOperations {

    // Consturctor
    CalculatorOperations(){}

    //flags move to another class later
    private boolean addFlag = false;
    private boolean substractionFlag = false;
    private boolean multiplicationFlag = false;
    private boolean divisionFlag = false;
    private boolean dotFlag = false;

    public int add(int number1, int number2){
        return number1 + number2;
    }

    public int substraction(int number1, int number2){
        return number1 - number2;
    }

    public int multiplication(int number1, int number2){
        return number1 * number2;
    }

    public double division(int number1, int number2){
        return (double)number1 / number2;
    }

    public void addStatus(boolean flag){
        addFlag = flag;
    }

    public void substractionStatus(boolean flag){
        substractionFlag = flag;
    }

    public void multiplicationStatus(boolean flag){
        multiplicationFlag = flag;
    }

    public void divisionStatus(boolean flag){
        divisionFlag = flag;
    }

    public void dotFlagStatus(boolean flag){
        dotFlag = flag;
    }

    public void clearStatusFlags(boolean flag){
        addFlag = flag;
        substractionFlag = flag;
        multiplicationFlag = flag;
        divisionFlag = flag;
        dotFlag = flag;
    }

    public boolean getAddFlag()
    {
        return addFlag;
    }

    public boolean getSubstractionFlag()
    {
        return substractionFlag;
    }

    public boolean getMultiplicationFlag()
    {
        return multiplicationFlag;
    }

    public boolean getDivisionFlag()
    {
        return divisionFlag;
    }

    public boolean getDotFlag()
    {
        return dotFlag;
    }

    public int convertStringToInteger(String number){
        return Integer.parseInt(number);
    }

    public double convertStringToDouble(String number)
    {
        double doubleValue;
        try {
            doubleValue = Double.parseDouble(number);
        } catch (NumberFormatException e) {
            doubleValue = 0.0; // Assign a default value, or handle the error appropriately
            e.printStackTrace(); // Log the exception for debugging
        }
        return doubleValue;
    }
}
