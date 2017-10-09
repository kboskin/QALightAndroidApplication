package com.example.hp.qalightandroidapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;
import com.spark.submitbutton.SubmitButton;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.example.hp.qalightandroidapp.Constants.CHAR_SEQUENCE_FAILURE_VALUE_FOR_RESPONSE;
import static com.example.hp.qalightandroidapp.Constants.CHECK_IF_IS_AUTH_PASSED;


public class LoginActivity extends AppCompatActivity {

    private TextFieldBoxes loginCodeEditText;
    private SubmitButton personalCabButton;
    private TextView creditsTextView;
    private Intent intent;
    private boolean isLoggedIn;
    private SharedPreferences prefs;
    private TextView sloganTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create intent firstly, because we pass it via constructor, task because call outside from activity
        intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // lines makes activity to become full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        findViewsByIdsAndSetListeners();

    }

    private void findViewsByIdsAndSetListeners() {

        // slogan typeface
        sloganTextView = findViewById(R.id.slogan);
        Constants.setTypefaceToTextView(sloganTextView, getApplicationContext());
        //button must be defined first, because it is a param in constructor of Listener
        personalCabButton = findViewById(R.id.submit_button);
        // edit text for code on login page
        loginCodeEditText = findViewById(R.id.loginCodeEditText);
        setTextChangeListener();
        //set link method for textView
        creditsTextView = (findViewById(R.id.txt_credits));
        Constants.setTypefaceToTextView(creditsTextView, getApplicationContext());

        creditsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        creditsTextView.setTextColor(getResources().getColor(R.color.colorGray));

        personalCabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // change value to make AutoLogin

                if (loginCodeEditText.getText() != null || loginCodeEditText.getText().isEmpty()) {
                    startConnection(loginCodeEditText.getText());
                } else {
                    loginCodeEditText.setErrorColor(getResources().getColor(R.color.colorYellow));
                    loginCodeEditText.setError(getResources().getString(R.string.wrong_code));
                }


            }
        });
    }


    private void setTextChangeListener() {
        loginCodeEditText.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }


            @Override
            public void afterTextChanged(final Editable editable) {


            }
        });
    }

    private void startConnection(final String param) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(Constants.QALight_URL_To_Connect + param)
                        .get()
                        .build();
                OkHttpClient client = new OkHttpClient();

                try {
                    // get the response
                    Response response = client.newCall(request).execute();
                    // get responseBody
                    String responseBody = response.body().string();
                    // form response body
                    JSONObject jsonObject = new JSONObject(responseBody);
                    // parse response
                    String name = String.valueOf(jsonObject.get("name"));
                    String group = String.valueOf(jsonObject.get("group"));


                    if (responseBody.contains(CHAR_SEQUENCE_FAILURE_VALUE_FOR_RESPONSE)) {
                        // exception
                    } else {
                        setToPrefsLoggedInValue();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void setToPrefsLoggedInValue() {
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isLoggedIn = true;
        prefs.edit().putBoolean(CHECK_IF_IS_AUTH_PASSED, isLoggedIn).commit(); // islogin is a boolean value of your login status
        startActivity(intent);
        finish();
    }

}
