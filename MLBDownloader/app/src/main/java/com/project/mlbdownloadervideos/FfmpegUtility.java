package com.project.mlbdownloadervideos;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/*import dev.ffmpegkit_maintained.ffmpegkit.FFmpegKit;
import dev.ffmpegkit_maintained.ffmpegkit.FFmpegSession;
import dev.ffmpegkit_maintained.ffmpegkit.ReturnCode;*/

/*import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.ReturnCode;*/
public class FfmpegUtility {

    FfmpegUtility() {

    }

    /* public void convertVideoToMp4(String inputPath, String outputPath) {
        // -y overrides the output file if it already exists
        // -c:v libx264 encodes the video to H.264 format
        // -c:a aac encodes the audio to AAC format
        // -movflags +faststart optimizes the mp4 for streaming/fast playback on mobile
        String ffmpegCommand = String.format("-y -i %s -c:v libx264 -c:a aac -movflags +faststart %s", inputPath, outputPath);

        Log.d("FFmpeg", "Starting conversion...");

        FFmpegKit.executeAsync(ffmpegCommand, session -> {
            ReturnCode returnCode = session.getReturnCode();

            if (ReturnCode.isSuccess(returnCode)) {
                Log.d("FFmpeg", "Conversion finished successfully!");
                // Update UI or notify user here
            } else if (ReturnCode.isCancel(returnCode)) {
                Log.d("FFmpeg", "Conversion was cancelled.");
            } else {
                Log.e("FFmpeg", String.format("Conversion failed with state %s and rc %s.", session.getState(), returnCode));
                // Print logs to see exactly why it failed
                Log.e("FFmpeg", session.getFailStackTrace());
            }
        }, log -> {
            // Optional: Track standard FFmpeg log output logs in real-time
            Log.d("FFmpegLog", log.getMessage());
        }, statistics -> {
            // Optional: Track execution statistics like current frame and time spent
            Log.d("FFmpegStats", "Time spent: " + statistics.getTime());
        });
    }*/

}
