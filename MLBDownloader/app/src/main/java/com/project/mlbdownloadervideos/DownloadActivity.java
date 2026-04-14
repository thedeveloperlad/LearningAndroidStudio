package com.project.mlbdownloadervideos;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DownloadActivity extends AppCompatActivity {

    DownloadManager manager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.download_activity);
        setToolbar();
        printResultScreen();
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    void setToolbar(){
        // Find the toolbar by ID
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.my_toolbar);

        // Set the toolbar to act as the ActionBar for this Activity
        setSupportActionBar(toolbar);

        // Optional: Customize title or navigation icon
        getSupportActionBar().setTitle("Download Page");
    }

    void printResultScreen(){
        Intent getIntent = getIntent();
        String name = getIntent.getStringExtra("name");
        String link = getIntent.getStringExtra("link");
        String image = getIntent.getStringExtra("image");

        TextView nameView = (TextView) findViewById(R.id.nameTextId);
        TextView linkView = (TextView) findViewById(R.id.linkTextId);
        TextView imageView = (TextView) findViewById(R.id.imageLinkId);

        nameView.setText(name);
        linkView.setText(link);
        imageView.setText(image);

        //Open video player for example
        openVideo(link);
    }

    public void DownloadButton(View view){
        TextView nameView = (TextView) findViewById(R.id.nameTextId);
        //Log.d("DownloadActivity.DownloadVideoButton()", "entro al metodo");
        TextView linkView = (TextView) findViewById(R.id.linkTextId);
        manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //Log.d("DownloadActivity.DownloadVideoButton().linkView.toString()=", linkView.getText().toString());
        Uri uri = Uri.parse(linkView.getText().toString());
        //Log.d("DownloadActivity.DownloadVideoButton()", "uri");
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //Log.d("DownloadActivity.DownloadVideoButton()", "request");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //Log.d("DownloadActivity.DownloadVideoButton()", "request.setNotificationVisibility");
        nameView.getText().toString();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameView.getText().toString()+"video.mp4");
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        long reference = manager.enqueue(request);
        // long reference = manager.enqueue(request);
    }

    public void BackHomeButton(View view){
        Intent intent = new Intent(DownloadActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    void openVideo(String videoLink){
            Uri uri = Uri.parse(videoLink);
            VideoView videoView = findViewById(R.id.videoId);
            //Create object for media Controller
            MediaController mediaController = new MediaController(this);
            //set media player
            mediaController.setMediaPlayer(videoView);
            //Set media Controller for media View
            videoView.setMediaController(mediaController);
            //set Video URL
            videoView.setVideoURI(uri);
            videoView.stopPlayback();
            videoView.setZOrderOnTop(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        VideoView videoView = findViewById(R.id.videoId);
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoView videoView = findViewById(R.id.videoId);
        videoView.start();
    }
}
