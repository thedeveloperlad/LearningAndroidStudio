package com.project.mlbdownloadervideos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class FileInfoActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fileinfo_activity);
        //setToolbar();
        showFileInfoOnScreen();


        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    private void showFileInfoOnScreen() {
        Intent getIntent = getIntent();

        String mlbRawString = getIntent.getStringExtra("mlbJson").toString();

        if(!mlbRawString.isEmpty()) {
            try {
                JSONObject mlbRaw = new JSONObject(mlbRawString);

                // Log.d("data sample= ", Objects.requireNonNull(getIntent.getStringExtra("mlbJsonRaw")));

                TextView nameInfoVideoView = (TextView) findViewById(R.id.nameInfoVideo);
                TextView descriptionInfoVideoView = (TextView) findViewById(R.id.descriptionInfoVideo);
                TextView uploadDateInfoVideoView = (TextView) findViewById(R.id.uploadDateInfoVideo);
                TextView durationInfoVideoView = (TextView) findViewById(R.id.durationInfoVideo);
                TextView thumbnailUrlInfoVideoView = (TextView) findViewById(R.id.thumbnailUrlInfoVideo);

                nameInfoVideoView.setTextIsSelectable(false);

                nameInfoVideoView.setText(mlbRaw.get("name").toString());
                descriptionInfoVideoView.setText(mlbRaw.get("description").toString());
                uploadDateInfoVideoView.setText(mlbRaw.get("uploadDate").toString());
                durationInfoVideoView.setText(mlbRaw.get("duration").toString());
                thumbnailUrlInfoVideoView.setText(mlbRaw.get("thumbnailUrl").toString());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(FileInfoActivity.this, "Invalid Data...", Toast.LENGTH_SHORT).show();
        }
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