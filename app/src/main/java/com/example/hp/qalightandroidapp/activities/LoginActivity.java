package com.example.hp.qalightandroidapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.hp.qalightandroidapp.Constants;
import com.example.hp.qalightandroidapp.R;
import com.spark.submitbutton.SubmitButton;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.example.hp.qalightandroidapp.Constants.CHECK_IF_IS_AUTH_PASSED;


public class LoginActivity extends AppCompatActivity {


    //private OkHttpClient client = new OkHttpClient();

    private TextFieldBoxes loginCodeEditText;
    private SubmitButton personalCabButton;
    private TextView creditsTextView;
    private Intent intent;
    private boolean isLoggedIn;
    private SharedPreferences prefs;

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
        //button must be defined first, because it is a param in constructor of Listener
        personalCabButton = findViewById(R.id.submit_button);

        // edit text for code on login page
        loginCodeEditText = findViewById(R.id.loginCodeEditText);
        setTextChangeListener();

        //set link method for textView
        creditsTextView = (findViewById(R.id.txt_credits));
        creditsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        creditsTextView.setTextColor(getResources().getColor(R.color.colorGray));
    }

    private void setTextChangeListener() {
        loginCodeEditText.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}



            @Override
            public void afterTextChanged(Editable editable) {

                /*Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RequestBody formBody = new FormBody.Builder()
                                .add("id", "123456789")
                                .build();
                        Request request = new Request.Builder()
                                .url(QALight_URL_To_Connect + 12345)
                                //.get//pasha is fucktar
                                .get()
                                .build();


                        OkHttpClient client = new OkHttpClient();

                        Response response = null;
                        try {
                            response = client.newCall(request).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (response.code() == 200) {
                            String responseData = null;
                            try {
                                responseData = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //Process the response Data
                            Log.d("SOMETAG", responseData);
                        } else {
                            //Server problem
                            String responseData = null;
                            try {
                                responseData = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("SOMETAG", responseData);
                        }
                    }
                });
                thread.start();*/



                Log.d("Here ", "1");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Request request = new Request.Builder()
                                .url(Constants.QALight_URL_To_Connect + "777")
                                .get()
                                .build();


                        OkHttpClient client = new OkHttpClient();

                        Response response = null;
                        try {
                            response = client.newCall(request).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (response.code() == 200) {
                            String responseData = null;
                            try {
                                responseData = response.body().string();

                                Log.d("Response", String.valueOf(response.code()));

                                Log.d("Response", response.toString());

                                Log.d("Response", response.body().toString());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //Process the response Data
                            Log.d("Tagone", responseData);
                        } else {
                            //Server problem
                            String responseData = null;
                            try {
                                responseData = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("Tagtwo", responseData);
                        }

                    }
                });
                thread.start();




                if (editable.toString().equals("correct")) {
                    // resetting error's color to make user friendly UI
                    loginCodeEditText.setErrorColor(getResources().getColor(R.color.colorGreen));
                    loginCodeEditText.setError(getResources().getString(R.string.correct_code));
                    personalCabButton.setVisibility(View.VISIBLE);

                    personalCabButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // change value to make AutoLogin
                            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            isLoggedIn = true;
                            prefs.edit().putBoolean(CHECK_IF_IS_AUTH_PASSED, isLoggedIn).commit(); // islogin is a boolean value of your login status
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    loginCodeEditText.setErrorColor(getResources().getColor(R.color.colorRed));
                    loginCodeEditText.setError(getResources().getString(R.string.wrong_code));
                }

            }
        });
    }

}
