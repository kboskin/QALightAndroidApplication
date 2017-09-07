package com.example.hp.qalightandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class LoginActivity extends AppCompatActivity {

    private TextFieldBoxes loginCodeEditText;
    private Button personalCabButton;
    private TextView creditsTextView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create intent firstly, because we pass it via constructor, task because call outside from activity
        intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        setContentView(R.layout.activity_login);
        findViewsByIds();

    }

    private void findViewsByIds()
    {
        //button must be defined first, because it is a param in constructor of Listener
        personalCabButton = findViewById(R.id.personalCabButtonOnLogin);

        // edit text for code on login page
        loginCodeEditText = findViewById(R.id.loginCodeEditText);
        setTextChangeListener();

        //set link method for textView
        creditsTextView = (findViewById(R.id.txt_credits));
        creditsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        creditsTextView.setTextColor(getResources().getColor(R.color.colorGray));
    }
    private void setTextChangeListener()
    {
        loginCodeEditText.getEditText().addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("correct")) {
                    // resetting error's color to make user friendly UI
                    loginCodeEditText.setErrorColor(getResources().getColor(R.color.colorGreen));
                    loginCodeEditText.setError(getResources().getString(R.string.correct_code));
                    personalCabButton.setVisibility(View.VISIBLE);
                    personalCabButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else {
                    loginCodeEditText.setErrorColor(getResources().getColor(R.color.colorRed));
                    loginCodeEditText.setError(getResources().getString(R.string.wrong_code));
                }

            }
        });
    }
}
