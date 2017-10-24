package com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask.ModelHomeTask;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask.ModelHomeTaskAdapter;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static com.example.hp.qalightandroidapp.Constants.setItemDecoration;

public class HomeTaskFragment extends Fragment {


    private List<ModelHomeTask> modelHomeTaskList;
    private RecyclerView recyclerView;
    private ModelHomeTaskAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private Date dateCalendar;

    //private MyCustomAdapter adapter;

    public HomeTaskFragment() {

    }

    public HomeTaskFragment(Date dateCalendar) {
        this.dateCalendar = dateCalendar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_task, container, false);

        modelHomeTaskList = new ArrayList<ModelHomeTask>();

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_materials_fragment_home_task_recycler_view);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        setItemDecoration(recyclerView, 1);

        //Log.e("CalendarList", ""+getData().size());

        mAdapter = new ModelHomeTaskAdapter(getData());

        recyclerView.setAdapter(mAdapter);

        if (dateCalendar != null) {
            dateCalendar.setYear(dateCalendar.getYear() + 1900);
            dateCalendar.setMonth(dateCalendar.getMonth() + 1);
            dateCalendar.setDate(dateCalendar.getDate() + 23);
            Log.d("Calendar1", "" + dateCalendar.getTime());
            Log.d("Calendar1", ""+dateCalendar.getYear()+" "+dateCalendar.getMonth()+" "+dateCalendar.getDate());
            Log.d("Calendar2", "" + getData().get(0).getDate().getTime());
            Log.d("Calendar2", "" + getData().get(0).getDate().getYear() + " " + getData().get(0).getDate().getMonth() + " " + getData().get(0).getDate().getDate());
            mAdapter.getFilter().filter("" + dateCalendar.getTime());
        }

        return view;
    }

    private ArrayList<ModelHomeTask> getData() {
        // get here some data using OKHTTP3

        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), "From 02/06/17", 2017, 9, 24));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), "From 02/06/17", 2017, 9, 20));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), "From 02/06/17", 2017, 9, 11));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), "From 02/06/17", 2017, 9, 22));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), "From 02/06/17", 2017, 9, 23));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), "From 02/06/17", 2017, 9, 24));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), "From 02/06/17", 2017, 9, 24));

        return (ArrayList<ModelHomeTask>) modelHomeTaskList;
    }


}
