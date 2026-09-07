package com.project.mlbdownloadervideos;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class FileInfoActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fileinfo_activity);
        //setToolbar();
        //printResultScreen();

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
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