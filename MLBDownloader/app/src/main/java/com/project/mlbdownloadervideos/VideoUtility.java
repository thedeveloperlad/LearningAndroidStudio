package com.project.mlbdownloadervideos;

import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

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
}
