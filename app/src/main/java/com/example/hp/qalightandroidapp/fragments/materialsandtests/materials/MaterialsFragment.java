package com.example.hp.qalightandroidapp.fragments.materialsandtests.materials;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerviewmaterials.ModelMaterials;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerviewmaterials.ModelMaterialsAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.hp.qalightandroidapp.Constants.setItemDecoration;

public class MaterialsFragment extends Fragment {

    private List<ModelMaterials> modelMaterialsList;
    private RecyclerView recyclerView;
    private ModelMaterialsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    public MaterialsFragment()
    {

    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materials, container, false);

        modelMaterialsList = new ArrayList<ModelMaterials>();

        recyclerView = (RecyclerView)view.findViewById(R.id.fragment_materials_recycler_view);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        setItemDecoration(recyclerView, 1);
        mAdapter = new ModelMaterialsAdapter(getData());
        recyclerView.setAdapter(mAdapter);
        return view;

    }

    private ArrayList<ModelMaterials> getData()
    {
        // get here some data using OKHTTP3

        modelMaterialsList.add(new ModelMaterials("What should evety tester know", "From 02/06/19"));
        modelMaterialsList.add(new ModelMaterials("What should evety tester know", "From 02/06/19"));
        modelMaterialsList.add(new ModelMaterials("What should evety tester know", "From 02/06/19"));
        modelMaterialsList.add(new ModelMaterials("What should evety tester know", "From 02/06/19"));
        modelMaterialsList.add(new ModelMaterials("What should evety tester know", "From 02/06/19"));
        modelMaterialsList.add(new ModelMaterials("What should evety tester know", "From 02/06/19"));

        return (ArrayList<ModelMaterials>) modelMaterialsList;
    }

}
