package com.example.hp.qalightandroidapp.fragments.motivations.RecycleViewMotivations;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 08.09.17.
 */

public class AdapterRecycleViewMotivation extends RecyclerView.Adapter<AdapterRecycleViewMotivation.ViewHolder> {
    List<ModalHistoryPersonal> modalHistoryPersonals;
    private Context context;
    private int positionPrivate;


    public AdapterRecycleViewMotivation(List<ModalHistoryPersonal> modalHistoryPersonals, Context context) {
        this.modalHistoryPersonals = modalHistoryPersonals;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_motivations_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        this.positionPrivate = position;

        Picasso.with(holder.itemView.getContext())
                .load(modalHistoryPersonals.get(position).getFoto())
                .error(R.drawable.feature_error)
                .into(holder.personPhoto);
        holder.name.setText(modalHistoryPersonals.get(position).getName());
        holder.position.setText(modalHistoryPersonals.get(position).getPosition());
        holder.history.setText(modalHistoryPersonals.get(position).getHistory());

    }

    @Override
    public int getItemCount() {
        return modalHistoryPersonals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView personPhoto;
        TextView history;
        TextView name;
        TextView position;
        View view;

        public ViewHolder(final View itemView) {
            super(itemView);
            personPhoto = itemView.findViewById(R.id.person_image);
            history = itemView.findViewById(R.id.history_text);

            Constants.setTypefaceToTextView(history, itemView.getContext());
            name = itemView.findViewById(R.id.person_name);

            Constants.setTypefaceToTextView(name, itemView.getContext());
            position = itemView.findViewById(R.id.person_position);
            this.view = itemView;
        }
    }
}
