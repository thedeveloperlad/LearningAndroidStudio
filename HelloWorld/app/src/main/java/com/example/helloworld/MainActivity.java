package com.example.helloworld;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        EditText editText = (EditText)findViewById(R.id.editTextText);

        if(button != null)
        {
            button.setOnClickListener((View.OnClickListener)(new View.OnClickListener()
            {
                        public final void onClick(View it)
                        {
                            if(editText.length() == 0) //null
                            {
                                // displaying a toast message
                                Toast.makeText((Context)MainActivity.this, "Hello World Testing", Toast.LENGTH_LONG).show();
                            } else {
                                // displaying a toast message
                                Toast.makeText((Context)MainActivity.this, (CharSequence) editText.getText(), Toast.LENGTH_LONG).show();
                            }
                        }
            }));
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}