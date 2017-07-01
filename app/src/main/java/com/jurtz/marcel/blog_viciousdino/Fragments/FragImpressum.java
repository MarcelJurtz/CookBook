package com.jurtz.marcel.blog_viciousdino.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jurtz.marcel.blog_viciousdino.Activities.PostActivity;
import com.jurtz.marcel.blog_viciousdino.R;
import com.jurtz.marcel.blog_viciousdino.Settings.PreferencesManager;
import com.jurtz.marcel.blog_viciousdino.Settings.URLManager;

import java.util.List;
import java.util.Map;

public class FragImpressum extends Fragment {

    // GUI
    WebView wvContent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.frag_impressum, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        wvContent = (WebView)getView().findViewById(R.id.wvContent);
        wvContent.loadUrl("file:///android_asset/impressum.html");
    }

}