package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.Model.ExpenseModel;

import io.realm.Realm;

public class AddExpenseActivity extends AppCompatActivity {
    EditText inputExpenseName;
    EditText inputExpensePrice;
    Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_expense);

        inputExpenseName = findViewById(R.id.inputExpenseName);
        inputExpensePrice = findViewById(R.id.inputExpensePrice);
        enterButton = findViewById(R.id.enterButton);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expenseName = inputExpenseName.getText().toString().trim();
                String expensePrice = inputExpensePrice.getText().toString();

                if (expenseName.isEmpty() && expensePrice.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please provide an expense and its price", Toast.LENGTH_SHORT).show();
                } else if (expenseName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please provide the expense name", Toast.LENGTH_SHORT).show();
                } else if (expensePrice.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please provide the price of: " + expenseName, Toast.LENGTH_SHORT).show();
                } else {
                    realm.beginTransaction();
                    ExpenseModel expense = realm.createObject(ExpenseModel.class);
                    expense.setExpenseName(expenseName);
                    expense.setExpensePrice(expensePrice);
                    realm.commitTransaction();
                    finish();
                }
            }
        });
    }
}