package com.example.hp.qalightandroidapp.fragments.notifications.recyclerviewnotifications;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.R;

import java.util.List;

/**
 * Created by hp on 018 18.10.2017.
 */

public class ModelNotificationsAdapter extends RecyclerView.Adapter<ModelNotificationsViewHolder> {
    private List<ModelNotifications> modelNotificationsList;


    public ModelNotificationsAdapter(List<ModelNotifications> modelNotificationsList)
    {
        this.modelNotificationsList = modelNotificationsList;
    }
    @Override
    public ModelNotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notifications_card_view, parent, false);

        return new ModelNotificationsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ModelNotificationsViewHolder holder, int position) {
        ModelNotifications modelNotifications = modelNotificationsList.get(position);

        holder.model_notifications_text_view_title.setText(modelNotifications.getTitle());
        holder.model_notifications_text_view_description.setText(modelNotifications.getDescription());

        // processing if status is read
        if (modelNotifications.getStatus() == 1)
        {
            holder.notifications_image_view.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return modelNotificationsList.size();
    }
}
