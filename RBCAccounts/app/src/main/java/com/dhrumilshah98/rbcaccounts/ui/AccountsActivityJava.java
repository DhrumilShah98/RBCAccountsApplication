package com.dhrumilshah98.rbcaccounts.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import com.dhrumilshah98.rbcaccounts.adapter.AccountsAdapter;
import com.dhrumilshah98.rbcaccounts.databinding.ActivityAccountsBinding;
import com.dhrumilshah98.rbcaccounts.util.Constants;
import com.dhrumilshah98.rbcaccounts.viewmodel.AccountsViewModel;
import com.rbc.rbcaccountlibrary.Account;

/**
 * Equivalent to `AccountsActivity` (Kotlin).
 */
public class AccountsActivityJava extends AppCompatActivity implements
        AccountsAdapter.OnAccountClickListener {

    private ActivityAccountsBinding binding;
    private AccountsViewModel accountsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setViewModel();
        displaySplashScreen();
        super.onCreate(savedInstanceState);
        binding = ActivityAccountsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setAccountsRV();
    }

    private void setViewModel() {
        accountsViewModel = new ViewModelProvider(this).get(AccountsViewModel.class);
    }

    private void displaySplashScreen() {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> accountsViewModel.isLoading().getValue());
    }

    private void setAccountsRV() {
        final AccountsAdapter adapter = new AccountsAdapter(this, accountsViewModel.getAccounts(), this);
        binding.activityRvAccounts.setAdapter(adapter);
        binding.activityRvAccounts.setHasFixedSize(true);
    }

    @Override
    public void onAccountClick(int position) {
        final Account account = accountsViewModel.getAccounts().get(position);
        final Intent transactionsIntent = new Intent(this, TransactionsActivity.class);
        transactionsIntent.putExtra(Constants.ACCOUNT_NAME, account.getName());
        transactionsIntent.putExtra(Constants.ACCOUNT_NUMBER, account.getNumber());
        transactionsIntent.putExtra(Constants.ACCOUNT_BALANCE, account.getBalance());
        startActivity(transactionsIntent);
    }
}