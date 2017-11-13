package com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.DataGetterFromServer;
import com.example.hp.qalightandroidapp.fragments.DataParser;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask.ModelHomeTask;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask.ModelHomeTaskAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.hp.qalightandroidapp.Constants.QALight_URL_To_Connect;
import static com.example.hp.qalightandroidapp.Constants.addSwipeRefresh;
import static com.example.hp.qalightandroidapp.Constants.setItemDecoration;

public class HomeTaskFragment extends Fragment {


    private List<ModelHomeTask> modelHomeTaskList;
    private RecyclerView recyclerView;
    private ModelHomeTaskAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private Date dateFilter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String responseData;
    private final String param = "home=123";

    //private MyCustomAdapter adapter;

    public HomeTaskFragment() {

    }

    public HomeTaskFragment(Date dateFilter) {
        this.dateFilter = dateFilter;
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
        // swipe refresh layout
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_home_task_swipe_refresh_layout);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_materials_fragment_home_task_recycler_view);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        setItemDecoration(recyclerView, 1);

        mAdapter = new ModelHomeTaskAdapter(getData());

        recyclerView.setAdapter(mAdapter);

        if (dateFilter != null) {
            dateFilter.setYear(dateFilter.getYear() + 1900);
            dateFilter.setMonth(dateFilter.getMonth() + 1);
            dateFilter.setDate(dateFilter.getDate() + 23);
            Log.d("Calendar1", "" + dateFilter.getTime());
            Log.d("Calendar1", "" + dateFilter.getYear() + " " + dateFilter.getMonth() + " " + dateFilter.getDate());
            Log.d("Calendar2", "" + getData().get(0).getDate().getTime());
            Log.d("Calendar2", "" + getData().get(0).getDate().getYear() + " " + getData().get(0).getDate().getMonth() + " " + getData().get(0).getDate().getDate());
            mAdapter.getFilter().filter("" + dateFilter.getTime());
        }
        // swipe refresh is added here
        addSwipeRefresh(swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataGetterFromServer dataGetterFromServer = new DataGetterFromServer(QALight_URL_To_Connect, param, getContext(), new DataParser() {
                    @Override
                    public void parseResponse(String responseData) {
                        try {
                            //Process the response Data
                            JSONArray jsonArray = new JSONArray(responseData);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject home = jsonArray.getJSONObject(i);
                                String name = home.getString("title");
                                modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml(name), 2017, 9, 24));
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // adapter recreation, for some reason notifyDataSetChanged doesnt work
                                    mAdapter = new ModelHomeTaskAdapter((ArrayList<ModelHomeTask>) modelHomeTaskList);
                                    recyclerView.swapAdapter(mAdapter, true);
                                    // duplication avoiding (just removing all from the list)
                                    modelHomeTaskList.clear();
                                    // stop refreshing
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dataGetterFromServer.start();
            }
        });


        return view;
    }

    private ArrayList<ModelHomeTask> getData() {
        // get here some data using OKHTTP3

        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), 2017, 9, 24));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), 2017, 9, 20));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), 2017, 9, 11));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), 2017, 9, 22));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), 2017, 9, 23));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), 2017, 9, 24));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"), 2017, 9, 24));
        //startConnection(QALight_URL_To_Connect);

        return (ArrayList<ModelHomeTask>) modelHomeTaskList;
    }
}
