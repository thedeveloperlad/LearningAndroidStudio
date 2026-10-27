package com.example.filereader;

import static java.lang.System.err;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileListIPTVActivity extends AppCompatActivity {
    M3u8Parser m3u8parser = new M3u8Parser();
    private static int save = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.file_list_iptv_activity);

        turnOnIPTV();
        //printResultonScreen();
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    void turnOnIPTV(){
        Intent getIntent = getIntent();
        String urlLink = getIntent.getStringExtra("url");
        Log.d("urlLink= ", urlLink);
        Toast.makeText(FileListIPTVActivity.this, urlLink, Toast.LENGTH_SHORT).show();
        fetchM3u8NetworkData(urlLink);

    }

    private void fetchM3u8NetworkData(String urlLink){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Create a handler linked to the Main (UI) Looper
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        executor.execute(()->{
            try {
                URL url;
                url = new URL(urlLink);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000); // 10 seconds timeout
                connection.connect();

                // InputStream inputStream = connection.getInputStream();
                // Map<String, List<Object>> resultData = m3u8parser.readM3u8Attributes(inputStream);
                List<MediaIPTVItemModel> resultData = m3u8parser.readM3u8Attributes(urlLink);

                //Log.e("fetchM3u8NetworkData().resultData=", resultData.toString());
                connection.disconnect();

                // Send data back safely to the UI thread
                mainThreadHandler.post(() -> {
                    // --- MAIN UI THREAD CODE ---
                    updateView(resultData);
                });


            } catch (Exception e) {
                Log.e("M3u8Network", "Error fetching/parsing M3U8 payload", e);

                // Handle errors on UI thread if needed
                mainThreadHandler.post(() -> {
                    // Show an error message toast or text view here
                });

            }
        });
    }

    private void updateView(List<MediaIPTVItemModel> data){
        Log.e("fetchM3u8NetworkData().resultData=", data.toString());
        ListView listView = findViewById(R.id.myListView);
        ArrayList<MediaIPTVItemModel> itemsList = new ArrayList<>();

        for(int index = 0; index<data.size(); index++){

            // System.out.println("innerObj= " + innerObj);
            //Log.d("jsonObject= ", data.get(index).getName());
            /*if(isLinkOnline(data.get(index).getUrl()))
            {

            }*/
            itemsList.add(new MediaIPTVItemModel(data.get(index).getGroup(),
                    data.get(index).getLogo(),
                    data.get(index).getName(),
                    data.get(index).getNumber(),
                    data.get(index).getCUID(),
                    data.get(index).getCountry(),
                    data.get(index).getLanguage(),
                    data.get(index).getRadio(),
                    data.get(index).getUrl()));
        }

        DetailListIPTVItemAdapter adapter = new DetailListIPTVItemAdapter(this, R.layout.item_iptv_view, itemsList);
        listView.setAdapter(adapter);
    }

    public void onListItemClick(View view) {
        // 'view' is the exact item row that was clicked
        TextView titleView = view.findViewById(R.id.tvItemTitle);
        TextView urlView = view.findViewById(R.id.tv_item_link);
        String urlSource = urlView.getText().toString();
        Log.d("ListClick", "URL: " + urlSource);

        /*parent.getChildAt(view.getVerticalScrollbarPosition()).setBackgroundColor(Color.BLUE);

        if (save != -1 && save != view.getVerticalScrollbarPosition()){
            parent.getChildAt(save).setBackgroundColor(Color.BLACK);
        }

        save = view.getVerticalScrollbarPosition();*/
        // Toast.makeText(FileListIPTVActivity.this, "ITEM CLICKED= " + urlView, Toast.LENGTH_SHORT).show();

        openVideo(urlSource);
        //String clickedText = titleView.getText().toString();


        // Do something with the clicked item
        //Log.d("ListClick", "You clicked: " + clickedText);
        //Toast.makeText(FileListIPTVActivity.this, "ITEM CLICKED" + urlSource, Toast.LENGTH_SHORT).show();
    }

    void openVideo(String videoLink){
        Uri uri = Uri.parse(videoLink);
        VideoView videoView = findViewById(R.id.myVideoView);
        //Create object for media Controller
        MediaController mediaController = new MediaController(this);
        //set media player
        mediaController.setMediaPlayer(videoView);
        //Set media Controller for media View
        videoView.setMediaController(mediaController);
        //set Video URL
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        //videoView.stopPlayback();
        videoView.setZOrderOnTop(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        VideoView videoView = findViewById(R.id.myVideoView);
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoView videoView = findViewById(R.id.myVideoView);
        videoView.start();
    }


}
