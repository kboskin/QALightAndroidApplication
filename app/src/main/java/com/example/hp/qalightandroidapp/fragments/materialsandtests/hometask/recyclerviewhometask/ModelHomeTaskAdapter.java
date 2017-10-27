package com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask;

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
 * Created by hp on 014 14.09.2017.
 */

public class ModelHomeTaskAdapter extends RecyclerView.Adapter<ModelHomeTaskViewHolder> implements Filterable {
    private List<ModelHomeTask> modelHomeTaskList;
    private List<ModelHomeTask> filtered;

    public ModelHomeTaskAdapter(ArrayList<ModelHomeTask> modelHomeTaskListCon) {
        this.modelHomeTaskList = new ArrayList<>(modelHomeTaskListCon);
        this.filtered = new ArrayList<>(modelHomeTaskListCon);
    }

    @Override
    public ModelHomeTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_task_card_view, parent, false);


        return new ModelHomeTaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ModelHomeTaskViewHolder holder, int position) {
        ModelHomeTask modelHomeTask = filtered.get(position);

        holder.textViewTitle.setText(modelHomeTask.getTitle());
        // snippet for displaying data in text view
        holder.textViewDate.setText(""+modelHomeTask.getDay() + "/" + modelHomeTask.getMonth() + "/"  + modelHomeTask.getYear());
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

            Log.d("Calendar", "Filter work");

            for(ModelHomeTask item : modelHomeTaskList){
                Log.d("CalendarFilter", ""+item.getDate().getTime());

                if((""+item.getDate().getTime()).equals(charSequence)){
                    filtered.add(item);
                }
            }
            Log.d("CalendarLst", "" + modelHomeTaskList.size());


            // I don't actually know what purpose to place it here, everything is fine without
            notifyDataSetChanged();
            return null;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        }
    };
}
