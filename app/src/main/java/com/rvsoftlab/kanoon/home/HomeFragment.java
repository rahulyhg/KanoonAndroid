package com.rvsoftlab.kanoon.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.rvsoftlab.kanoon.R;
import com.rvsoftlab.kanoon.materialradio.OnButtonCheckedChangeListener;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;

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

        //getAllPost();
        getFollowers();
        return view;
    }

    private void getFollowers() {
        CollectionReference ref = db.collection("users").document("8286903263").collection("followers");
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc : task.getResult().getDocuments()){
                        Map<String,Object> map = doc.getData();
                        String username = (String) map.get("username");
                        Log.d("UserName", username);
                        fetchPost(username);
                    }
                }
            }
        });
    }

    private void fetchPost(final String username) {
        CollectionReference ref = db.collection("posts").document(username).collection("posts");
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    for (final DocumentSnapshot doc : task.getResult().getDocuments()){
                        Log.d(doc.getId(),""+doc.getData());
                        //getComments(username, doc);
                        final CollectionReference comRef = db.collection("posts").document(username).collection("posts").document(doc.getId()).collection("comments");
                        comRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for (DocumentSnapshot comData : task.getResult().getDocuments()){
                                        Log.d("Com :"+comData.getId(),""+comData.getData()+" "+doc.getId());
                                    }
                                }
                            }
                        });

                    }
                    Log.d("TAG","out of loop");
                }
            }
        });

    }

    private void getComments(String username, DocumentSnapshot doc) {
        CollectionReference ref = db.collection("posts").document(username).collection("posts").document(doc.getId()).collection("comments");
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot data : task.getResult().getDocuments()){
                        Log.d(data.getId(),""+data.getData());
                    }
                }
            }
        });
    }

    private void getAllPost() {
        final CollectionReference ref = db.collection("posts");
        Query query = ref.limit(50);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e!=null){
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }
            }
        });
    }

}
