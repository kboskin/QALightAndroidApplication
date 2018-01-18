package com.example.hp.qalightandroidapp.fragments.calendar;

import android.content.Context;
import android.graphics.RectF;
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
import com.example.hp.qalightandroidapp.fragments.materialsandtests.FixturesTabsFragment;
import com.example.hp.qalightandroidapp.helpers.serverdatagetter.DataGetterFromServer;
import com.example.hp.qalightandroidapp.helpers.serverdatagetter.DataParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.hp.qalightandroidapp.Constants.parseDateAndHoursToProperFormat;

/**
 * Created by root on 05.09.17.
 */

public class CalendarFragment extends android.support.v4.app.Fragment implements WeekView.EventClickListener {
    private WeekView mWeekView;
    private Context context;
    private String QALight_URL_To_Connect = "http://app.qalight.com.ua/?calendar=123";
    DataGetterFromServer dataGetterFromServer;
    ModelCalendar mht;
    List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromConnection();
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
            //events = new ArrayList<WeekViewEvent>();

            try {
                dataGetterFromServer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int newMoun = newMonth - 1;

            ArrayList<WeekViewEvent> eventsMonth = new ArrayList<WeekViewEvent>();
            for (int i = 0; i < events.size(); i++) {
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
        Date date = new Date(event.getStartTime().getTime().getYear() + 1900, event.getStartTime().getTime().getMonth() + 1                                                                  , event.getStartTime().getTime().getDay() + 3 + 14);

        Log.d("DateTagY", String.valueOf(event.getStartTime().getTime().getYear()));
        Log.d("DateTagM", String.valueOf(event.getStartTime().getTime().getMonth()));
        Log.d("DateTagD", String.valueOf(event.getStartTime().getTime().getDay()));


        Log.d("DateObjTagY", String.valueOf(date.getYear()));
        Log.d("DateObjTagM", String.valueOf(date.getMonth()));
        Log.d("DateObjTagD", String.valueOf(date.getDay()));
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

    private void getData() {
        getDataFromConnection();
    }

    private void getDataFromConnection() {
        dataGetterFromServer = new DataGetterFromServer(QALight_URL_To_Connect, "", getContext(), new DataParser() {
            @Override
            public void parseResponse(String responseData) {
                try {
                    //Process the response Data
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject calendar = jsonArray.getJSONObject(i);
                        String datestart = calendar.getString("date_lection_start");
                        String datefinish = calendar.getString("date_lection_finish");

                        mht = new ModelCalendar(Integer.parseInt(calendar.getString("id")),
                                calendar.getString("name"),
                                parseDateAndHoursToProperFormat(datestart)[0],
                                parseDateAndHoursToProperFormat(datestart)[1],
                                parseDateAndHoursToProperFormat(datestart)[2],
                                parseDateAndHoursToProperFormat(datestart)[3],
                                parseDateAndHoursToProperFormat(datestart)[4],
                                parseDateAndHoursToProperFormat(datefinish)[0],
                                parseDateAndHoursToProperFormat(datefinish)[1],
                                parseDateAndHoursToProperFormat(datefinish)[2],
                                parseDateAndHoursToProperFormat(datefinish)[3],
                                parseDateAndHoursToProperFormat(datefinish)[4],
                                calendar.getString("type"));

                        Calendar startTime = Calendar.getInstance();
                        startTime.set(mht.getYear_end(), mht.getMonth_end()-1, mht.getDay_end(), mht.getHours_end(), mht.getMinut_end());
                        Calendar endTime = (Calendar) startTime.clone();
                        endTime.set(mht.getYear_start(), mht.getMonth_start()-1, mht.getDay_start(), mht.getHours_start(), mht.getMinut_start());
                        WeekViewEvent event = new WeekViewEvent(mht.getId(), mht.getName(), startTime, endTime);
                        Log.d("Event", ""+event.getName());
                        Log.d("Event", ""+event.getStartTime().getTimeInMillis());
                        Log.d("Event", ""+event.getEndTime().getTimeInMillis());
                        event.setColor(getResources().getColor(R.color.colorOrange));
                        events.add(event);
                        /*modelHomeTaskList.add(0, mht);*/
                        Log.d("ResponseCalendar", calendar.getString("name"));
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(datestart)[0]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(datestart)[1]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(datestart)[2]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(datestart)[3]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(datestart)[4]);
                        Log.d("ResponseCalendar", calendar.getString("name"));
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(datefinish)[0]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(datefinish)[1]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(datefinish)[2]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(datefinish)[3]);
                        Log.d("ResponseCalendar", ""+parseDateAndHoursToProperFormat(datefinish)[4]);


                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // adapter recreation, for some reason notifyDataSetChanged doesnt work

                            // duplication avoiding (just removing all from the list)

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
        dataGetterFromServer.start();
    }

}
