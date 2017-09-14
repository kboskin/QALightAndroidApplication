package com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask;

import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.hp.qalightandroidapp.R;

/**
 * Created by hp on 014 14.09.2017.
 */

public class ModelHomeTaskViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public TextView textViewDescription;


    public ModelHomeTaskViewHolder(View itemView) {
        super(itemView);


        textViewTitle = (TextView) itemView.findViewById(R.id.card_view_home_task_title);

        textViewDescription = (TextView) itemView.findViewById(R.id.card_view_home_task_description);

        textViewDescription.setMovementMethod(LinkMovementMethod.getInstance());
        // set here link as param
        //textViewDescription.setTextColor(getResources().getColor(R.color.colorGray));
    }
}
