package com.example.hp.qalightandroidapp.fragments.materialsandtests.tests;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.tests.recyclerviewtests.ModelTests;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.tests.recyclerviewtests.ModelTestsAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.hp.qalightandroidapp.Constants.setItemDecoration;

public class TestsFragment extends Fragment {

    private List<ModelTests> modelTestsList;
    private RecyclerView recyclerView;
    private ModelTestsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    public TestsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tests, container, false);

        modelTestsList = new ArrayList<ModelTests>();

        recyclerView = (RecyclerView)view.findViewById(R.id.fragment_tests_recycler_view);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        setItemDecoration(recyclerView, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ModelTestsAdapter(getData());
        recyclerView.setAdapter(mAdapter);


        return view;
    }

    private List<ModelTests> getData()
    {
        modelTestsList.add(new ModelTests("First test", "This test will be good for bla-bla-bla"));
        modelTestsList.add(new ModelTests("Second test", "It is very useful information bla-bla-bla"));
        modelTestsList.add(new ModelTests("Third test", "Oh god it is such a bla-bla-bla"));
        modelTestsList.add(new ModelTests("Fourth test", "Come to our church bla-bla-bla"));

        return modelTestsList;
    }


}
