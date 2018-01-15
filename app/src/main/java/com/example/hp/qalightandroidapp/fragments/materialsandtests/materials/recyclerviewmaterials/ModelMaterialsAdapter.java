package com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerviewmaterials;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.hp.qalightandroidapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 008 08.09.2017.
 */

public class ModelMaterialsAdapter extends RecyclerView.Adapter<ModelMaterialsViewHolder> implements Filterable{
    private List<ModelMaterials> modelMaterialsList;
    private List<ModelMaterials> filtered;


    public ModelMaterialsAdapter(ArrayList<ModelMaterials> modelMaterialsList) {
        this.modelMaterialsList = new ArrayList<ModelMaterials>(modelMaterialsList);
        this.filtered = new ArrayList<>(modelMaterialsList);
    }

    @Override
    public ModelMaterialsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_materials_card_view, parent, false);


        return new ModelMaterialsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ModelMaterialsViewHolder holder, int position) {
        ModelMaterials modelMaterials = filtered.get(position);

        holder.textViewTitle.setText(modelMaterials.getTitle());
        holder.textViewDate.setText(modelMaterials.getDateInString());
        // set on click listener to open in new activity
    }


    @Override
    public int getItemCount() {
        return filtered.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filtered.clear();
            for (ModelMaterials item : modelMaterialsList) {
                Log.d("CalendarFilter", ""+item.getDate().getTime());
                Log.d("CalendarFilterDate", ""+item.getDate().getYear()+" "
                        +item.getDate().getMonth()+" "
                        +item.getDate().getDay());

                if((""+item.getDate().getTime()).equals(charSequence)){
                    filtered.add(item);
                }
            }
            notifyDataSetChanged();
            return null;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        }
    };
}
