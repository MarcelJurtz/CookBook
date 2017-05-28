package com.jurtz.marcel.blog_viciousdino;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

public class SettingsManager {
    public static String url = "http://blog.vicious-dino.de/wp-json/wp/v2";
    public static String rtAllPosts = url + "/posts";

    public static String rtLastXPosts(int posts) {
        //return posts +
        return "";
    }

    public static String getAuthor(String id) {
        switch(id) {
            case  "2.0":
                return "Dominik";
            case  "4.0":
                return "Marcel";
            case  "5.0":
                return "Matt";
            default:
                return "Anonymous";
        }
    }

    public static String formatDate(String date) {
        date = date.replace("T"," ");
        date = date.substring(0,date.length()-3);
        return date;
    }

    // SHARED PREFERENCES

    private static final String preferences = "favourites";
    private static final String prefName = "favArray";


    public static void addFavourite(Context context, int id) {

        SharedPreferences prefs = context.getSharedPreferences(preferences, Context.MODE_PRIVATE);
        String favString = prefs.getString(prefName, "");

        /*
        int[] intFavArray;
        int strArrayLength = 0;
        if(favString != "") {
            String[] strFavArray = favString.split("-");
            int length = strFavArray.length + 1;
            strArrayLength = strFavArray.length;
            intFavArray = new int[length];
            for (int i = 0; i < strFavArray.length; i++) {
                intFavArray[i] = Integer.parseInt(strFavArray[i]);
            }
        } else {
            intFavArray = new int[1];
        }

        intFavArray[strArrayLength] = id;

        String newFavs = "";
        for(int i = 0; i < intFavArray.length; i++)
        {
            newFavs += intFavArray[0];
            if (i != intFavArray.length -1)
                newFavs += "-";
        }
        */

        if(favString != "") {
            favString = favString + "-" + id;
        } else {
            favString = id + "";
        }
        prefs.edit().putString(prefName, favString).commit();
    }

    public static boolean postIsFavourite(Context context, int id) {
        int[] favs = getFavourites(context);
        boolean contained = false;

        if(favs != null) {
            for(int i = 0; i < favs.length; i++) {
                if(favs[i] == id) {
                    contained = true;
                }
            }
        }

        return contained;
    }

    public static int[] getFavourites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(preferences, Context.MODE_PRIVATE);
        String favString = prefs.getString(prefName, "");

        if(favString != "") {
            String[] strFavArray = favString.split("-");
            int length = strFavArray.length + 1;
            int[] intFavArray = new int[length];
            for (int i = 0; i < strFavArray.length; i++) {
                intFavArray[i] = Integer.parseInt(strFavArray[i]);
            }
            return intFavArray;
        } else {
            return null;
        }
    }
}
