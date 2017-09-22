package com.example.hp.qalightandroidapp.fragments.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.WeekViewLoader;
import com.example.hp.qalightandroidapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by root on 05.09.17.
 */

public class CalendarFragment extends android.support.v4.app.Fragment implements WeekView.EventClickListener, MonthLoader.MonthChangeListener{
    private WeekView mWeekView;
    private Context context;
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
        mWeekView.setScrollListener(new WeekView.ScrollListener() {
            @Override
            public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
                //Toast.makeText(getActivity().getApplicationContext(), "not work", Toast.LENGTH_LONG).show();
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
            List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

            Calendar startTime = Calendar.getInstance();
            startTime.set(2017, 8, 24, 0, 23);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(2017, 8, 24, 2, 23);
            WeekViewEvent event = new WeekViewEvent(1, "HelloWorld", startTime, endTime);
            event.setColor(getResources().getColor(R.color.colorOrange));
            events.add(event);

            return events;
        }
    };

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

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


        return events;
    }
}
