package com.example.hp.qalightandroidapp.fragments.motivations;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MotivationsFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    CircleImageView circleImageView;
    List<ModalHistoryPersonal> modalHistoryPersonals = Constants.modalHistoryPersonal;

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

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
               view.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent(view.getContext(), MotivationsActivity.class);
                       intent.putExtra("name", modalHistoryPersonals.get(position).getName().toString());
                       intent.putExtra("position", modalHistoryPersonals.get(position).getPosition().toString());
                       intent.putExtra("history", modalHistoryPersonals.get(position).getHistory().toString());
                       intent.putExtra("personFoto", modalHistoryPersonals.get(position).getFoto().toString());
                       startActivity(intent);
                   }
               });
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return view;
    }

}
