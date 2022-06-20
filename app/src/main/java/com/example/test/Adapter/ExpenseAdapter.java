package com.example.test.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.AddExpenseActivity;
import com.example.test.EditExpenseActivity;
import com.example.test.MainActivity;
import com.example.test.Model.ExpenseModel;
import com.example.test.R;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    Context context;
    RealmResults<ExpenseModel> expenseList;

    public ExpenseAdapter(Context context, RealmResults<ExpenseModel> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpenseViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        ExpenseModel expense = expenseList.get(position);
        holder.expenseName.setText(expense.getExpenseName());
        holder.expensePrice.setText("$" + expense.getExpensePrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditExpenseActivity.class);
                intent.putExtra("POSITION", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView expenseName;
        TextView expensePrice;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseName = itemView.findViewById(R.id.expenseName);
            expensePrice = itemView.findViewById(R.id.expensePrice);

        }
    }
}
