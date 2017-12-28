package com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask;

import android.content.SharedPreferences;
import android.annotation.SuppressLint;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.hp.qalightandroidapp.Constants.QALight_URL_To_Connect;
import static com.example.hp.qalightandroidapp.Constants.addSwipeRefresh;
import static com.example.hp.qalightandroidapp.Constants.parseDateToProperFormat;
import static com.example.hp.qalightandroidapp.Constants.setItemDecoration;
import static com.example.hp.qalightandroidapp.activities.MainActivity.getMainProgressBar;

public class HomeTaskFragment extends Fragment {


    private List<ModelHomeTask> modelHomeTaskList;
    private RecyclerView recyclerView;
    private ModelHomeTaskAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private Date dateFilter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String param = "home=";
    //private MyCustomAdapter adapter;

    public HomeTaskFragment() {

    }

    @SuppressLint("ValidFragment")
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

        mAdapter = new ModelHomeTaskAdapter(getData());

        recyclerView.setAdapter(mAdapter);


        Log.d("isHere", "isNotHere");


        // swipe refresh is added here
        addSwipeRefresh(swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dateFilter = null;
                modelHomeTaskList.clear();
                getDataFromConnection();
            }
        });

        return view;
    }

    private ArrayList<ModelHomeTask> getData() {
        getDataFromConnection();
        return (ArrayList<ModelHomeTask>) modelHomeTaskList;
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

                        ModelHomeTask mht = new ModelHomeTask(Html.fromHtml(home.getString("title")),
                                parseDateToProperFormat(date)[0],
                                parseDateToProperFormat(date)[1],
                                parseDateToProperFormat(date)[2]);
                        modelHomeTaskList.add(0, mht);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // adapter recreation, for some reason notifyDataSetChanged doesnt work
                            mAdapter = new ModelHomeTaskAdapter((ArrayList<ModelHomeTask>) modelHomeTaskList);
                            recyclerView.swapAdapter(mAdapter, true);

                            if (dateFilter != null) {
                                Log.d("isHere", "ishere");

                                Log.d("MHT", String.valueOf(modelHomeTaskList.size()));
                                for (ModelHomeTask mht : modelHomeTaskList)
                                {
                                    Log.d("MHT", String.valueOf(mht.getTitle()));
                                }
                                dateFilter.setYear(dateFilter.getYear() + 1900);
                                dateFilter.setMonth(dateFilter.getMonth() + 1);
                                dateFilter.setDate(dateFilter.getDate() + 22);
                                Log.d("CalendarFilter", ""+dateFilter.getTime());
                                Log.d("CalendarFilterMain", ""+dateFilter.getYear()+" "
                                        +dateFilter.getMonth()+" "
                                        +dateFilter.getDay());
                                mAdapter.getFilter().filter("" + dateFilter.getTime());

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
