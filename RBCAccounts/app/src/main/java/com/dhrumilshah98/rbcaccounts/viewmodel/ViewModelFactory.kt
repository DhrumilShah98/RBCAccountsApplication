package com.dhrumilshah98.rbcaccounts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhrumilshah98.rbcaccounts.repository.TransactionsRepository
import com.dhrumilshah98.rbcaccounts.util.Constants

/**
 * `ViewModelFactory` class is responsible for providing the correct ViewModel object.
 */
class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TransactionsViewModel::class.java) -> {
                TransactionsViewModel(TransactionsRepository()) as T
            }
            modelClass.isAssignableFrom(AccountsViewModel::class.java) -> {
                AccountsViewModel() as T
            }
            else -> {
                throw IllegalArgumentException(Constants.ERROR_MESSAGE_UNKNOWN_VIEW_MODEL)
            }
        }
    }
}