package com.dhrumilshah98.rbcaccounts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dhrumilshah98.rbcaccounts.R
import com.dhrumilshah98.rbcaccounts.util.Constants
import com.rbc.rbcaccountlibrary.Account
import kotlin.math.abs

/**
 * `AccountsAdapter` inflates the item layout and populates it with appropriate data source (i.e., All Accounts).
 */
class AccountsAdapter(
    private val context: Context,
    private val accounts: List<Account>,
    private val onAccountClickListener: OnAccountClickListener
) :
    RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>() {

    /**
     * Gets the item count for the datasource `accounts`.
     */
    override fun getItemCount(): Int = accounts.size

    /**
     * Inflates the layout to hold `AccountsViewHolder` object.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        val adapterLayout: View = LayoutInflater.from(context).inflate(R.layout.item_account, parent, false)
        return AccountsViewHolder(adapterLayout, onAccountClickListener)
    }

    /**
     * Binds the inflated layout to the current `AccountsViewHolder` object.
     */
    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val account: Account = accounts[position]
        val accountBalanceAbs: Double = abs(account.balance.toDouble())
        holder.itemTvAccountName?.text = account.name
        holder.itemTvAccountNumber?.text = context.getString(R.string.template_account_number, account.number)
        val accountBalanceColor = Constants.getAmountColor(context, account.balance.toDouble())
        holder.itemTvAccountBalance?.setTextColor(accountBalanceColor)
        holder.itemTvAccountBalance?.text = context.getString(R.string.template_amount, accountBalanceAbs)
        holder.itemCSLAccount?.setOnClickListener { holder.itemOnAccountClickListener.onAccountClick(position) }
    }

    /**
     * Listens to the click on any account.
     */
    interface OnAccountClickListener {
        fun onAccountClick(position: Int)
    }

    /**
     * `AccountsViewHolder` class holds the view for the provided `view: View`.
     */
    class AccountsViewHolder(view: View?, onAccountClickListener: OnAccountClickListener) : RecyclerView.ViewHolder(view!!) {
        val itemTvAccountName: TextView? = view?.findViewById(R.id.item_tv_account_name)
        val itemTvAccountNumber: TextView? = view?.findViewById(R.id.item_tv_account_number)
        val itemTvAccountBalance: TextView? = view?.findViewById(R.id.item_tv_account_balance)
        val itemCSLAccount: ConstraintLayout? = view?.findViewById(R.id.item_csl_account)
        val itemOnAccountClickListener: OnAccountClickListener = onAccountClickListener
    }
}
