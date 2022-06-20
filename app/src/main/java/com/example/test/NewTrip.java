package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;

public class NewTrip extends AppCompatActivity {
    EditText inputDestination;
    Button createButton;
    TextView title;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_trip);

        inputDestination = findViewById(R.id.inputDestination);
        createButton = findViewById(R.id.createButton);
        title = findViewById(R.id.title);

        inputDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputDestination.getText().toString().trim().isEmpty()) {
                    title.setText("New Trip");
                } else {
                    title.setText(inputDestination.getText().toString().trim() + " Trip");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputDestination.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please provide the trip destination", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(NewTrip.this, MainActivity.class);
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(TITLE, inputDestination.getText().toString()).commit();
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {}
}