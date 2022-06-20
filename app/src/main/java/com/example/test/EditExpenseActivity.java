package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import io.realm.RealmResults;

public class EditExpenseActivity extends AppCompatActivity {
    EditText inputExpenseName;
    EditText inputExpensePrice;
    Button saveButton;
    Button deleteBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_expense);

        inputExpenseName = findViewById(R.id.inputExpenseName);
        inputExpensePrice = findViewById(R.id.inputExpensePrice);
        saveButton = findViewById(R.id.saveButton);
        deleteBTN = findViewById(R.id.deleteBTN);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ExpenseModel> expenseList = realm.where(ExpenseModel.class).findAll();

        Intent intent = getIntent();
        int position = intent.getIntExtra("POSITION", -1);

        ExpenseModel thisExpense = expenseList.get(position);
        inputExpenseName.setText(thisExpense.getExpenseName());
        inputExpensePrice.setText(thisExpense.getExpensePrice());

        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EditExpenseActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete this expense?");
                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realm.beginTransaction();
                        thisExpense.deleteFromRealm();
                        realm.commitTransaction();
                        finish();
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

        saveButton.setOnClickListener(new View.OnClickListener() {
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
                    thisExpense.setExpenseName(expenseName);
                    thisExpense.setExpensePrice(expensePrice);
                    realm.commitTransaction();
                    finish();
                }
            }
        });
    }
}