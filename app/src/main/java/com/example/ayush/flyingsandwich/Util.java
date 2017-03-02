package com.example.ayush.flyingsandwich;

import static com.example.ayush.flyingsandwich.Provider.MusicDirectoryEngine.FORMAT;

/**
 * Created by Ayush on 3/1/2017.
 */

public class Util {
    public static String parseMusicFilename(String input){
        int lastslash = input.lastIndexOf("/");
        return input.substring(lastslash+1,input.length()-FORMAT.length());
    }
}
