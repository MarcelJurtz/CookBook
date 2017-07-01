package com.jurtz.marcel.blog_viciousdino.Settings;

public class AuthorManager {
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
}
