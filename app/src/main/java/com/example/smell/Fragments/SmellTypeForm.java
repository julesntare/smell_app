package com.example.smell.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smell.R;
import com.example.smell.model.SmellTypesModal;
import com.example.smell.smell_db.DBHandler;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SmellTypeForm extends Fragment {
    private FirebaseFirestore db;
    TextInputEditText titleField, descField;
    SwitchMaterial sense;
    Button saveButton;
    private DBHandler dbHandler;
    int flag;
    final Handler handler = new Handler();

    public SmellTypeForm(int flag) {
        this.flag = flag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_smell_type_form, container, false);
        titleField = view.findViewById(R.id.titleField);
        descField = view.findViewById(R.id.descField);
        sense = view.findViewById(R.id.bad_or_good);
        saveButton = view.findViewById(R.id.saveButton);
        db = FirebaseFirestore.getInstance();
        dbHandler = new DBHandler(requireContext());
        dbHandler.open();

        saveButton.setOnClickListener(v -> {
            if (Objects.requireNonNull(titleField.getText()).toString().isEmpty()) {
                titleField.setError("Type is required");
                return;
            }
            if (Objects.requireNonNull(descField.getText()).toString().isEmpty()) {
                descField.setError("descriptions are required");
                return;
            }

            if (flag == 1) {
                saveToRemote(titleField.getText().toString(), descField.getText().toString(), sense.isChecked(), v);
                return;
            }

            long result = dbHandler.insert(titleField.getText().toString(), descField.getText().toString(), sense.isChecked());
            if (result > 0) {
                Snackbar snackbar = Snackbar.make(v, "Smell Type well added!!!", Snackbar.LENGTH_LONG);
                snackbar.show();

                handler.postDelayed(() -> {
                    // Do something after 2s = 2000ms
                    SmellLocalListFragment formFrag = new SmellLocalListFragment();
                    requireActivity().getSupportFragmentManager().beginTransaction().detach(this).commit();
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.thisView, formFrag, SmellTypeForm.class.getSimpleName())
                            .commit();
                }, 2000);
            } else {
                Snackbar snackbar = Snackbar.make(v, "Whoop! Something went wrong", Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.RED);
                snackbar.show();
            }
        });

        return view;
    }

    private void saveToRemote(String smellTitle, String smellDesc, boolean sense, View v) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbSmellTypes = db.collection("smell");

        // adding our data to our courses object class.
        SmellTypesModal smellTypes = new SmellTypesModal(smellTitle, smellDesc, sense ? 1 : 0, 1);

        // below method is use to add data to Firebase Firestore.
        dbSmellTypes.add(smellTypes).addOnSuccessListener(documentReference -> {
            Snackbar snackbar = Snackbar.make(v, "Yay! data saved successfully", Snackbar.LENGTH_LONG);
            snackbar.show();

            handler.postDelayed(() -> {
                // Do something after 2s = 2000ms
                SmellRemoteListFragment formFrag = new SmellRemoteListFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().detach(this).commit();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.remoteLayout, formFrag, SmellTypeForm.class.getSimpleName())
                        .commit();
            }, 2000);

        }).addOnFailureListener(e -> {
            Snackbar snackbar = Snackbar.make(v, "Whoops! something went wrong", Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(Color.RED);
            snackbar.show();
        });
    }
}
