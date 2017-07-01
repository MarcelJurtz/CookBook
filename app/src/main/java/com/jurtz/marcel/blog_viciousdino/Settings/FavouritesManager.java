package com.jurtz.marcel.blog_viciousdino.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class FavouritesManager {
    private static final String preferences = "favourites";
    private static final String prefName = "favArray";


    // add article to favourites
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

    // remove article from favourites
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
        favString = TextUtils.join("-", newArray);
        prefs.edit().putString(prefName, favString).commit();
    }

    // check if article is favourite
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

    // get all favourite articles by id
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
}
