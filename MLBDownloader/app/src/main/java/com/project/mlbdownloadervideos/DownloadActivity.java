package com.project.mlbdownloadervideos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DownloadActivity extends AppCompatActivity {
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
    }

    void openVideo(){
        /*String videoLink = result.get("link").toString();
            videoURLLink = videoLink;
            //myVideoLinkTextView.setText(result.get("link").toString());

            videoView = findViewById(R.id.videoId);
            //Create object for media Controller
            MediaController mediaController = new MediaController(this);
            //set media player
            mediaController.setMediaPlayer(videoView);
            //Set media Controller for media View
            videoView.setMediaController(mediaController);
            //set Video URL
            videoView.setVideoURI(Uri.parse(pathValue));*/
    }
}
