package com.example.hp.qalightandroidapp.fragments.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.activities.PushNotificationActivity;
import com.example.hp.qalightandroidapp.fragments.notifications.database_notifications.DatabaseHandler;
import com.example.hp.qalightandroidapp.fragments.notifications.database_notifications.ModelDatabaseNotification;
import com.example.hp.qalightandroidapp.fragments.notifications.recyclerviewnotifications.ModelNotifications;
import com.example.hp.qalightandroidapp.fragments.notifications.recyclerviewnotifications.ModelNotificationsAdapter;
import com.example.hp.qalightandroidapp.fragments.notifications.recyclerviewnotifications.ModelNotificationsRecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.hp.qalightandroidapp.Constants.EXTRA_NOTIFICATION_DESCRIPTION;
import static com.example.hp.qalightandroidapp.Constants.EXTRA_NOTIFICATION_STATUS;
import static com.example.hp.qalightandroidapp.Constants.EXTRA_NOTIFICATION_TITLE;
import static com.example.hp.qalightandroidapp.Constants.setItemDecoration;

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ModelNotificationsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ModelNotifications> modelNotificationsList;
    private Intent intent;
    private DatabaseHandler db;
    private List<ModelDatabaseNotification> notificationsList;
    public View newView;


    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_notifications_rv);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        processFromDataBase(view);

        mAdapter = new ModelNotificationsAdapter(modelNotificationsList);
        setItemDecoration(recyclerView, 1);

        recyclerView.setAdapter(mAdapter);


        addToRVITouchListener(recyclerView, view);

        return view;
    }

    private void addToRVITouchListener(final RecyclerView recyclerView, View view) // here we add touchListenerToRv
    {
        recyclerView.addOnItemTouchListener(new ModelNotificationsRecyclerViewClickListener(getContext(),
                new ModelNotificationsRecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                notificationsList.get(position).set_status(1);
                // update query in row
                db.updateNotification(notificationsList.get(position));

                setToExtras(view, position);

            }
        }));
    }

    private void processFromDataBase(View view) // this method processes notifications and passes them to rv
    {
        // get all notifications from db
        db = new DatabaseHandler(getContext());
        notificationsList = db.getAllNotifications();


        // generate empty one to fill it
        modelNotificationsList = new ArrayList<>();
        // parse data from one list to another
        for (int i = 0; i < notificationsList.size(); i++) {

            // process if name of message is empty
            if (notificationsList.get(i).get_title() == null) {
                modelNotificationsList.add(new ModelNotifications(getString(R.string.app_name), notificationsList.get(i).get_description(), notificationsList.get(i).get_status()));
            } else
                modelNotificationsList.add(new ModelNotifications(notificationsList.get(i).get_title(), notificationsList.get(i).get_description(), notificationsList.get(i).get_status()));


        }
    }

    private void setToExtras(View view, int position) // method to set into extras
    {
        intent = new Intent(view.getContext(), PushNotificationActivity.class);

        // handle nullable title
        if (notificationsList.get(position).get_title() != null) {
            intent.putExtra(EXTRA_NOTIFICATION_TITLE, notificationsList.get(position).get_title());
        } else if (notificationsList.get(position).get_title() == null || notificationsList.get(position).get_title().equals("")){
            intent.putExtra(EXTRA_NOTIFICATION_TITLE, getString(R.string.app_name));
        }

        intent.putExtra(EXTRA_NOTIFICATION_DESCRIPTION, notificationsList.get(position).get_description());
        intent.putExtra(EXTRA_NOTIFICATION_STATUS, notificationsList.get(position).get_status());
        startActivity(intent);
    }


}
