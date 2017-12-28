package com.example.hp.qalightandroidapp.fragments.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.activities.MainActivity;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.FixturesTabsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 05.09.17.
 */

public class CalendarFragment extends android.support.v4.app.Fragment implements WeekView.EventClickListener, MonthLoader.MonthChangeListener {
    private WeekView mWeekView;
    private Context context;
    private String QALight_URL_To_Connect = "http://app.qalight.com.ua/?calendar=123";
    private String responseData = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        context = container.getContext();
        mWeekView = view.findViewById(R.id.weekView);
        mWeekView.setMonthChangeListener(mMonthChangeListener);
        mWeekView.setOnEventClickListener(this);
        //mWeekView.setWeekViewLoader();
        mWeekView.setScrollListener(new WeekView.ScrollListener() {
            @Override
            public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
                //Toast.makeText(getActivity().getApplicationContext(), "not work", Toast.LENGTH_LONG).show();
            }
        });
        getCalendarDataFromServer();
        Log.d("InHomeTask", "calendar");


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
        @Override
        public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
            // Populate the week view with some events.
            List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

            Calendar startTime = Calendar.getInstance();
            startTime.set(2017, 9, 28, 0, 23);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(2017, 9, 28, 2, 23);
            WeekViewEvent event = new WeekViewEvent(1, "HelloWorld", startTime, endTime);
            event.setColor(getResources().getColor(R.color.colorOrange));
            events.add(event);

            int newMoun = newMonth - 1;

            ArrayList<WeekViewEvent> eventsMonth = new ArrayList<WeekViewEvent>();
            for (int i = 0; i < events.size(); i++) {
                if (((events.get(i).getStartTime().get(Calendar.MONTH)) == (newMonth - 1))) {
                    eventsMonth.add(events.get(i));
                }
            }
            return eventsMonth;
        }
    };

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Log.i("onEventClick", event.getName());
        Toast.makeText(getContext(), event.getName() + " " + event.getStartTime().getTime().getDate(), Toast.LENGTH_LONG).show();
        Date date = new Date(event.getStartTime().getTime().getYear(), event.getStartTime().getTime().getMonth(), event.getStartTime().getTime().getDay());
        //FixturesTabsFragment fixturesTabsFragment = new FixturesTabsFragment(date);
        FixturesTabsFragment fixturesTabsFragment = new FixturesTabsFragment(date);

        getFragmentManager().beginTransaction().replace(R.id.frgmCont, fixturesTabsFragment).commit();
/*
        Bundle bundle = new Bundle();
        bundle.putLong("calendarDate", date.getTime());
        if(((MainActivity)getActivity()).fixturesTabsFragment == null) {
            ((MainActivity) getActivity()).fixturesTabsFragment = new FixturesTabsFragment();
        }
        //((MainActivity) getActivity()).fixturesTabsFragment.setArguments(bundle);
        ((MainActivity) getActivity()).fixturesTabsFragment.setDate(date);
        ((MainActivity)getActivity()).replaceWithFragment(((MainActivity) getActivity()).fixturesTabsFragment, ((MainActivity) getActivity()).handler);
*/

        //getFragmentManager().beginTransaction().replace(R.id.frgmCont, fixturesTabsFragment).commit();
    }



    private boolean checkInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Log.d(TAG, "checkInternet: " + "Connected to WIFI");
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Log.d(TAG, "checkInternet: " + "Connected to Mobile data");
            }
                return true;
            }
        }


            Log.d(TAG, "checkInternet: " + "Not connected");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), getString(R.string.internet_connection_failed), Toast.LENGTH_SHORT).show();
                }
            });

            return false;

    }

    private void getCalendarDataFromServer() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(QALight_URL_To_Connect)
                        .get()
                        .build();
                OkHttpClient client = new OkHttpClient();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (checkInternet(context)) {

                    if (response != null) {
                        if (response.code() == 200) {

                            try {
                                responseData = response.body().string();
                                JSONObject jsonObject = new JSONObject(responseData);
                                String id = (String) jsonObject.get("date_lection_start");
                                Log.e("idJSON", "" + id);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Process the response Data
                            Log.d("Calendar_Response_Data", responseData);
                        } else {
                            //Server problem
                            String responseData = null;
                            try {
                                responseData = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    } else {
                        Activity getActivity = (Activity) context;
                        getActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), getResources().getString(R.string.connection_problems), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            }
        });
        thread.start();

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        Calendar startTime = Calendar.getInstance();
        startTime.set(2017, 9, 24, 0, 23);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.set(2017, 9, 24, 2, 23);
        WeekViewEvent event = new WeekViewEvent(1, "HelloWorld", startTime, endTime);
        event.setColor(Resources.getSystem().getColor(R.color.colorOrange));
        events.add(event);

        ArrayList<WeekViewEvent> eventsMonth = new ArrayList<WeekViewEvent>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getStartTime().get(Calendar.MONTH) == newMonth) {
                eventsMonth.add(events.get(i));
            }
        }
        return eventsMonth;

    }

}
