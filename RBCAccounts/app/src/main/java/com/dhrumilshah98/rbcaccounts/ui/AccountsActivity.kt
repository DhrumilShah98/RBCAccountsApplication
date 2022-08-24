package com.dhrumilshah98.rbcaccounts.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.dhrumilshah98.rbcaccounts.adapter.AccountsAdapter
import com.dhrumilshah98.rbcaccounts.databinding.ActivityAccountsBinding
import com.dhrumilshah98.rbcaccounts.util.Constants
import com.dhrumilshah98.rbcaccounts.viewmodel.AccountsViewModel
import com.dhrumilshah98.rbcaccounts.viewmodel.ViewModelFactory
import com.rbc.rbcaccountlibrary.Account

/**
 * Equivalent to `AccountsActivityJava` (Java).
 */
class AccountsActivity : AppCompatActivity(), AccountsAdapter.OnAccountClickListener {

    private lateinit var binding: ActivityAccountsBinding
    private lateinit var accountsViewModel: AccountsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setViewModel()
        displaySplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityAccountsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAccountsRV()
    }

    private fun setViewModel() {
        accountsViewModel = ViewModelProvider(this, ViewModelFactory())[AccountsViewModel::class.java]
    }

    private fun displaySplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { accountsViewModel.isLoading.value }
    }

    private fun setAccountsRV() {
        binding.activityRvAccounts.adapter = AccountsAdapter(this, accountsViewModel.getAccounts(), this)
        binding.activityRvAccounts.setHasFixedSize(true)
    }

    override fun onAccountClick(position: Int) {
        val account: Account = accountsViewModel.getAccounts()[position]
        val transactionsIntent = Intent(this, TransactionsActivity::class.java)
        transactionsIntent.putExtra(Constants.ACCOUNT_NAME, account.name)
        transactionsIntent.putExtra(Constants.ACCOUNT_NUMBER, account.number)
        transactionsIntent.putExtra(Constants.ACCOUNT_BALANCE, account.balance)
        startActivity(transactionsIntent)
    }
}