package com.example.hp.qalightandroidapp.fragments.materialsandtests.materials;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerviewmaterials.ModelMaterials;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerviewmaterials.ModelMaterialsAdapter;
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

public class MaterialsFragment extends Fragment {

    private ArrayList<ModelMaterials> modelMaterialsList;
    private RecyclerView recyclerView;
    private ModelMaterialsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private String param = "files=";
    SwipeRefreshLayout swipeRefreshLayout;
    private String KEY = "Materials";

    int filterYear;
    int filterMonth;
    int filterDay;


    public MaterialsFragment() {

    }

    @SuppressLint("ValidFragment")
    public MaterialsFragment(int year, int mounth, int day) {
        this.filterYear = year;
        this.filterMonth = mounth;
        this.filterDay = day;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materials, container, false);

        // progress bar to load data when oncreate, and internet is slow enough
        getMainProgressBar().setVisibility(View.VISIBLE);

        //get shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        param += prefs.getString(Constants.EXTRA_LOGIN_CODE, "123");

        modelMaterialsList = new ArrayList<>();

        swipeRefreshLayout = view.findViewById(R.id.fragment_materials_swipe_refresh_layout);

        recyclerView = view.findViewById(R.id.fragment_materials_recycler_view);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        setItemDecoration(recyclerView, 1);

        if (TinyStorage.retrieveList(getContext(), KEY, ModelMaterials.class).isEmpty()) {
            modelMaterialsList = getData();
        } else {
            modelMaterialsList = (ArrayList<ModelMaterials>) TinyStorage.retrieveList(getContext(), KEY, ModelMaterials.class);/*getData();*/
            getMainProgressBar().setVisibility(View.INVISIBLE);
        }
        mAdapter = new ModelMaterialsAdapter(modelMaterialsList, getContext());
        recyclerView.setAdapter(mAdapter);

        if (filterYear != 0) {
            mAdapter.getFilter().filter("" + filterYear + filterMonth + filterDay);
        }

        // swipe refresh is added here
        addSwipeRefresh(swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filterYear = 0;
                modelMaterialsList.clear();
                getDataFromConnection();
            }
        });

/*
        TinyDB tinyDB = new TinyDB(getContext());
        tinyDB.putListObject("HeyBro", tinyDB.castModelMaterialsList(modelMaterialsList));
        ArrayList<Object> objects = tinyDB.getListObject("HeyBro", ModelMaterials.class);
        Log.d("SomeTagMHT", objects.toString());*/
        return view;
    }

    private ArrayList<ModelMaterials> getData() {
        // get here some data using OKHTTP3
        getDataFromConnection();
        return modelMaterialsList;
    }

    private void getDataFromConnection() {
        DataGetterFromServer dataGetterFromServer = new DataGetterFromServer(QALight_URL_To_Connect, param, getContext(), new DataParser() {
            @Override
            public void parseResponse(String responseData) {
                try {
                    //Process the response Data
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject material = jsonArray.getJSONObject(i);

                        Log.d("material", String.valueOf(material));
                        String date = material.getString("data").substring(0, 10);// trim because Danya is fucktar, date is not in proper format (;/;/; hh:mm:ss)

                        ModelMaterials modelMaterial = new ModelMaterials(material.getString("title"),
                                parseDateToProperFormat(date)[0],
                                parseDateToProperFormat(date)[1],
                                parseDateToProperFormat(date)[2],
                                material.getString("url_file"));


                        modelMaterialsList.add(modelMaterial);
                    }
                    // this will save list
                    TinyStorage.storeList(getContext(), KEY, modelMaterialsList);
                    Log.d("modelMaterials", modelMaterialsList.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // adapter recreation, for some reason notifyDataSetChanged doesnt work
                            mAdapter = new ModelMaterialsAdapter(modelMaterialsList, getContext());
                            recyclerView.swapAdapter(mAdapter, true);

                            // duplication avoiding (just removing all from the list)
                            //modelMaterialsList.clear();
                            // stop refreshing
                            swipeRefreshLayout.setRefreshing(false);
                            // make big loader invisible
                            getMainProgressBar().setVisibility(View.INVISIBLE);
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