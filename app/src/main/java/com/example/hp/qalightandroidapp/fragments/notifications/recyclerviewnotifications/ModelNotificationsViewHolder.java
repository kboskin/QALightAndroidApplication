package com.example.hp.qalightandroidapp.fragments.notifications.recyclerviewnotifications;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;

/**
 * Created by hp on 018 18.10.2017.
 */

public class ModelNotificationsViewHolder extends RecyclerView.ViewHolder {
    public TextView model_notifications_text_view_title;
    public TextView model_notifications_text_view_description;


    public ModelNotificationsViewHolder(View itemView) {
        super(itemView);


        model_notifications_text_view_description = itemView.findViewById(R.id.fragment_notifications_card_view_text_view_description);
        Constants.setTypefaceToTextView(model_notifications_text_view_description, itemView.getContext());

        model_notifications_text_view_title = itemView.findViewById(R.id.fragment_notifications_card_view_text_view_title);
        Constants.setTypefaceToTextView(model_notifications_text_view_title, itemView.getContext());


    }
}
