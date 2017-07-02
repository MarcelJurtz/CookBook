package com.jurtz.marcel.blog_viciousdino.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.widget.TextView;
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
import com.jurtz.marcel.blog_viciousdino.Settings.PreferencesManager;
import com.jurtz.marcel.blog_viciousdino.Settings.URLManager;

import java.util.List;
import java.util.Map;

public class FragPostsByTags extends Fragment {

    // GUI
    ProgressDialog progressDialog;
    ListView postList;
    TextView txtTagsPostsTitle;

    // VARIABLES
    String url;
    Tag tag;

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
        return inflater.inflate(R.layout.frag_posts_by_tags, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        txtTagsPostsTitle = (TextView)getView().findViewById(R.id.txtTagsPostsTitle);

        Bundle bundle = this.getArguments();
        tag = new Tag();

        if (bundle != null) {
            tag.setID(bundle.getInt("tagID"));
            tag.setTitle(bundle.getString("tagName"));
        }

        postList = (ListView)getView().findViewById(R.id.postList);
        if(tag != null) {
            txtTagsPostsTitle.setText(tag.getTitle());
            populateList();
        } else {
            Toast.makeText(getView().getContext(), R.string.toast_no_tag, Toast.LENGTH_SHORT).show();
        }
    }

    private void populateList() {

        url = URLManager.getUrlPostsByTag(tag.getID());

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
                Tag tag = new Tag(((Double) mapPost.get("id")).intValue(), (String)mapPost.get("name"));

                Intent intent = new Intent(getActivity().getApplicationContext(), PostActivity.class);
                intent.putExtra("id", "" + tag.getID());
                startActivity(intent);
            }
        });
    }
}
