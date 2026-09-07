package com.project.mlbdownloadervideos;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.content.ClipData;
import android.content.ClipboardManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.media3.transformer.EditedMediaItem;

import org.json.JSONObject;

import java.io.File;
import java.util.Objects;

import javax.xml.transform.Transformer;

public class DownloadActivity extends AppCompatActivity {

    DownloadManager manager;
    // FfmpegUtility ffmpegUtility = new FfmpegUtility();
    private String name;
    private String link;
    private String image;
    private String description;

    private JSONObject mlbRawJson;

    private PlayerView playerView;
    private ExoPlayer player;
    private boolean playWhenReady = true;
    private int currentItem = 0;
    private long playbackPosition = 0L;

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
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);

        // Set the toolbar to act as the ActionBar for this Activity
        setSupportActionBar(toolbar);

        // Optional: Customize title or navigation icon
        getSupportActionBar().setTitle("Download Page");

        TextView descriptionTextView = findViewById(R.id.descriptionTextId);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    void printResultScreen(){
        Intent getIntent = getIntent();
        setName(getIntent.getStringExtra("name"));
        setLink(getIntent.getStringExtra("link"));
        setImage(getIntent.getStringExtra("image"));
        setDescription(getIntent.getStringExtra("description"));
        // setMlbRawJson(getIntent.getStringExtra("mlbJsonRaw"));

        // Log.d("data sample= ", Objects.requireNonNull(getIntent.getStringExtra("mlbJsonRaw")));

        TextView nameView = (TextView) findViewById(R.id.nameTextId);
        TextView linkView = (TextView) findViewById(R.id.linkTextId);
        TextView descriptionView = (TextView) findViewById(R.id.descriptionTextId);
        /*TextView linkView = (TextView) findViewById(R.id.linkTextId);
        TextView imageView = (TextView) findViewById(R.id.imageLinkId);*/
        nameView.setTextIsSelectable(true);

        nameView.setText(name);
        linkView.setText(link);
        descriptionView.setText(description);
        /*imageView.setText(image);*/

        //Open video player for example
        Log.d("DownloadActivity.printResultScreen= link = ", link.toString());
        if(link.toString() == null)
        {
            Toast.makeText(DownloadActivity.this, "Invalid link ,link is broken or is processing...", Toast.LENGTH_SHORT).show();
        } else {
            openVideo(link);
        }
    }

    public void onCopyDescription(View view) {
        //TextView nameTextView = findViewById(R.id.nameTextId);
        TextView descriptionTextView = findViewById(R.id.descriptionTextId);
        // Get the text from TextView
        // String nameTextToCopy = nameTextView.getText().toString();
        String descriptionTextToCopy = descriptionTextView.getText().toString();
        // Access the Clipboard service
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", descriptionTextToCopy);

        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            // Notify user
            Toast.makeText(DownloadActivity.this, "Text Copied!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCopyVideoText(View view) {
        TextView descriptionTextView = findViewById(R.id.nameTextId);
        String descriptionTextToCopy = descriptionTextView.getText().toString();
        // Access the Clipboard service
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", descriptionTextToCopy);

        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            // Notify user
            Toast.makeText(DownloadActivity.this, "Text Copied!", Toast.LENGTH_SHORT).show();
        }
    }

    public void DownloadButton(View view){
        TextView nameView = (TextView) findViewById(R.id.nameTextId);
        TextView linkView = (TextView) findViewById(R.id.linkTextId);
        manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(linkView.getText().toString());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Download file...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //nameView.getText().toString()
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "video.mp4");
        // request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameView.getText().toString()+"video.mp4");
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        // long reference = manager.enqueue(request);
    }

    public void DownloadMP4Button(View view){
        // File appDownloadDir = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File outputFile = new File(downloadDir, "converted_video.mp4");
        String localOutputPath = outputFile.getAbsolutePath();
        Log.d("DownloadMP4Button_(): link= ", link);
        Log.d("DownloadMP4Button_(): path= ", localOutputPath);

        if(!link.isEmpty()){
            VideoUtility.videoEncoderH264(this, link, localOutputPath);
            
        } else {
            Toast.makeText(DownloadActivity.this, "URL path is empty!", Toast.LENGTH_SHORT).show();
        }
    }

    //pending to check this method.
    /*private static DownloadManager Request getRequest(Uri uri) {
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Download file...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //nameView.getText().toString()
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "video.mp4");
        return request;
    }*/

    public void fileInfoScreen(View view){
        Intent intent = new Intent(DownloadActivity.this, FileInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void BackHomeButton(View view){
        Intent intent = new Intent(DownloadActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    void openVideo(String videoLink){
        playerView = findViewById(R.id.videoId);
        initializePlayer(videoLink);

           /*  Uri uri = Uri.parse(videoLink);
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
            videoView.setZOrderOnTop(true); */
    }

    private void initializePlayer(String videoUrl) {
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);

        player.setMediaItem(mediaItem);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentItem, playbackPosition);

        player.prepare();
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentItem = player.getCurrentMediaItemIndex();
            playWhenReady = player.getPlayWhenReady();

            // Critical step to avoid resource leaks and background audio hanging
            player.release();
            player = null;
        }
    }

    // Handle OS fragmentation and API 24+ lifecycle triggers properly
    @Override
    protected void onStart() {
        super.onStart();
        if (android.os.Build.VERSION.SDK_INT > 23) {
            initializePlayer(getLink());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT <= 23 || player == null) {
            initializePlayer(getLink());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (android.os.Build.VERSION.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (android.os.Build.VERSION.SDK_INT > 23) {
            releasePlayer();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JSONObject getMlbRawJson() {
        return mlbRawJson;
    }

    public void setMlbRawJson(JSONObject mlbRawJson) {
        this.mlbRawJson = mlbRawJson;
    }
}
