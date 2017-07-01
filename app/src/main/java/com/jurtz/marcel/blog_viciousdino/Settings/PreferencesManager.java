package com.jurtz.marcel.blog_viciousdino.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

public class PreferencesManager {
    public static String getReadmoreTag(String id) {
        return "<div id=\"pressrelease-link-"+id+"\" class=\"sh-link pressrelease-link sh-hide\">"
                +"<a href=\"#\" onclick=\"showhide_toggle('pressrelease', "+id+", 'Show full article', 'Hide article'); return false;\" aria-expanded=\"false\">"
                +"<span id=\"pressrelease-toggle-"+id+"\">Show full article</span>"
                +"</a>"
                +"</div>"
                +"<div id=\"pressrelease-content-"+id+"\" class=\"sh-content pressrelease-content sh-hide\" style=\"display: none;\">";
    }


    public static String formatDate(String date) {
        date = date.replace("T"," ");
        date = date.substring(0,date.length()-3);
        return date;
    }


    public static String fixString(String source) {
        source = source.replace("&#8211;","-");

        return source;
    }
}
