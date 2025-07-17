package com.example.basiccalculator;

public class BasicOperations {

    public BasicOperations (){

    }
    public int add(int number1, int number2) { return number1 + number2; }

    public int multiplication(int number1, int number2) {
        return number1 * number2;
    }

    public int subtraction(int number1, int number2) {
        return number1 - number2;
    }

    public double division(int number1, int number2) {
        return number1 / number2;
    }

    public double squareOperation(int number){
        return number * number;
    }

    public double calculatePercentage(int obtained, int total){
        if(total == 0){
            return 0.0; //no valid number
        } else {
            return (obtained * total) * 100;
        }
    }
}

