package com.rvsoftlab.kanoon.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rvsoftlab.kanoon.R;
import com.rvsoftlab.kanoon.adapter.PostAdapter;
import com.rvsoftlab.kanoon.helper.Constant;
import com.rvsoftlab.kanoon.materialradio.OnButtonCheckedChangeListener;
import com.rvsoftlab.kanoon.model.Posts;
import com.rvsoftlab.kanoon.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private AsyncHttpClient httpClient;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_post);
        db = FirebaseFirestore.getInstance();
        httpClient = new AsyncHttpClient();
        getAllPost();

        return view;
    }

    private void getAllPost() {
        RequestParams param = new RequestParams();
        param.put("tag","gpost");
        param.put("userid","5a7aa3bcc59393.67211717");

        httpClient.post(Constant.API,param,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Post",response.toString());
                try {
                    if (response.optBoolean("success",false)){
                        JSONArray array = response.getJSONArray("response");
                        List<Posts> posts = new ArrayList<>();
                        for (int i=0;i<array.length();i++){
                            Posts data = new Posts();
                            JSONObject obj = array.getJSONObject(i);
                            data.setCaption(obj.optString("caption",""));
                            data.setUuid(obj.optString("uuid",""));
                            data.setPostType(obj.optString("post_type",""));
                            data.setUrl(obj.optString("url",""));
                            data.setPostVisibility(obj.optString("post_visibility",""));
                            data.setLikes(obj.optInt("likes",0));
                            data.setComments(obj.optInt("comments",0));
                            User user = new User();
                            user.setUuid(obj.optString("user",""));
                            user.setUserName(obj.optString("user_name",""));
                            user.setUserMobile(obj.optString("mobile",""));
                            user.setUserImg(obj.optString("user_img",""));
                            data.setUser(user);
                            posts.add(data);
                        }
                        populateListView(posts);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Fail",errorResponse.toString());
            }

        });
    }

    private void populateListView(List<Posts> posts) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        List<Object> messageList = new ArrayList<>();
        messageList.add(Constant.VIEW_TYPE.NEW_POST);
        messageList.addAll(posts);
        recyclerView.setAdapter(new PostAdapter(getContext(),messageList));
    }

}
