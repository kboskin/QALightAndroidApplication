package com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hp.qalightandroidapp.R;

/**
 * Created by hp on 008 08.09.2017.
 */

public class ModelMaterialsViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewTitle;
    public ImageButton downloadButton;
    public TextView textViewDate;

    public ModelMaterialsViewHolder(View itemView) {
        super(itemView);

        textViewDate = (TextView) itemView.findViewById(R.id.materials_fragment_card_view_text_view_date);

        downloadButton = (ImageButton) itemView.findViewById(R.id.materials_fragment_card_view_image_button);

        textViewTitle = (TextView) itemView.findViewById(R.id.materials_fragment_card_view_title);
    }
}
