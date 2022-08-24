package com.dhrumilshah98.rbcaccounts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhrumilshah98.rbcaccounts.repository.TransactionsRepository
import com.dhrumilshah98.rbcaccounts.util.Constants
import com.rbc.rbcaccountlibrary.Transaction
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * `TransactionsViewModel` holds the data for `TransactionsActivity`.
 */
class TransactionsViewModel(private val transactionsRepository: TransactionsRepository) :
    ViewModel() {

    // Variables
    // All transactions variable
    private val allTransactions: MutableLiveData<List<Transaction>> = MutableLiveData<List<Transaction>>()

    // Error status variable
    private val errorStatus: MutableLiveData<Int> = MutableLiveData<Int>()

    // Account information variables
    lateinit var accountName: String
    lateinit var accountNumber: String
    lateinit var accountBalance: String

    // Observers
    fun getAllTransactions(): LiveData<List<Transaction>> {
        return allTransactions
    }

    fun getErrorStatus(): LiveData<Int> {
        return errorStatus
    }

    // Logic

    /**
     * Sets the transactions
     */
    fun getAllTransactions(accountNumber: String) {
        viewModelScope.launch {
            val transactionsDeferred = async { transactionsRepository.getTransactions(accountNumber) }
            val creditCardTransactionsDeferred = async { transactionsRepository.getAdditionalCreditCardTransactions(accountNumber) }

            val transactionsResult = transactionsDeferred.await()
            val creditCardTransactionsResult = creditCardTransactionsDeferred.await()

            val list1Transactions = transactionsResult.getOrDefault(listOf())
            val list2CreditCardTransactions = creditCardTransactionsResult.getOrDefault(listOf())

            // Sort the transactions with newest first.
            val transactions = (list1Transactions + list2CreditCardTransactions).sortedByDescending { t -> t.date.time }

            if (transactions.isEmpty()) {
                errorStatus.postValue(Constants.ERROR_CODE_NO_TRANSACTIONS_FOUND)
            } else if (transactionsResult.isFailure) {
                if (creditCardTransactionsResult.isFailure) {
                    errorStatus.postValue(Constants.ERROR_CODE_SOMETHING_WENT_WRONG)
                } else {
                    errorStatus.postValue(Constants.ERROR_CODE_NORMAL_TRANSACTIONS)
                }
            } else if (creditCardTransactionsResult.isFailure) {
                errorStatus.postValue(Constants.ERROR_CODE_ADDITIONAL_CREDIT_CARD_TRANSACTIONS)
            }
            allTransactions.postValue(transactions)
        }
    }
}