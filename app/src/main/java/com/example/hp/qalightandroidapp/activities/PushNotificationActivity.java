package com.example.hp.qalightandroidapp.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.hp.qalightandroidapp.R;

import static com.example.hp.qalightandroidapp.Constants.EXTRA_NOTIFICATION_DESCRIPTION;
import static com.example.hp.qalightandroidapp.Constants.EXTRA_NOTIFICATION_TITLE;
import static com.example.hp.qalightandroidapp.Constants.setTypefaceToTextView;

public class PushNotificationActivity extends AppCompatActivity {

    private TextView textViewDescription;
    private TextView textViewNotificationsTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notification);

        setStatusBarColor();

        textViewNotificationsTitle = findViewById(R.id.textViewTitleNotifications);
        textViewNotificationsTitle.setText(getIntent().getStringExtra(EXTRA_NOTIFICATION_TITLE));
        setTypefaceToTextView(textViewNotificationsTitle, getApplicationContext());

        textViewDescription = findViewById(R.id.textViewNotificationDescription);
        textViewDescription.setText(getIntent().getStringExtra(EXTRA_NOTIFICATION_DESCRIPTION));
        setTypefaceToTextView(textViewDescription, getApplicationContext());
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }


}
