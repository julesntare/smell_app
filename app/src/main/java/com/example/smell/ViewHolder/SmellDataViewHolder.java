package com.example.smell.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smell.R;
import com.example.smell.model.SmellTypesModal;

public class SmellDataViewHolder extends RecyclerView.ViewHolder {
    TextView smellId;
    TextView smellTitle;
    TextView smellDesc;
    TextView smellSense;
    Context context;

    public SmellDataViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        smellId = itemView.findViewById(R.id.smell_id);
        smellTitle = itemView.findViewById(R.id.smellTitle);
        smellDesc = itemView.findViewById(R.id.smellDesc);
        smellSense = itemView.findViewById(R.id.sense);
    }

    public void BindSmellData(final SmellTypesModal smellData) {
        System.out.println(smellData.getSense());
        smellId.setText(String.valueOf(smellData.getId()));
        smellTitle.setText(smellData.getSmellType());
        smellDesc.setText(smellData.getSmellDescriptions());
        smellSense.setText(smellData.getSense() ? "Good" : "Bad");
    }
}
