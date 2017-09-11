package com.example.hp.qalightandroidapp.fragments.motivations;

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
    List<ModalHistoryPersonal> modalHistoryPersonals = Constants.modalHistoryPersonal;

/*    public AdapterRecycleViewMotivation(List<ModalHistoryPersonal> modalHistoryPersonals) {
        this.modalHistoryPersonals = modalHistoryPersonals;
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_motivations_fragment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(holder.itemView.getContext())
                .load(modalHistoryPersonals.get(position).getFoto())
                .error(R.drawable.feature_error)
                .into(holder.personFoto);
        holder.name.setText(modalHistoryPersonals.get(position).getName());
        holder.position.setText(modalHistoryPersonals.get(position).getPosition());
        holder.history.setText(modalHistoryPersonals.get(position).getHistory());
    }

    @Override
    public int getItemCount() {
        return modalHistoryPersonals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView personFoto;
        TextView history;
        TextView name;
        TextView position;

        public ViewHolder(View itemView) {
            super(itemView);
            personFoto = itemView.findViewById(R.id.person_image);
            history = itemView.findViewById(R.id.history_text);
            name = itemView.findViewById(R.id.person_name);
            position = itemView.findViewById(R.id.person_position);
        }
    }
}
