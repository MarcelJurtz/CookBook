package com.jurtz.marcel.blog_viciousdino;

public class SettingsManager {
    public static String url = "http://blog.vicious-dino.de/wp-json/wp/v2";
    public static String rtAllPosts = url + "/posts";

    public static String rtLastXPosts(int posts) {
        //return posts +
        return "";
    }

    public static String getAuthor(String id) {
        switch(id) {
            case  "4.0":
                return "Marcel";
            default:
                return "Anonymous";
        }
    }

    public static String formatDate(String date) {
        date = date.replace("T"," ");
        date = date.substring(0,date.length()-3);
        return date;
    }
}
