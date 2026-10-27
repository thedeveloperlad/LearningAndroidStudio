package com.example.filereader;

import java.util.regex.Pattern;

public class StringPatterns {
    // Regex to match the group-title attribute inside #EXTINF tags
    static Pattern groupPattern = Pattern.compile("group-title=\"([^\"]+)\"");

}
