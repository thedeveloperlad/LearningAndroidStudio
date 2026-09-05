package com.project.mlbdownloadervideos;

import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.annotation.OptIn;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.transformer.EditedMediaItem;
import androidx.media3.transformer.Transformer;

public class VideoUtility {

    /*
    getVideoMimeTypeFromUri: Returns the exact MIME type registered in the media database
    * */
    public String getVideoMimeTypeFromUri(Context context, Uri uri) {
        return context.getContentResolver().getType(uri);
    }

    public String getVideoMIMETypeFromUrl(String url){
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.contains("#")) {
            url = url.substring(0, url.indexOf("#"));
        }

        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        String mimeType = null;

        if (extension != null && !extension.isEmpty()) {
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        }
        return mimeType;
    }

    public String getVideoExtensionFromUrl(String url){
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.contains("#")) {
            url = url.substring(0, url.indexOf("#"));
        }
        return MimeTypeMap.getFileExtensionFromUrl(url);
    }

    @OptIn(markerClass = UnstableApi.class)
    public static void videoEncoderH264(Context context, String inputPath, String outputPath){
        Uri sourceUri = Uri.parse(inputPath);
        MediaItem mediaItem = MediaItem.fromUri(sourceUri);

        // Request H.264/AVC by setting the videoMimeType
        EditedMediaItem editedMediaItem = new EditedMediaItem.Builder(mediaItem)
                .setFlattenForSlowMotion(true)
                .build();

        Transformer transformer = new Transformer.Builder(context)
                .setVideoMimeType(MimeTypes.VIDEO_H264)
                .build();

        transformer.start(editedMediaItem, outputPath);
    }
}
