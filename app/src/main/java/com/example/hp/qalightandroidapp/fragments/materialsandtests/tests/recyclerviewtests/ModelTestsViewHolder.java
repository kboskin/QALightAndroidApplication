package com.example.hp.qalightandroidapp.fragments.materialsandtests.tests.recyclerviewtests;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;

/**
 * Created by hp on 012 12.09.2017.
 */


public class ModelTestsViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public Button startTestButton;
    public TextView textViewShortDescription;

    public ModelTestsViewHolder(View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.tests_fragment_card_view_title);

        Constants.setTypefaceToTextView(textViewTitle, itemView.getContext());
        startTestButton = itemView.findViewById(R.id.tests_fragment_card_view_start_test);

        textViewShortDescription = itemView.findViewById(R.id.test_fragment_text_view_description);
        Constants.setTypefaceToTextView(textViewShortDescription, itemView.getContext());


    }
}
