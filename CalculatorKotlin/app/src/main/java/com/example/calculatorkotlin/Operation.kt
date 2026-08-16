package com.example.calculatorkotlin

import android.R
import android.view.View

class Operation() {

    var isAdd: Boolean = false
    var isSubtraction: Boolean = false
    var isMultiplication: Boolean = false
    var isDivision: Boolean = false

    fun add(a: Double, b: Double): Double {
        return a+b
    }

    fun subtraction(a: Double, b: Double): Double {
        return a-b
    }

    fun division(a: Double, b: Double): Double {
        return a/b
    }

    fun multiplication(a: Double, b: Double): Double {
        return a*b
    }

    fun setAddFlag(flag: Boolean) {
        isAdd = flag;
    }

    fun setSubtractionFlag(flag: Boolean ) {
        isSubtraction = flag
    }

    fun setMultiplicationFlag(flag: Boolean ) {
        isMultiplication = flag
    }

    fun setDivisionFlag(flag: Boolean ) {
        isDivision = flag
    }

    fun getAddFlag() : Boolean {
        return isAdd
    }

    fun getSubtractionFlag() : Boolean {
        return isSubtraction
    }

    fun getMultiplicationFlag() : Boolean {
        return isMultiplication
    }

    fun getDivisionFlag() : Boolean {
        return isDivision
    }
}