package com.project.mlbdownloadervideos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setToolbar();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void setToolbar(){
        // Find the toolbar by ID
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.my_toolbar);

        // Set the toolbar to act as the ActionBar for this Activity
        setSupportActionBar(toolbar);

        // Optional: Customize title or navigation icon
        getSupportActionBar().setTitle("MLB Downloader");
    }

    public void DownloaderButton(View view){
        TextInputLayout URLText = (TextInputLayout)findViewById(R.id.VideoLinkText);

        String pathValue = String.valueOf(URLText.getEditText().getText());
        Log.d("fileReader()= ","pathValue= " + pathValue);

    }

    protected class GetEventsTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
            Log.d("GetEventsTask", "TEST onPreExecute");
            // start loading icon
        }
        @Override
        protected String doInBackground(String... args) {
            Log.d("GetEventsTask", "TEST doInBackground");
            String urlDATA = args[0];
            Log.d("GetEventsTask= data=", urlDATA);
            String dataResult = "";
            try{
                URL url = new URL(urlDATA);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                // Set timeouts
                httpURLConnection.setConnectTimeout(5000); // 5 seconds
                httpURLConnection.setReadTimeout(5000);    // 5 seconds

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                String data="";
                while((line = bufferedReader.readLine()) != null){
                    data = data + line;
                }
                dataResult = data;
                Log.d("GetEventsTask= data=", data);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } /*catch (JSONException e) {
                throw new RuntimeException(e);
            }*/
            return dataResult;
        }

        @Override
        protected void onPostExecute(String aVoid){
            super.onPostExecute(aVoid);
            //processValue(aVoid);
            Log.d("GetEventsTask", "TEST onPostExecute");
            // Update UI
        }
    }
}