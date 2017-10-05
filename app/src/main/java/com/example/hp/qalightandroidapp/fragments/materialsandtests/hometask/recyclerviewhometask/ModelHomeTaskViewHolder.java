package com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;

import static com.example.hp.qalightandroidapp.Constants.GOOGLE_STANDARD_WITH_NO_PARAMS_SEARCH_QUERY;

/**
 * Created by hp on 014 14.09.2017.
 */

public class ModelHomeTaskViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public TextView textViewDescription;
    private View itemView;
    private String query;

    public ModelHomeTaskViewHolder(final View itemView) {
        super(itemView);
        // trick to connect itemView in listener
        this.itemView = itemView;


        textViewDescription = (TextView) itemView.findViewById(R.id.card_view_home_task_description);
        Constants.setTypefaceToTextView(textViewDescription, itemView.getContext());

        textViewTitle = (TextView) itemView.findViewById(R.id.card_view_home_task_title);
        Constants.setTypefaceToTextView(textViewTitle, itemView.getContext());

        // underline text
        textViewTitle.setPaintFlags(textViewTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = String.valueOf(textViewTitle.getText());

                // alternative way

                /*Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, query); // query contains search string
                itemView.getContext().startActivity(intent);*/

                Uri uri = Uri.parse(GOOGLE_STANDARD_WITH_NO_PARAMS_SEARCH_QUERY+query);
                Intent gSearchIntent = new Intent(Intent.ACTION_VIEW, uri);
                itemView.getContext().startActivity(gSearchIntent);
            }
        });
        // set here link as param
        //textViewDescription.setTextColor(getResources().getColor(R.color.colorGray));
    }
}
