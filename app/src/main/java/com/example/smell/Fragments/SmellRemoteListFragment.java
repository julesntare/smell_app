package com.example.smell.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smell.Adapters.SmellDataAdapter;
import com.example.smell.R;
import com.example.smell.model.SmellTypesModal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class SmellRemoteListFragment  extends Fragment {

    private FirebaseFirestore db;
    RecyclerView userDataRecyclerView;
    ArrayList<SmellTypesModal> smellDataArrayList;
    SmellDataAdapter smellDataAdapter;
    FloatingActionButton fabRemote;
    TextView noData;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.smell_remote_list_fragment, viewGroup, false);
        smellDataArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        userDataRecyclerView = view.findViewById(R.id.smellrecyclerview);
        fabRemote = view.findViewById(R.id.fab_remote);
        noData = view.findViewById(R.id.no_data);

        fabRemote.setOnClickListener(v-> {
            SmellTypeForm formFrag= new SmellTypeForm(1);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.remoteLayout, formFrag, SmellTypeForm.class.getSimpleName())
                    .commit();
        });

        db.collection("smell")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        noData.setVisibility(View.GONE);
                        smellDataArrayList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            smellDataArrayList.add(new SmellTypesModal(Objects.requireNonNull(document.getData().get("smellType")).toString(), Objects.requireNonNull(document.getData().get("smellDescriptions")).toString(), (boolean) document.getData().get("sense") ? 1 : 0, smellDataArrayList.size()));
                            smellDataAdapter = new SmellDataAdapter(smellDataArrayList, getContext());
                            userDataRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            userDataRecyclerView.setVerticalScrollBarEnabled(true);
                            smellDataAdapter.notifyDataSetChanged();
                            userDataRecyclerView.setAdapter(smellDataAdapter);
                        }
                    } else {
                        System.out.println("Error getting documents: " + task.getException());
                    }
                });

        return view;
    }

}