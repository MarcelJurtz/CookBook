package com.jurtz.marcel.blog_viciousdino.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.jurtz.marcel.blog_viciousdino.Objects.Tag;
import com.jurtz.marcel.blog_viciousdino.R;
import com.jurtz.marcel.blog_viciousdino.Settings.URLManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragTags extends Fragment {

    // GUI
    ProgressDialog progressDialog;
    ListView lvTagList;

    // VARIABLES
    String url;

    // to save fetched blog data
    List<Object> list;
    Gson gson;
    List<Tag> tagList;
    String tagTitle[];
    Map<String, Object> mapPosts;

    // to submit post id when selected
    int postID;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.frag_tags, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        lvTagList = (ListView)getView().findViewById(R.id.lvTagList);
        tagList = new ArrayList<>();

        populateList();
    }

    private void populateList() {

        url = URLManager.tags;

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                gson = new Gson();
                list = (List) gson.fromJson(s, List.class);
                tagTitle = new String[list.size()];

                for(int i=0;i<list.size();++i){
                    mapPosts = (Map<String,Object>)list.get(i);

                    //Tag tag = new Tag(((Double)mapPosts.get("id")).intValue(),(String)mapPosts.get("Name"), (String)mapPosts.get("Link"));
                    Tag tag = new Tag(((Double)mapPosts.get("id")).intValue(),(String)mapPosts.get("name"), URLManager.getUrlPostsByTag((String) mapPosts.get("name")));
                    tagList.add(tag);
                    tagTitle[i] = (String)mapPosts.get("name");
                }

                lvTagList.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, tagTitle));
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "Some error occurred", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);

        // start activity for specific post
        lvTagList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mapPosts = (Map<String, Object>) list.get(position);
                Tag tag = new Tag(((Double) mapPosts.get("id")).intValue(), (String)mapPosts.get("name"), URLManager.getUrlPostsByTag((String) mapPosts.get("name")));

                Bundle bundle = new Bundle();
                bundle.putInt("tagID",tag.getID());
                bundle.putString("tagName", tag.getTitle());
                bundle.putString("tagUrl",tag.getUrl());

                Fragment fragment = new FragPostsByTags();
                fragment.setArguments(bundle);

                if (fragment != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
            }
        });
    }
}
