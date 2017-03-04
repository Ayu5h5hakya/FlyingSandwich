package com.example.ayush.flyingsandwich;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import static com.example.ayush.flyingsandwich.Provider.MusicDirectoryEngine.FORMAT;

/**
 * Created by Ayush on 3/1/2017.
 */

public class Util {
    public static String parseMusicFilename(String input) {
        int lastslash = input.lastIndexOf("/");
        return input.substring(lastslash + 1, input.length() - FORMAT.length());
    }

    public static String convertDurationToMinutes(float duration) {
        float seconds = duration / 1000;
        String durationinString = (seconds/60)%60+"."+(seconds%60);
        return durationinString.substring(0,4);
    }

    public static SpannableStringBuilder setSongDisplayTitle(String songName, String artistName) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString finalSongName = new SpannableString(songName);

        spannableStringBuilder.append(finalSongName);

        SpannableString finalArtistName = new SpannableString(" - " + artistName);
        finalArtistName.setSpan(new ForegroundColorSpan(Color.parseColor("#C2CAC9")), 0, finalArtistName.length(), 0);

        spannableStringBuilder.append(finalArtistName);

        return spannableStringBuilder;
    }
}
