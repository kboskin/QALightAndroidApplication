package com.example.hp.qalightandroidapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MotivationsActivity extends Activity {

    CircleImageView personFoto;
    TextView history;
    TextView name;
    TextView position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivations);

        personFoto = findViewById(R.id.person_image);
        history = findViewById(R.id.history_text);
        Constants.setTypefaceToTextView(history, getApplicationContext());

        name = findViewById(R.id.person_name);
        Constants.setTypefaceToTextView(history, getApplicationContext());

        position = findViewById(R.id.person_position);
        Constants.setTypefaceToTextView(position, getApplicationContext());


        Intent intent = getIntent();

        Log.i("putExtra", intent.getStringExtra("name"));
        Log.i("putExtra", intent.getStringExtra("position"));
        Log.i("putExtra", intent.getStringExtra("history"));

        name.setText(""+intent.getStringExtra("name"));
        position.setText(""+intent.getStringExtra("position"));
        history.setText(""+intent.getStringExtra("history"));

        Picasso.with(getApplicationContext())
                .load(""+intent.getStringExtra("personFoto"))
                .error(R.drawable.feature_error)
                .into(personFoto);
    }
}
