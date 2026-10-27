
package com.example.filereader;

import static java.lang.System.err;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //String APIJsonList = "https://streamtpnew.com/eventos.json?nocache=1775656111252";
    String APIJsonList = "https://streamtp-x-y-z.ws/eventos.json?nocache=1780674715448";
    //String textLinkTest2 = "https://raw.githubusercontent.com/thedeveloperlad/links_channels/refs/heads/main/testing_json.json";
    JsonParser fileParser = new JsonParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onReadLink(View view) throws ExecutionException, InterruptedException {
        EditText URLText = (EditText) findViewById(R.id.myLinkText);
        String pathValue = String.valueOf(URLText.getText());

        if(pathValue.isEmpty()){
            //Display warning or error message.
            Toast.makeText(MainActivity.this, "Not valid", Toast.LENGTH_SHORT).show();
        } else {
            // Do parser Things
            Intent intent = new Intent(MainActivity.this, FileListIPTVActivity.class);
            intent.putExtra("url", pathValue);
            startActivity(intent);
        }
    }

    //onLiveTV
    public void onLiveTV(View view) throws ExecutionException, InterruptedException, MalformedURLException {
        String LiveTV = "https://raw.githubusercontent.com/media-api-dev/media_tv/refs/heads/main/tv_sports_live.m3u8";

        // Send URL to another ACTIVITY
        Intent intent = new Intent(MainActivity.this, FileListIPTVActivity.class);
        intent.putExtra("url", LiveTV);
        startActivity(intent);
    }
    public void onLoadLink(View view) throws ExecutionException, InterruptedException, JSONException {
        Toast.makeText(MainActivity.this, "click on onLoadLink", Toast.LENGTH_SHORT).show();
        EditText URLText = (EditText) findViewById(R.id.myLinkText);
        String pathValue = String.valueOf(URLText.getText());

        if(pathValue.isEmpty()){
            Toast.makeText(MainActivity.this, "URL field is empty", Toast.LENGTH_SHORT).show();
        } else {
            /*Intent intent = new Intent(MainActivity.this, FileListIPTVActivity.class);
            intent.putExtra("url", pathValue);
            startActivity(intent);*/
        }
    }

    public void onReadSchedule(View view) throws ExecutionException, InterruptedException, JSONException {
        String pathValue = APIJsonList;

        if(pathValue.toString().isEmpty()){
            //Display warning or error message.
            Toast.makeText(MainActivity.this, "Not valid", Toast.LENGTH_SHORT).show();
        } else {
            // Do parser Things
            String strJson = new MyTask().execute(pathValue).get();
            //convertStringToJsonArray
            JSONArray jsonArray = fileParser.convertStringToJsonArray(strJson);
            JSONObject jsonObject = fileParser.convertJsonArrayToJsonObject(jsonArray);
            String jsonObjectLength = Integer.toString(jsonObject.length());
            Log.d("jsonObject.length= ", jsonObjectLength);
            Log.d("jsonObject= ", jsonObject.toString());

            if(jsonObject.length() == 0){

                Toast.makeText(MainActivity.this, "Not valid JSON", Toast.LENGTH_SHORT).show();

            } else {
                System.out.printf("jsonArray.length(): " + jsonArray.length());

                Intent intent = new Intent(MainActivity.this, FileListActivity.class);
                intent.putExtra("JSON_DATA", jsonArray.toString());
                startActivity(intent);

                /*for(int index = 0; index<jsonArray.length(); index++){
                    JSONObject innerObj = jsonArray.getJSONObject(index);
                    // System.out.println("innerObj= " + innerObj);
                    Log.d("jsonObject= ", innerObj.toString());
                    System.out.printf("title: "+ innerObj.get("title") + " ");//getJSONObject("title"));
                    System.out.printf("time: "+ innerObj.get("time") + " ");
                    System.out.printf("category: "+ innerObj.get("category") + " ");
                    System.out.printf("status: "+ innerObj.get("status") + " ");
                    System.out.printf("link: "+ innerObj.get("link") + " ");
                    System.out.printf("language: "+ innerObj.get("language") + " ");
                    System.out.printf("\n");
                }*/
            }
        }
    }

    // Background Thread to avoid conflict between main thread and a sub thread.
    private class MyTask extends AsyncTask<String, Integer, String> {
        private final ProgressDialog dialog = new ProgressDialog(
                MainActivity.this);
        @Override
        protected void onPreExecute() {
            // Setup UI before starting
            this.dialog.setMessage("Loading, Please Wait..");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String urlDATA = args[0];
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
        protected void onProgressUpdate(Integer... progress) {
            // Update UI with progress
        }

        @Override
        protected void onPostExecute(String result) {
            // Final UI update with result
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("GetEventsTask", "TEST onPostExecute");
        }
    }
}