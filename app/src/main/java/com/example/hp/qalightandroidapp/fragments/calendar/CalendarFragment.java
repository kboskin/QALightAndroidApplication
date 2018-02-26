package com.example.hp.qalightandroidapp.fragments.calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.FixturesTabsFragment;
import com.example.hp.qalightandroidapp.helpers.serverdatagetter.DataGetterFromServer;
import com.example.hp.qalightandroidapp.helpers.serverdatagetter.DataParser;
import com.example.hp.qalightandroidapp.helpers.tinyDB.TinyStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.hp.qalightandroidapp.Constants.addSwipeRefresh;
import static com.example.hp.qalightandroidapp.Constants.parseDateAndHoursToProperFormat;
import static com.example.hp.qalightandroidapp.activities.MainActivity.getMainProgressBar;

/**
 * Created by root on 05.09.17.
 */

public class CalendarFragment extends android.support.v4.app.Fragment implements WeekView.EventClickListener {
    private WeekView mWeekView;
    private Context context;
    private String param = "calendar=";
    private String KEY = "Calendar";
    DataGetterFromServer dataGetterFromServer;
    ModelCalendar modelCalendar;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        param += prefs.getString(Constants.EXTRA_LOGIN_CODE, "123");
        getDataFromConnection();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        swipeRefreshLayout = view.findViewById(R.id.fragment_celendar_swipe_refresh_layout);
        context = container.getContext();
        mWeekView = view.findViewById(R.id.weekView);

        if (TinyStorage.retrieveList(getContext(), KEY, WeekViewEvent.class).isEmpty()) {
            events = getData();
        } else {
            events = (ArrayList<WeekViewEvent>) TinyStorage.retrieveList(getContext(), KEY, WeekViewEvent.class);/*getData();*/
            getMainProgressBar().setVisibility(View.INVISIBLE);
        }

        mWeekView.setMonthChangeListener(mMonthChangeListener);
        mWeekView.setOnEventClickListener(this);
        addSwipeRefresh(swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                events.clear();
                getDataFromConnection();

            }
        });

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
            //events = new ArrayList<WeekViewEvent>();

            try {
                dataGetterFromServer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            ArrayList<WeekViewEvent> eventsMonth = new ArrayList<WeekViewEvent>();
            for (int i = 0; i < events.size(); i++) {
                Log.e("EventsCalendar", ""+(newMonth-1));
                if (((events.get(i).getStartTime().get(Calendar.MONTH)) == (newMonth-1))) {
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
        Date date = new Date(event.getStartTime().getTime().getYear() + 1900, event.getStartTime().getTime().getMonth() + 1, event.getStartTime().getTime().getDay() + 3 + 14);

        //FixturesTabsFragment fixturesTabsFragment = new FixturesTabsFragment(date);
        FixturesTabsFragment fixturesTabsFragment = new FixturesTabsFragment(event.getStartTime().getTime().getYear()+1900, event.getStartTime().getTime().getMonth()+1, event.getStartTime().getTime().getDay()+14);

        Log.d("calendarDate", ""+(event.getStartTime().getTime().getYear()+1900)+(event.getStartTime().getTime().getMonth()+1)+(event.getStartTime().getTime().getDay()+14));

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

    private ArrayList<WeekViewEvent> getData() {
        getDataFromConnection();
        return events;
    }

    private void getDataFromConnection() {
        dataGetterFromServer = new DataGetterFromServer(Constants.QALight_URL_To_Connect, param, getContext(), new DataParser() {
            @Override
            public void parseResponse(String responseData) {
                try {
                    //Process the response Data
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject calendar = jsonArray.getJSONObject(i);
                        String dateStart = calendar.getString("date_lection_start");
                        String dateFinish = calendar.getString("date_lection_finish");

                        modelCalendar = new ModelCalendar(Integer.parseInt(calendar.getString("id")),
                                calendar.getString("name"),
                                parseDateAndHoursToProperFormat(dateStart)[0],
                                parseDateAndHoursToProperFormat(dateStart)[1],
                                parseDateAndHoursToProperFormat(dateStart)[2],
                                parseDateAndHoursToProperFormat(dateStart)[3],
                                parseDateAndHoursToProperFormat(dateStart)[4],
                                parseDateAndHoursToProperFormat(dateFinish)[0],
                                parseDateAndHoursToProperFormat(dateFinish)[1],
                                parseDateAndHoursToProperFormat(dateFinish)[2],
                                parseDateAndHoursToProperFormat(dateFinish)[3],
                                parseDateAndHoursToProperFormat(dateFinish)[4],
                                calendar.getString("type"));

                        Calendar startTime = Calendar.getInstance();
                        startTime.set(modelCalendar.getYear_start(), modelCalendar.getMonth_start()-1, modelCalendar.getDay_start(), modelCalendar.getHours_start(), modelCalendar.getMinut_start());
                        Calendar endTime = (Calendar) startTime.clone();
                        endTime.set(modelCalendar.getYear_end(), modelCalendar.getMonth_end()-1, modelCalendar.getDay_end(), modelCalendar.getHours_end(), modelCalendar.getMinut_end());
                        WeekViewEvent event = new WeekViewEvent(modelCalendar.getId(), modelCalendar.getName(), startTime, endTime);
                        event.setColor(Color.parseColor(modelCalendar.getColor()));
                        events.add(event);
                        /*modelHomeTaskList.add(0, modelCalendar);*/
                        Log.d("ResponseCalendar", calendar.getString("name"));
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(dateStart)[0]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(dateStart)[1]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(dateStart)[2]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(dateStart)[3]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(dateStart)[4]);
                        Log.d("ResponseCalendar", calendar.getString("name"));
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(dateFinish)[0]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(dateFinish)[1]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(dateFinish)[2]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(dateFinish)[3]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(dateFinish)[4]);


                    }
                    TinyStorage.storeList(getContext(), KEY, events);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // adapter recreation, for some reason notifyDataSetChanged doesnt work
                            mWeekView.notifyDatasetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                            // duplication avoiding (just removing all from the list)

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, swipeRefreshLayout);
        dataGetterFromServer.start();
    }

}
