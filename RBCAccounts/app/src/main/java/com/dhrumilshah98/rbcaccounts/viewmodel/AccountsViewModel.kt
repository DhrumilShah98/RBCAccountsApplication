package com.dhrumilshah98.rbcaccounts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbc.rbcaccountlibrary.Account
import com.rbc.rbcaccountlibrary.AccountProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * `AccountsViewModel` holds the data for `AccountsActivity`.
 */
class AccountsViewModel : ViewModel() {

    // Splash screen loading variables
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // All accounts variable
    private val accounts: List<Account>

    init {
        // Launch the ViewModel scope to display splash screen initially and remove it after provided time.
        viewModelScope.launch {
            delay(1500)
            _isLoading.value = false
        }
        accounts = AccountProvider.getAccountsList().sortedWith { a1, a2 -> a1.type.compareTo(a2.type) }
    }

    /**
     * Get all accounts
     */
    fun getAccounts(): List<Account> {
        return accounts
    }
}