package com.example.hp.qalightandroidapp.fragments.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.notifications.database_notifications.DatabaseHandler;
import com.example.hp.qalightandroidapp.fragments.notifications.database_notifications.ModelDatabaseNotification;
import com.example.hp.qalightandroidapp.fragments.notifications.recyclerviewnotifications.ModelNotifications;
import com.example.hp.qalightandroidapp.fragments.notifications.recyclerviewnotifications.ModelNotificationsAdapter;
import com.example.hp.qalightandroidapp.fragments.notifications.recyclerviewnotifications.ModelNotificationsRecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.hp.qalightandroidapp.Constants.setItemDecoration;

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ModelNotificationsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ModelNotifications> modelNotificationsList;
    private Intent i;
    private DatabaseHandler db;
    private List<ModelDatabaseNotification> notificationsList;

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

        processFromDataBase();


        mAdapter = new ModelNotificationsAdapter(modelNotificationsList);
        setItemDecoration(recyclerView, 1);

        recyclerView.setAdapter(mAdapter);
        addToRVITouchListener(recyclerView);


        return view;
    }

    private void addToRVITouchListener(RecyclerView recyclerView) // here we add touchListenerToRv
    {
        recyclerView.addOnItemTouchListener(new ModelNotificationsRecyclerViewClickListener(getContext(), new ModelNotificationsRecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO: handle clicks here
                 // trigger to reset notification's status
                notificationsList.get(position).set_status(1);
                db.updateNotification(notificationsList.get(position));

            }
        }));
    }

    private void processFromDataBase() // this method processes notifications and passes them to rv
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
                modelNotificationsList.add(new ModelNotifications(getString(R.string.app_name), notificationsList.get(i).get_description()));
            } else
                modelNotificationsList.add(new ModelNotifications(notificationsList.get(i).get_title(), notificationsList.get(i).get_description()));
           Log.d("Status", String.valueOf(notificationsList.get(i).get_status())); // here getting a notification


        }
    }

    private void handleItemClick() {
        i = new Intent();
    }

}
