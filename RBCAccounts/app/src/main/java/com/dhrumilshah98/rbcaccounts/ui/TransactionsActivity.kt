package com.dhrumilshah98.rbcaccounts.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dhrumilshah98.rbcaccounts.R
import com.dhrumilshah98.rbcaccounts.adapter.TransactionsAdapterJava
import com.dhrumilshah98.rbcaccounts.databinding.ActivityTransactionsBinding
import com.dhrumilshah98.rbcaccounts.util.Constants
import com.dhrumilshah98.rbcaccounts.viewmodel.TransactionsViewModel
import com.dhrumilshah98.rbcaccounts.viewmodel.ViewModelFactory
import kotlin.math.abs

class TransactionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionsBinding
    private lateinit var transactionsViewModel: TransactionsViewModel
    private lateinit var adapter: TransactionsAdapterJava

    override fun onCreate(savedInstanceState: Bundle?) {
        setViewModel()
        setObservers()
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun setViewModel() {
        transactionsViewModel = ViewModelProvider(this, ViewModelFactory())[TransactionsViewModel::class.java]
    }

    private fun setObservers() {
        transactionsViewModel.getAllTransactions().observe(this) { transactions ->
            adapter.updateTransactions(transactions)
            toggleCircularProgressBarVisibility(false)
            toggleTransactionsRecyclerViewVisibility(true)
        }

        transactionsViewModel.getErrorStatus().observe(this) { errorStatus ->
            val message: String = getErrorMessage(errorStatus)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun init() {
        toggleTransactionsRecyclerViewVisibility(false)
        toggleCircularProgressBarVisibility(true)
        setAccountInformation()
        setAdapter()
        transactionsViewModel.getAllTransactions(transactionsViewModel.accountNumber)
    }

    private fun setAccountInformation() {
        transactionsViewModel.accountName = intent.getStringExtra(Constants.ACCOUNT_NAME).toString()
        transactionsViewModel.accountNumber = intent.getStringExtra(Constants.ACCOUNT_NUMBER).toString()
        transactionsViewModel.accountBalance = intent.getStringExtra(Constants.ACCOUNT_BALANCE).toString()
        val accountBalanceAbs: Double = abs(transactionsViewModel.accountBalance.toDouble())
        binding.activityLytAccount.itemTvAccountName.text = transactionsViewModel.accountName
        binding.activityLytAccount.itemTvAccountNumber.text = getString(R.string.template_account_number, transactionsViewModel.accountNumber)
        val accountBalanceColor = Constants.getAmountColor(this, transactionsViewModel.accountBalance.toDouble())
        binding.activityLytAccount.itemTvAccountBalance.setTextColor(accountBalanceColor)
        binding.activityLytAccount.itemTvAccountBalance.text = getString(R.string.template_amount, accountBalanceAbs)
    }

    private fun setAdapter() {
        adapter = TransactionsAdapterJava(this, transactionsViewModel.getAllTransactions().value)
        binding.activityRvTransactions.adapter = adapter
        binding.activityRvTransactions.setHasFixedSize(true)
    }

    private fun toggleTransactionsRecyclerViewVisibility(isVisible: Boolean) {
        binding.activityRvTransactions.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun toggleCircularProgressBarVisibility(isVisible: Boolean) {
        binding.activityLytProgressBar.layoutFlCircularProgressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun getErrorMessage(errorCode: Int): String {
        val message: String = when (errorCode) {
            Constants.ERROR_CODE_NORMAL_TRANSACTIONS -> {
                getString(R.string.error_normal_transactions_not_found)
            }
            Constants.ERROR_CODE_ADDITIONAL_CREDIT_CARD_TRANSACTIONS -> {
                getString(R.string.error_additional_credit_card_transactions_not_found)
            }
            Constants.ERROR_CODE_NO_TRANSACTIONS_FOUND -> {
                getString(R.string.error_no_transactions_found)
            }
            else -> {
                getString(R.string.error_something_went_wrong)
            }
        }
        return message
    }
}