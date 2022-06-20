package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.Adapter.ExpenseAdapter;
import com.example.test.Model.ExpenseModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    private TextView titleText;
    private TextView totalText;
    private FloatingActionButton addButton;
    private FloatingActionButton newTripButton;
    private String title;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        titleText = findViewById(R.id.titleText);
        totalText = findViewById(R.id.totalText);
        addButton = findViewById(R.id.addButton);
        newTripButton = findViewById(R.id.newTripButton);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        title = sharedPreferences.getString(TITLE, "New");
        titleText.setText(title + " Trip");

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ExpenseModel> expenseList = realm.where(ExpenseModel.class).findAll();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddExpenseActivity.class));
            }
        });

        newTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Create A New Trip");
                alert.setMessage("Your current trip will be erased!");
                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realm.beginTransaction();
                        expenseList.deleteAllFromRealm();
                        realm.commitTransaction();
                        startActivity(new Intent(MainActivity.this, NewTrip.class));
                    }
                });
                alert.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(MainActivity.this, expenseList);
        recyclerView.setAdapter(expenseAdapter);

        double totalPrice = 0;
        for (int i = 0; i < expenseList.size(); i++) {
            totalPrice += Double.parseDouble(expenseList.get(i).getExpensePrice());
        }
        totalText.setText("Total: $" + String.format("%.2f", totalPrice));

        expenseList.addChangeListener(new RealmChangeListener<RealmResults<ExpenseModel>>() {
            @Override
            public void onChange(RealmResults<ExpenseModel> expenseModels) {
                expenseAdapter.notifyDataSetChanged();

                double totalPrice = 0;
                for (int i = 0; i < expenseList.size(); i++) {
                    totalPrice += Double.parseDouble(expenseList.get(i).getExpensePrice());
                }
                totalText.setText("Total: $" + String.format("%.2f", totalPrice));
            }
        });
    }
}