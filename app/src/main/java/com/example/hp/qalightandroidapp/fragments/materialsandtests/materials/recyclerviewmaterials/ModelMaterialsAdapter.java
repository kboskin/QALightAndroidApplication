package com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerviewmaterials;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.helpers.FileOpen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.hp.qalightandroidapp.Constants.DOWNLOAD_PATH;

/**
 * Created by hp on 008 08.09.2017.
 */

public class ModelMaterialsAdapter extends RecyclerView.Adapter<ModelMaterialsViewHolder> implements Filterable {
    private List<ModelMaterials> modelMaterialsList;
    private List<ModelMaterials> filtered;
    private DownloadManager downloadManager;
    private Context context;

    public ModelMaterialsAdapter(ArrayList<ModelMaterials> modelMaterialsList, Context context) {
        this.modelMaterialsList = new ArrayList<>(modelMaterialsList);
        this.filtered = new ArrayList<>(modelMaterialsList);
        this.context = context;
    }

    @Override
    public ModelMaterialsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_materials_card_view, parent, false);


        return new ModelMaterialsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ModelMaterialsViewHolder holder, int position) {
        final ModelMaterials modelMaterials = filtered.get(position);

        holder.textViewTitle.setText(modelMaterials.getTitle());
        holder.textViewDate.setText(modelMaterials.getDateInString());

        final String s = performSearch(modelMaterials.fileName);// check if file exists
        if (s != null) { // file is found and we have to open

            holder.downloadButton.setImageDrawable(context.getDrawable(R.drawable.open_file)); // set image "open file"

            holder.downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File file = new File(s);
                    try {
                        FileOpen.openFile(context, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /*String format = s.substring(s.length() - 3, s.length());
                    Log.d("Format", format);
                    Uri path = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setDataAndType(path, String.format("application/%s", format));

                    try {
                        context.startActivity(intent);
                    }
                    catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }*/
                }
            });
        } else { // file is not found, download
            holder.downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // download files
                    downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(modelMaterials.getUrl());

                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, modelMaterials.fileName); // stores file in download dir of android
                    Long reference = downloadManager.enqueue(request);

                    // setting image to button
                    holder.downloadButton.setImageDrawable(context.getDrawable(R.drawable.open_file));

                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return filtered.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filtered.clear();
            for (ModelMaterials item : modelMaterialsList) {
                Log.d("CalendarFilterDate", "" + item.getYear() + " "
                        + item.getMonth() + " "
                        + item.getDay());

                if (("" + item.getYear() + item.getMonth() + item.getDay()).equals(charSequence)) {
                    filtered.add(item);
                }
            }
            notifyDataSetChanged();
            return null;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        }
    };

    private String performSearch(String fileName) {
        File directory = new File(DOWNLOAD_PATH);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: " + files.length);
        for (int i = 0; i < files.length; i++) {
            if (fileName.equals(files[i].getName()))
                return files[i].getAbsolutePath();
        }
        return null;
    }
}
