package com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask.ModelHomeTask;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask.ModelHomeTaskAdapter;
import com.example.hp.qalightandroidapp.helpers.serverdatagetter.DataGetterFromServer;
import com.example.hp.qalightandroidapp.helpers.serverdatagetter.DataParser;
import com.example.hp.qalightandroidapp.helpers.tinyDB.TinyStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.hp.qalightandroidapp.Constants.QALight_URL_To_Connect;
import static com.example.hp.qalightandroidapp.Constants.addSwipeRefresh;
import static com.example.hp.qalightandroidapp.Constants.parseDateToProperFormat;
import static com.example.hp.qalightandroidapp.Constants.setItemDecoration;
import static com.example.hp.qalightandroidapp.activities.MainActivity.getMainProgressBar;

public class HomeTaskFragment extends Fragment {


    private ArrayList<ModelHomeTask> modelHomeTaskList;
    private RecyclerView recyclerView;
    private ModelHomeTaskAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String param = "home=";
    private final String KEY = "HomeTask";
    //private MyCustomAdapter adapter;

    int filterYear;
    int filterMonth;
    int filterDay;

    public HomeTaskFragment() {

    }


    @SuppressLint("ValidFragment")
    public HomeTaskFragment(int filterYear, int filterMonth, int filterDay) {
        this.filterYear = filterYear;
        this.filterMonth = filterMonth;
        this.filterDay = filterDay;
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

        //get shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        param += prefs.getString(Constants.EXTRA_LOGIN_CODE, "123");

        modelHomeTaskList = new ArrayList<>();
        // swipe refresh layout
        swipeRefreshLayout = view.findViewById(R.id.fragment_home_task_swipe_refresh_layout);

        recyclerView = view.findViewById(R.id.fragment_materials_fragment_home_task_recycler_view);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        // progress bar to load data when oncreate, and internet is slow enough
        getMainProgressBar().setVisibility(View.VISIBLE);

        setItemDecoration(recyclerView, 1);

        // if storage is empty get data from server
        if (TinyStorage.retrieveList(getContext(), KEY, ModelHomeTask.class).isEmpty()) {
            modelHomeTaskList = getData();
        } else {

            getMainProgressBar().setVisibility(View.INVISIBLE);

            modelHomeTaskList = (ArrayList<ModelHomeTask>) TinyStorage.retrieveList(getContext(), KEY, ModelHomeTask.class);
        }

        mAdapter = new ModelHomeTaskAdapter(modelHomeTaskList);

        recyclerView.setAdapter(mAdapter);

        if (filterYear != 0) {
            mAdapter.getFilter().filter("" + filterYear + filterMonth + filterDay);
        }

        Log.d("isHere", "isNotHere");


        // swipe refresh is added here
        addSwipeRefresh(swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filterYear = 0;
                //mAdapter.getFilter().filter("");
                modelHomeTaskList.clear();
                getDataFromConnection();
            }
        });

        return view;
    }

    private ArrayList<ModelHomeTask> getData() {
        getDataFromConnection();
        return modelHomeTaskList;
    }

    private void getDataFromConnection() {
        DataGetterFromServer dataGetterFromServer = new DataGetterFromServer(QALight_URL_To_Connect, param, getContext(), new DataParser() {
            @Override
            public void parseResponse(String responseData) {
                try {
                    //Process the response Data
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject home = jsonArray.getJSONObject(i);
                        String date = home.getString("date");

                        ModelHomeTask mht = new ModelHomeTask(Html.fromHtml(home.getString("title")).toString(),
                                parseDateToProperFormat(date)[0],
                                parseDateToProperFormat(date)[1],
                                parseDateToProperFormat(date)[2]);
                        modelHomeTaskList.add(0, mht);
                    }
                    // this line saves data after refresh into prefs

                    Log.d("OurListInHomeTask", modelHomeTaskList.toString());
                    TinyStorage.storeList(getContext(), KEY, modelHomeTaskList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // adapter recreation, for some reason notifyDataSetChanged doesnt work
                            mAdapter = new ModelHomeTaskAdapter((ArrayList<ModelHomeTask>) modelHomeTaskList);
                            recyclerView.swapAdapter(mAdapter, true);

                            if (filterYear != 0) {
                                mAdapter.getFilter().filter("" + filterYear + filterMonth + filterDay);
                            }

                            // duplication avoiding (just removing all from the list)
                            modelHomeTaskList.clear();
                            // stop refreshing
                            swipeRefreshLayout.setRefreshing(false);
                            // make big loader invisible
                            getMainProgressBar().setVisibility(View.INVISIBLE);
                            Log.d("MHT152", String.valueOf(modelHomeTaskList.size()));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, swipeRefreshLayout);
        dataGetterFromServer.start();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}