package com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.helpers.listenerchangertoopenfile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.helpers.intentopener.FileOpen;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerviewmaterials.ModelMaterials;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerviewmaterials.ModelMaterialsViewHolder;

import java.io.File;
import java.io.IOException;

import static com.example.hp.qalightandroidapp.Constants.performSearch;

/**
 * Created by hp on 011 11.02.2018.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    ModelMaterialsViewHolder holder;
    ModelMaterials modelMaterials;

    public MyBroadcastReceiver(ModelMaterialsViewHolder holder, ModelMaterials modelMaterials) {
        this.holder = holder;
        this.modelMaterials = modelMaterials;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        // down click listener
        holder.downloadButton.setOnClickListener(null);

        // setting image to button
        holder.downloadButton.setImageDrawable(context.getDrawable(R.drawable.open_file));

        // add new click listener
        final String s = performSearch(modelMaterials.fileName);// check if file exists
        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(s);
                try {
                    FileOpen.openFile(context, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
