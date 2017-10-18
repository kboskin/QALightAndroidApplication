package com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerviewmaterials;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.R;

import java.util.List;

/**
 * Created by hp on 008 08.09.2017.
 */

public class ModelMaterialsAdapter extends RecyclerView.Adapter<ModelMaterialsViewHolder> {
    private List<ModelMaterials> modelMaterialsList;

    @Override
    public ModelMaterialsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_materials_card_view, parent, false);


        return new ModelMaterialsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ModelMaterialsViewHolder holder, int position) {
        ModelMaterials modelMaterials = modelMaterialsList.get(position);

        holder.textViewTitle.setText(modelMaterials.getTitle());
        holder.textViewDate.setText(modelMaterials.getDateInString());
        // set on click listener to open in new activity
    }


    @Override
    public int getItemCount() {
        return modelMaterialsList.size();
    }

    public ModelMaterialsAdapter(List<ModelMaterials> modelMaterialsList) {
        this.modelMaterialsList = modelMaterialsList;
    }
}
