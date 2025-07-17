package com.example.basiccalculator;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.basiccalculator.BasicOperations;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        /* BasicOperations operation = new BasicOperations();

        //Buttons on activity main
        // Button addButton = (Button)findViewById(R.id.add);
        // Button subButton = (Button)findViewById(R.id.sub);
        // Button divButton = (Button)findViewById(R.id.div);
        // Button multButton = (Button)findViewById(R.id.mult);

        Button buttonOperation = (Button)findViewById(R.id.button);

        EditText number1Text = (EditText)findViewById(R.id.number1);
        EditText number2Text = (EditText)findViewById(R.id.number2);

        TextView resultText = (TextView)findViewById(R.id.result); */


        /* buttonOperation.setOnClickListener((View.OnClickListener)(new View.OnClickListener()
        {
            public final void onClick(View it)
            {
                if(number1Text.length() == 0 || number2Text.length() == 0) {
                        // displaying a toast message
                    Toast.makeText((Context)MainActivity.this, "No valid number", Toast.LENGTH_LONG).show();
                } else {

                    String value = number1Text.getText().toString();
                    int number1 = Integer.parseInt(value);
                    String value2 = number2Text.getText().toString();
                    int number2 = Integer.parseInt(value2);
                    int resultOperation=0;

                    if( buttonOperation.getText().toString().equals("+")){

                        resultOperation = operation.add(number1, number2);

                    } else if (buttonOperation.getText().toString().equals("-"))
                    {
                        resultOperation = operation.subtraction(number1, number2);
                    } else if (buttonOperation.getText().toString().equals("/"))
                    {
                        float resultDiv = operation.division(number1, number2);
                        // resultText.setText((String.valueOf(resultDiv)));
                    } else if (buttonOperation.getText().toString().equals("x"))
                    {
                        resultOperation = operation.multiplication(number1, number2);
                    }

                    // print result in TextView
                    resultText.setText((String.valueOf(resultOperation)));
                }
            }
        }));*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onCalculatorOperations(View view){

        BasicOperations operation = new BasicOperations();

        EditText number1Text = (EditText)findViewById(R.id.number1);
        EditText number2Text = (EditText)findViewById(R.id.number2);

        TextView resultText = (TextView)findViewById(R.id.result);
        if(number1Text.length() == 0 || number2Text.length() == 0) {
            // displaying a toast message
            Toast.makeText((Context)MainActivity.this, "No valid number", Toast.LENGTH_LONG).show();
        } else {

            String value = number1Text.getText().toString();
            int number1 = Integer.parseInt(value);
            String value2 = number2Text.getText().toString();
            int number2 = Integer.parseInt(value2);
            int resultOperation = 0;
            double resultDiv = 0;
            boolean divisionFlag = false;

            if (view.getId() == R.id.add) {
                resultOperation = operation.add(number1, number2);
            } else if (view.getId() == R.id.substraction) {
                resultOperation = operation.subtraction(number1, number2);
            } else if (view.getId() == R.id.division) {
                resultDiv = operation.division(number1, number2);
                divisionFlag = true;
            } else if (view.getId() == R.id.mult) {
                resultOperation = operation.multiplication(number1, number2);
            } else if (view.getId() == R.id.square) {
                resultDiv = operation.squareOperation(number1);
                divisionFlag = true;
            } else if(view.getId() == R.id.percentage){
                resultDiv = operation.calculatePercentage(number1, number2);
                divisionFlag = true;
            }

            // print result in TextView
            if(divisionFlag == false) {
                resultText.setText((String.valueOf(resultOperation)));
            } else {
                divisionFlag = false;
                resultText.setText((String.valueOf(resultDiv)));
            }
        }
    }
}