package com.example.hp.qalightandroidapp.fragments.motivations;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.activities.MotivationsActivity;
import com.example.hp.qalightandroidapp.fragments.motivations.RecycleViewMotivations.AdapterRecycleViewMotivation;
import com.example.hp.qalightandroidapp.fragments.motivations.RecycleViewMotivations.ModalHistoryPersonal;
import com.example.hp.qalightandroidapp.fragments.motivations.RecycleViewMotivations.RecyclerItemClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.hp.qalightandroidapp.Constants.setItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class MotivationsFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    CircleImageView circleImageView;
    List<ModalHistoryPersonal> modalHistoryPersonals = Constants.modalHistoryPersonal;
    private int positionPrivate;

    public MotivationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_motivations, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_motivations);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AdapterRecycleViewMotivation(modalHistoryPersonals, this.getContext());
        recyclerView.setAdapter(adapter);

        setItemDecoration(recyclerView, 1);


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemMotionEventListener() {
            @Override
            public void onItemClick(View view, int position) {
                positionPrivate = position;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), MotivationsActivity.class);
                        intent.putExtra("name", modalHistoryPersonals.get(positionPrivate).getName().toString());
                        intent.putExtra("position", modalHistoryPersonals.get(positionPrivate).getPosition().toString());
                        intent.putExtra("history", modalHistoryPersonals.get(positionPrivate).getHistory().toString());
                        intent.putExtra("personFoto", modalHistoryPersonals.get(positionPrivate).getFoto().toString());
                        startActivity(intent);
                    }
                });
            }



            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        return view;
    }

}
