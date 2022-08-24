package com.dhrumilshah98.rbcaccounts.repository

import com.dhrumilshah98.rbcaccounts.util.Constants.ERROR_SOMETHING_WENT_WRONG
import com.rbc.rbcaccountlibrary.AccountProvider
import com.rbc.rbcaccountlibrary.Transaction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * `TransactionRepository` is responsible for querying the Transactions.
 */
class TransactionsRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    /**
     * Gets transactions for specified account number.
     */
    suspend fun getTransactions(accountNumber: String): Result<List<Transaction>> {
        return withContext(dispatcher) {
            return@withContext try {
                Result.success(AccountProvider.getTransactions(accountNumber))
            } catch (e: Exception) {
                Result.failure(Exception(ERROR_SOMETHING_WENT_WRONG))
            }
        }
    }

    /**
     * Gets additional credit card related transactions for specified account number.
     */
    suspend fun getAdditionalCreditCardTransactions(accountNumber: String): Result<List<Transaction>> {
        return withContext(dispatcher) {
            return@withContext try {
                Result.success(AccountProvider.getAdditionalCreditCardTransactions(accountNumber))
            } catch (e: Exception) {
                Result.failure(Exception(ERROR_SOMETHING_WENT_WRONG))
            }
        }
    }
}