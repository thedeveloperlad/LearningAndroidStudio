package com.example.simplecalculator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.util.Log; // DEBUG

public class MainActivity extends AppCompatActivity {


    private Button[] buttons = new Button[StaticNumbers.NUMBER_OF_BUTTONS];
    private Button[] buttonsOperations = new Button[StaticNumbers.NUMBER_OF_OPERATIONS];
    CalculatorOperations operation = new CalculatorOperations();
    // private static final String CONSOLE_MESSAGE = "onGetNumbers(): "; // Replace "YourClassName" with the actual class name
    public double number1=0;
    public double number2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        populateButtons();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onGetNumbers(View view){
        TextView tempOperationsText = (TextView)findViewById(R.id.tempOperations);

        for(int i=0; i<buttons.length; i++) {
            final int index = i;

            if(view.getId() == buttons[index].getId()) {
                Toast.makeText(MainActivity.this, "Button " + (index) + " clicked", Toast.LENGTH_SHORT).show();
                if(view.getId() == R.id.dotId)
                {
                    if(operation.getDotFlag())
                    {
                        Toast.makeText(MainActivity.this, "Not valid", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    operation.dotFlagStatus(true);
                    tempOperationsText.setText("" + tempOperationsText.getText() + buttons[index].getText());
                }
                tempOperationsText.setText("" + tempOperationsText.getText() + buttons[index].getText());
            }
        }
    }
    /*public void onGetNumbers(View view){

        TextView tempOperationsText = (TextView)findViewById(R.id.tempOperations);


        // Fix this loop to caputure the first click
        for(int i=0; i<buttons.length; i++)
        {
            final int index =i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Button " + (index) + " clicked", Toast.LENGTH_SHORT).show();
                if(v.getId() == R.id.dotId)
                {
                    if(operation.getDotFlag())
                    {
                        Toast.makeText(MainActivity.this, "Not valid", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    operation.dotFlagStatus(true);

                tempOperationsText.setText("" + tempOperationsText.getText() + buttons[index].getText());
                }
            });
            });
        }
    }*/

    public void onCalculatorOperations(View view){
        TextView tempOperationsText = (TextView)findViewById(R.id.tempOperations);
        // EditText number2Text = (EditText)findViewById(R.id.number2);
        TextView resultText = (TextView)findViewById(R.id.result);

        TextView tempValue1Text = (TextView)findViewById(R.id.tempValue1);
        TextView tempValue2Text = (TextView)findViewById(R.id.tempValue2);
        TextView symbolText = (TextView)findViewById(R.id.operationSymbol);
        number1 = operation.convertStringToDouble(tempOperationsText.getText().toString());

        Log.d("onCalculatorOperations()= ","number1= " + number1);

        operation.dotFlagStatus(false);

        if (view.getId() == R.id.add)
        {
            // result = operation.add(number1, number2);
            operation.addStatus(true);
            tempValue1Text.setText(tempOperationsText.getText().toString());// (int) number1);
            symbolText.setText("+");
            // Toast.makeText(MainActivity.this, "Button " + "+" + " clicked", Toast.LENGTH_SHORT).show();
        } else if(view.getId() == R.id.substraction)
        {
            operation.substractionStatus(true);
            tempValue1Text.setText(tempOperationsText.getText().toString());//(int) number1);
            symbolText.setText("-");
        } else if(view.getId() == R.id.multiplication)
        {
            operation.multiplicationStatus(true);
            tempValue1Text.setText(tempOperationsText.getText().toString());
            symbolText.setText("x");
        } else if(view.getId() == R.id.division)
        {
            operation.divisionStatus(true);
            tempValue1Text.setText(tempOperationsText.getText().toString());
            symbolText.setText("/");
        } /* else if(view.getId() == R.id.percentage){

        } */
        tempOperationsText.setText("");
    }

    public void onClear(View view)
    {
        TextView tempOperationsText = (TextView)findViewById(R.id.tempOperations);
        TextView resultText = (TextView)findViewById(R.id.result);
        TextView tempValue1Text = (TextView)findViewById(R.id.tempValue1);
        TextView tempValue2Text = (TextView)findViewById(R.id.tempValue2);
        TextView symbolText = (TextView)findViewById(R.id.operationSymbol);

        tempOperationsText.clearComposingText();
        resultText.clearComposingText();
        tempOperationsText.setText("");
        resultText.setText("");
        tempValue1Text.setText("");
        tempValue2Text.setText("");
        symbolText.setText("");
        // operation.dotFlagStatus(false);
        operation.clearStatusFlags(false);
        number1 = 0;
        number2 = 0;
    }

    public void onBackspace(View view)
    {
        TextView currentText = (TextView)findViewById(R.id.tempOperations);
        String currentTextString = ((String) currentText.getText()).toString();

        if(currentTextString.length() > 0)
        {
            currentText.setText(currentTextString.substring(0, currentText.length() - 1));
        }
    }

    void populateButtons()
    {
        for(int i=0; i< StaticNumbers.BUTTON_NUMBERS_ID.length; i++)
        {
            buttons[i] = (Button) findViewById(StaticNumbers.BUTTON_NUMBERS_ID[i]);
        }

        /** for(int i=0; i< StaticNumbers.OPERATIONS_ID.length; i++)
        {
            buttonsOperations[i] = (Button) findViewById(StaticNumbers.OPERATIONS_ID[i]);
        } **/
    }

    public void onResult(View view)
    {
        TextView tempOperationsText = (TextView)findViewById(R.id.tempOperations);
        TextView resultText = (TextView)findViewById(R.id.result);

        TextView tempValue2Text = (TextView)findViewById(R.id.tempValue2);

        number2 = operation.convertStringToDouble(tempOperationsText.getText().toString());
        tempValue2Text.setText(tempOperationsText.getText().toString());

        if(operation.getAddFlag())
        {
            int result = operation.add((int) number1, (int) number2);
            operation.addStatus(false);
            resultText.setText(String.valueOf(result));
        } else if(operation.getSubstractionFlag())
        {
            int result = operation.substraction((int) number1, (int) number2);
            operation.substractionStatus(false);
            resultText.setText(String.valueOf(result));
        } else if(operation.getMultiplicationFlag())
        {
            int result = operation.multiplication((int) number1, (int) number2);
            operation.multiplicationStatus(false);
            resultText.setText(String.valueOf(result));
        } else if(operation.getDivisionFlag())
        {
            double result = operation.division((int) number1, (int) number2);
            operation.divisionStatus(false);
            resultText.setText(String.valueOf(result));
        }
    }

}