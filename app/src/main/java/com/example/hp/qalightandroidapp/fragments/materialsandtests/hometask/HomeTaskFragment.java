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

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask.ModelHomeTask;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask.ModelHomeTaskAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.hp.qalightandroidapp.Constants.CHAR_SEQUENCE_FAILURE_VALUE_FOR_RESPONSE;
import static com.example.hp.qalightandroidapp.Constants.setItemDecoration;

public class HomeTaskFragment extends Fragment {


    private List<ModelHomeTask> modelHomeTaskList;
    private RecyclerView recyclerView;
    private ModelHomeTaskAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private Date dateFilter;

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

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_materials_fragment_home_task_recycler_view);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        setItemDecoration(recyclerView, 1);

        //Log.e("CalendarList", ""+getData().size());

        mAdapter = new ModelHomeTaskAdapter(getData());

        recyclerView.setAdapter(mAdapter);

        if (dateFilter != null) {
            dateFilter.setYear(dateFilter.getYear() + 1900);
            dateFilter.setMonth(dateFilter.getMonth() + 1);
            dateFilter.setDate(dateFilter.getDate() + 23);
            Log.d("Calendar1", "" + dateFilter.getTime());
            Log.d("Calendar1", ""+ dateFilter.getYear()+" "+ dateFilter.getMonth()+" "+ dateFilter.getDate());
            Log.d("Calendar2", "" + getData().get(0).getDate().getTime());
            Log.d("Calendar2", "" + getData().get(0).getDate().getYear() + " " + getData().get(0).getDate().getMonth() + " " + getData().get(0).getDate().getDate());
            mAdapter.getFilter().filter("" + dateFilter.getTime());
        }

        return view;
    }

    private ArrayList<ModelHomeTask> getData() {
        // get here some data using OKHTTP3

        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"),  2017, 9, 24));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"),  2017, 9, 20));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"),  2017, 9, 11));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"),  2017, 9, 22));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"),  2017, 9, 23));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"),  2017, 9, 24));
        modelHomeTaskList.add(new ModelHomeTask(Html.fromHtml("Fuc*ng feature with query search"),  2017, 9, 24));
        //startConnection(QALight_URL_To_Connect);

        return (ArrayList<ModelHomeTask>) modelHomeTaskList;
    }

    private void startConnection(final String param) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(Constants.QALight_URL_To_Connect + param)
                        .get()
                        .build();
                OkHttpClient client = new OkHttpClient();

                try {
                    // get the response
                    Response response = client.newCall(request).execute();
                    // get responseBody
                    String responseBody = response.body().string();
                    // form response body
                    JSONObject jsonObject = new JSONObject(responseBody);
                    // parse response
                    Log.d("JSON", jsonObject.toString());



                    if (responseBody.contains(CHAR_SEQUENCE_FAILURE_VALUE_FOR_RESPONSE)) {
                        // exception
                    } else {
                        // TODO: handle
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


}
