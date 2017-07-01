package com.jurtz.marcel.blog_viciousdino.PostFragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
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
import com.jurtz.marcel.blog_viciousdino.Post;
import com.jurtz.marcel.blog_viciousdino.R;
import com.jurtz.marcel.blog_viciousdino.SettingsManager;

import java.util.List;
import java.util.Map;

public class FragNewPosts extends Fragment {

    // GUI
    ProgressDialog progressDialog;
    ListView postList;

    // VARIABLES
    String url;

    // to save fetched blog data
    List<Object> list;
    Gson gson;
    Map<String,Object> mapPost;
    Map<String,Object> mapTitle;
    String postTitle[];

    // to submit post id when selected
    int postID;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.frag_new_posts, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        postList = (ListView)getView().findViewById(R.id.postList);
        populateList();
    }

    private void populateList() {

        url = SettingsManager.newestTenPosts;

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                gson = new Gson();
                list = (List) gson.fromJson(s, List.class);
                postTitle = new String[list.size()];

                for(int i=0;i<list.size();++i){
                    mapPost = (Map<String,Object>)list.get(i);
                    mapTitle = (Map<String, Object>) mapPost.get("title");

                    postTitle[i] = (String) mapTitle.get("rendered");
                    // Replace HTML elements for colons
                    postTitle[i] = postTitle[i].replace(" &#8211;", ":");
                }

                postList.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, postTitle));
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
        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mapPost = (Map<String, Object>) list.get(position);
                postID = ((Double) mapPost.get("id")).intValue();

                Intent intent = new Intent(getActivity().getApplicationContext(), Post.class);
                intent.putExtra("id", "" + postID);
                startActivity(intent);
            }
        });
    }
}
