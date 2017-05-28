package com.jurtz.marcel.blog_viciousdino;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import java.util.Map;

public class Post extends AppCompatActivity {

    TextView title;
    TextView info;
    WebView content;
    ProgressDialog progressDialog;
    Gson gson;
    Map<String, Object> mapPost;
    Map<String, Object> mapTitle;
    Map<String, Object> mapContent;
    Double authorID;
    String publishDate;

    int postId;

    boolean isFavourite = false;

    Toolbar menuBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        menuBar = (Toolbar) findViewById(R.id.postMenuBar);
        menuBar.setTitle("Vicious Dino - Blog");
        setSupportActionBar(menuBar);

        final String id = getIntent().getExtras().getString("id");
        postId = Integer.parseInt(id);

        if(SettingsManager.postIsFavourite(getApplicationContext(), postId)) {
            isFavourite = true;
        }

        final String imageResize = "<style>img{display: inline; height: auto; max-width: 100%;}</style>";

        title = (TextView)findViewById(R.id.title);
        content = (WebView)findViewById(R.id.content);
        info = (TextView)findViewById(R.id.txtAuthorInfo);

        progressDialog = new ProgressDialog(Post.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final String url = SettingsManager.rtAllPosts + "/" + id + "?fields=title,content";
        final String readMoreTag =
                "<div id=\"pressrelease-link-"+id+"\" class=\"sh-link pressrelease-link sh-hide\">"
                +"<a href=\"#\" onclick=\"showhide_toggle('pressrelease', "+id+", 'Show full article', 'Hide article'); return false;\" aria-expanded=\"false\">"
                +"<span id=\"pressrelease-toggle-"+id+"\">Show full article</span>"
                +"</a>"
                +"</div>"
                +"<div id=\"pressrelease-content-"+id+"\" class=\"sh-content pressrelease-content sh-hide\" style=\"display: none;\">";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                gson = new Gson();
                mapPost = (Map<String, Object>) gson.fromJson(s, Map.class);
                mapTitle = (Map<String, Object>) mapPost.get("title");
                mapContent = (Map<String, Object>) mapPost.get("content");
                authorID = Double.parseDouble(mapPost.get("author").toString());
                publishDate = mapPost.get("date").toString();

                String strContent = mapContent.get("rendered").toString();
                strContent = strContent.replace(readMoreTag, "");

                title.setText(mapTitle.get("rendered").toString());
                publishDate = SettingsManager.formatDate(publishDate);
                info.setText(publishDate + " by " + SettingsManager.getAuthor(authorID.toString()));
                // content.loadData(imageResize + strContent, "text/html", "UTF-8");
                content.loadData(imageResize + strContent, "text/html; charset=utf-8", "utf-8");

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(Post.this, id, Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Post.this);
        rQueue.add(request);
    }

    /* Handle actionbar button click

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                isFavourite = !isFavourite;
                if(isFavourite) {
                    menuBar.getMenu().findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_outline_24dp);
                } else {
                    menuBar.getMenu().getItem(0).setIcon(R.drawable.ic_favorite_24dp);
                    SettingsManager.addFavourite(getApplicationContext(), postId);
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
