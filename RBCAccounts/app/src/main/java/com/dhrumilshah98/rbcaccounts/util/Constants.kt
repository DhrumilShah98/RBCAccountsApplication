package com.dhrumilshah98.rbcaccounts.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.dhrumilshah98.rbcaccounts.R

/**
 * `Constants` hold all the constants of the application.
 */
object Constants {

    // Error Codes
    const val ERROR_CODE_SOMETHING_WENT_WRONG: Int = 1000;
    const val ERROR_CODE_NORMAL_TRANSACTIONS: Int = 1001;
    const val ERROR_CODE_ADDITIONAL_CREDIT_CARD_TRANSACTIONS: Int = 1002;
    const val ERROR_CODE_NO_TRANSACTIONS_FOUND: Int = 1003;

    // Error Messages
    const val ERROR_MESSAGE_UNKNOWN_VIEW_MODEL: String = "Oops! Unknown ViewModel class"
    const val ERROR_SOMETHING_WENT_WRONG: String = "Oops! Something went wrong, please try again."

    // Account Constants
    const val ACCOUNT_NAME: String = "ACCOUNT_NAME"
    const val ACCOUNT_NUMBER: String = "ACCOUNT_NUMBER"
    const val ACCOUNT_BALANCE: String = "ACCOUNT_BALANCE"

    /**
     * Gets the color for the amount
     */
    @JvmStatic
    fun getAmountColor(context: Context, amount: Double): Int {
        return if (amount >= 0)
            ContextCompat.getColor(context, R.color.green_300)
        else
            ContextCompat.getColor(context, R.color.red_300)
    }
}