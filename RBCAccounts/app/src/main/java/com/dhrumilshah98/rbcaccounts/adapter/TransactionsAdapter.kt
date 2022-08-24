package com.dhrumilshah98.rbcaccounts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhrumilshah98.rbcaccounts.R
import com.dhrumilshah98.rbcaccounts.util.Constants
import com.dhrumilshah98.rbcaccounts.util.DateUtils
import com.rbc.rbcaccountlibrary.Transaction
import kotlin.math.abs

/**
 * `TransactionsAdapter` inflates the item layout and populates it with appropriate data source (i.e., All Transactions).
 * Equivalent to `TransactionsAdapterJava` (Java).
 */
class TransactionsAdapter(
    private val context: Context,
    private var transactions: List<Transaction>?
) : RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateTransactions(transactions: List<Transaction>?) {
        if (transactions != null) {
            this.transactions = transactions
            notifyDataSetChanged()
        }
    }

    /**
     * Gets the item count for the datasource `transactions`.
     */
    override fun getItemCount(): Int = transactions?.size ?: 0

    /**
     * Inflates the layout to hold `AccountsViewHolder` object.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val adapterLayout: View = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false)
        return TransactionsViewHolder(adapterLayout)
    }

    /**
     * Binds the inflated layout to the current `AccountsViewHolder` object.
     */
    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val transaction: Transaction = transactions!![position]
        val transactionBalanceAbs: Double = abs(transaction.amount.toDouble())
        holder.itemTvTransactionDescription?.text = transaction.description
        val amountBalanceColor = Constants.getAmountColor(context, transaction.amount.toDouble())
        holder.itemTvTransactionAmount?.setTextColor(amountBalanceColor)
        holder.itemTvTransactionAmount?.text = context.getString(R.string.template_amount, transactionBalanceAbs)
        val transactionDate = DateUtils.formatDate(transaction.date.time, DateUtils.DATE_FORMAT_1)
        holder.itemTvTransactionDate?.text = transactionDate
    }

    /**
     * `TransactionsViewHolder` class holds the view for the provided `view: View`.
     * Equivalent to `TransactionsViewHolderJava` (Java).
     */
    class TransactionsViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val itemTvTransactionDate: TextView? = view?.findViewById(R.id.item_tv_transaction_date)
        val itemTvTransactionDescription: TextView? = view?.findViewById(R.id.item_tv_transaction_description)
        val itemTvTransactionAmount: TextView? = view?.findViewById(R.id.item_tv_transaction_amount)
    }
}