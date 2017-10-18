package com.example.hp.qalightandroidapp.fragments.materialsandtests.tests.recyclerviewtests;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.R;

import java.util.List;

/**
 * Created by hp on 012 12.09.2017.
 */


public class ModelTestsAdapter extends RecyclerView.Adapter<ModelTestsViewHolder> {
    private List<ModelTests> modelTestsList;

    public ModelTestsAdapter(List<ModelTests> modelTestsList)
    {
        this.modelTestsList = modelTestsList;
    }


    @Override
    public ModelTestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tests_card_view, parent, false);

        return new ModelTestsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ModelTestsViewHolder holder, int position) {
        ModelTests modelTests = modelTestsList.get(position);

        holder.textViewTitle.setText(modelTests.getTitle());
        holder.textViewShortDescription.setText(modelTests.getDescription());
        // set on click listener

    }

    @Override
    public int getItemCount() {
        return modelTestsList.size();
    }
}
