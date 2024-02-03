package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView blog_list_view;
    //private List<BlogPost> blog_list;//comment kiya
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
   // private BlogRecyclerAdapter blogRecyclerAdapter;//comment kiya
    private DocumentSnapshot lastVisible;
    private Boolean isFirstPage=true;
    private String account_type,pincode,department,user_id;
    CollectionReference postsRef,userRef;

    public HomeFragment() {
        // Required empty public constructor
    }


}