package com.jurtz.marcel.blog_viciousdino;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

public class SettingsManager {
    public static String url = "http://blog.vicious-dino.de/wp-json/wp/v2";
    public static String allPostsUrl = url + "/posts";
    public static String posts = url + "/posts";
    public static String newestTenPosts = url + "/posts?filter[posts_per_page]=10&fields=id,title";
    public static String GetPageUrl(int page) {
        return posts + "?page=" + page;
    }

    public static String rtLastXPosts(int posts) {
        //return posts +
        return "";
    }

    public static String getAuthor(String id) {
        switch(id) {
            case  "2.0":
                return "Dominik";
            case "3.0":
                return "Manuel";
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

        if(favString != "") {
            favString = favString + "-" + id;
        } else {
            favString = id + "";
        }
        prefs.edit().putString(prefName, favString).commit();
    }

    public static void removeFavourite(Context context, int id) {
        SharedPreferences prefs = context.getSharedPreferences(preferences, Context.MODE_PRIVATE);
        String favString = prefs.getString(prefName, "");

        String[] strArray = favString.split("-");
        String[] newArray = new String[strArray.length -1];
        int iterator = 0;
        for(int i = strArray.length-1; i >= 0; i--) {
            if(Integer.parseInt(strArray[i]) != id) {
                newArray[iterator] = strArray[i];
                iterator++;
            }
        }
        favString = TextUtils.join("-",newArray);
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

        if(favString != "" && favString != null) {
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

    public static String fixString(String source) {
        source = source.replace("&#8211;","-");

        return source;
    }
}
