package com.example.housingloanlegalfees

data class HouseLoan(var sellingPrice: Double, var downPayment: Double, var firstTimeBuyer: Boolean) {
    fun getSellingPriceString(): String {
        return sellingPrice.toString()
    }
    fun getDownPaymentString(): String {
        return downPayment.toString()
    }
    fun getFirstTimeString(): String {
        return firstTimeBuyer.toString()
    }
}