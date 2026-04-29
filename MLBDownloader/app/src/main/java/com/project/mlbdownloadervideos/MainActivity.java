package com.project.mlbdownloadervideos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.TestOnly;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {

    FileParser fileParser = new FileParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setToolbar();
        if(!checkConnection()){
            showNetworkInfo(checkConnection());
        }
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

    public void DownloaderButton(View view) throws ExecutionException, InterruptedException, JSONException {
        TextInputLayout URLText = (TextInputLayout)findViewById(R.id.VideoLinkText);
        String pathValue = String.valueOf(URLText.getEditText().getText());

        if(pathValue.isEmpty()){
            //Display warning or error message.
            Toast.makeText(MainActivity.this, "Not valid", Toast.LENGTH_SHORT).show();
        } else {
            if(pathValue.contains("mlb") || pathValue.contains("milb")){

                if(!checkConnection()){
                    showNetworkInfo(checkConnection());
                } else {
                    String mlbHtmlLink = new GetEventsTask().execute(pathValue).get();
                    JSONObject resultJSON = fileParser.mlbParserFile(mlbHtmlLink);

                    Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                    intent.putExtra("name", resultJSON.get("name").toString());
                    intent.putExtra("link", resultJSON.get("link").toString());
                    intent.putExtra("image", resultJSON.get("image").toString());
                    startActivity(intent);
                }
            }
            else {
                Toast.makeText(MainActivity.this, "Invalid link, only supports MLB & MiLB...", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private boolean checkConnection() {

        // initialize intent filter
        IntentFilter intentFilter = new IntentFilter();
        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");
        // register receiver
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(new ConnectionReceiver(), intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }
        // Initialize listener
        ConnectionReceiver.Listener = this;
        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        // get connection status
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        return isConnected;
    }

    private void showNetworkInfo(boolean isConnected) {

        String message;
        int color;

        if (isConnected) {
            message = "Connected to Internet";
            // color = Color.WHITE;
        } else {
            message = "Not Connected to Internet";
            // color = Color.RED;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage(message);

        // Create and show the AlertDialog
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        showNetworkInfo(isConnected);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkConnection();
    }

    @Override
    public void onPause() {
        super.onPause();
        checkConnection();
    }

    protected class GetEventsTask extends AsyncTask<String, Void, String> {

        private final ProgressDialog dialog = new ProgressDialog(
                MainActivity.this);

        protected void onPreExecute(){
            //super.onPreExecute();
            // start loading icon
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
                /* Log.d("GetEventsTask= data=", data); */
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
            //super.onPostExecute(aVoid);
            //processValue(aVoid);
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("GetEventsTask", "TEST onPostExecute");
            // Update UI
        }
    }
}