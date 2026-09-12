package com.project.mlbdownloadervideos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileInfoActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fileinfo_activity);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setToolbar();
        showFileInfoOnScreen();


        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    void setToolbar(){
        /*Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Media Info (i)");

        TextView descriptionTextView = findViewById(R.id.descriptionTextId);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());*/
    }

    private void showFileInfoOnScreen() {
        Intent getIntent = getIntent();

        String mlbRawString = getIntent.getStringExtra("mlbJson").toString();

        if(!mlbRawString.isEmpty()) {
            try {
                JSONObject mlbRaw = new JSONObject(mlbRawString);

                TextView nameInfoVideoView = (TextView) findViewById(R.id.nameInfoVideo);
                TextView descriptionInfoVideoView = (TextView) findViewById(R.id.descriptionInfoVideo);
                TextView uploadDateInfoVideoView = (TextView) findViewById(R.id.uploadDateInfoVideo);
                TextView durationInfoVideoView = (TextView) findViewById(R.id.durationInfoVideo);
                TextView thumbnailUrlInfoVideoView = (TextView) findViewById(R.id.thumbnailUrlInfoVideo);
                // ImageView imageURLSampleView = (ImageView) findViewById(R.id.ImageSample);

                nameInfoVideoView.setTextIsSelectable(false);

                nameInfoVideoView.setText(mlbRaw.get("name").toString());
                descriptionInfoVideoView.setText(mlbRaw.get("description").toString());
                uploadDateInfoVideoView.setText(mlbRaw.get("uploadDate").toString());
                durationInfoVideoView.setText(mlbRaw.get("duration").toString());
                thumbnailUrlInfoVideoView.setText(mlbRaw.get("thumbnailUrl").toString());
                setBitmapFromURL(mlbRaw.get("thumbnailUrl").toString());
                // imageURLSampleView.setImageBitmap(getBitmap(mlbRaw.get("thumbnailUrl").toString()));

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(FileInfoActivity.this, "Invalid Data...", Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap getBitmap(String source){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        final Bitmap[] myBitmap = new Bitmap[1];
        // [Equivalent to onPreExecute]
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // [Equivalent to doInBackground]
                // Run your heavy background tasks here (Network, Database, etc.)


                // Send the result back to the main UI thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // [Equivalent to onPostExecute]
                        // Update your text views, hide spinners, or show data here
                        // myTextView.setText(result); [EXAMPLE]
                    }
                });
            }
        });
        return myBitmap[0];
    }

    public void setBitmapFromURL(String src) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        ImageView imageURLSampleView = (ImageView) findViewById(R.id.ImageSample);

        executor.execute(() -> {
            try {
                java.io.InputStream in = new java.net.URL(src).openStream();
                android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(in);

                // Push the bitmap to the UI thread
                handler.post(() -> imageURLSampleView.setImageBitmap(bitmap));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

/*{
    "@context":"https://schema.org",
    "@type":"VideoObject",
    "name":"Luis Campusano's walk-off two-run homer (6)",
    "description":"Luis Campusano belts a walk-off two-run homer deep to left field to defeat the Yankees, 3-2 in extra innings",
    "thumbnailUrl":"https://img.mlbstatic.com/mlb-images/image/upload/ar_16:9,g_auto,q_auto:good,w_1536,c_fill,f_jpg/mlb/mcnyapc1qkoizcqqa2qk",
    "uploadDate":"2026-09-04",
    "duration":"P0Y0M0DT0H0M43S",
    "contentUrl":"https://mlb-cuts-diamond.mlb.com/FORGE/2026/2026-09/04/8aa5965e-ed1814dd-ba313552-csvm-diamondgcp-asset.m3u8",
    "embedUrl":"https://streamable.com/m/paul-blackburn-in-play-run-s-to-luis-campusano"
}*/