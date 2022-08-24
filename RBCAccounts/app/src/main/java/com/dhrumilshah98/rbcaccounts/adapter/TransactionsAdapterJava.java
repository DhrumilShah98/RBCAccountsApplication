package com.dhrumilshah98.rbcaccounts.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.dhrumilshah98.rbcaccounts.R;
import com.dhrumilshah98.rbcaccounts.util.Constants;
import com.dhrumilshah98.rbcaccounts.util.DateUtils;
import com.rbc.rbcaccountlibrary.Transaction;

import java.util.List;

/**
 * `TransactionsAdapterJava` inflates the item layout and populates it with appropriate data source (i.e., All Transactions).
 * Equivalent to `TransactionsAdapter` (Kotlin).
 */
public class TransactionsAdapterJava extends
        RecyclerView.Adapter<TransactionsAdapterJava.TransactionsViewHolderJava> {

    private final Context context;
    private List<Transaction> transactions;

    public TransactionsAdapterJava(@NonNull Context context, @Nullable List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateTransactions(@NonNull List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (transactions == null) ? 0 : transactions.size();
    }

    @NonNull
    @Override
    public TransactionsViewHolderJava onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View adapterLayout = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new TransactionsViewHolderJava(adapterLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolderJava holder, int position) {
        final Transaction transaction = transactions.get(position);
        final Double transactionBalanceAbs = Math.abs(Double.parseDouble(transaction.getAmount()));
        holder.itemTvTransactionDescription.setText(transaction.getDescription());
        final int amountBalanceColor = Constants.getAmountColor(context, Double.parseDouble(transaction.getAmount()));
        holder.itemTvTransactionAmount.setTextColor(amountBalanceColor);
        holder.itemTvTransactionAmount.setText(context.getString(R.string.template_amount, transactionBalanceAbs));
        final String transactionDate = DateUtils.formatDate(transaction.getDate().getTime(), DateUtils.DATE_FORMAT_1);
        holder.itemTvTransactionDate.setText(transactionDate);
    }

    /**
     * `TransactionsViewHolderJava` class holds the view for the provided `view: View`.
     * Equivalent to `TransactionsViewHolder` (Kotlin).
     */
    static class TransactionsViewHolderJava extends RecyclerView.ViewHolder {
        private final TextView itemTvTransactionDate;
        private final TextView itemTvTransactionDescription;
        private final TextView itemTvTransactionAmount;

        public TransactionsViewHolderJava(@NonNull View itemView) {
            super(itemView);
            itemTvTransactionDate = itemView.findViewById(R.id.item_tv_transaction_date);
            itemTvTransactionDescription = itemView.findViewById(R.id.item_tv_transaction_description);
            itemTvTransactionAmount = itemView.findViewById(R.id.item_tv_transaction_amount);
        }
    }
}
