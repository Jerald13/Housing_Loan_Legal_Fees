package com.example.housingloanlegalfees

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.housingloanlegalfees.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)



        binding.button.setOnClickListener{
            calculateLoan()
        }

        var isPortrait = true

        binding.btnSwitchDisplay.setOnClickListener{
            requestedOrientation = if (isPortrait) {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                // else change to Portrait
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }

            // opposite the value of isPortrait
            isPortrait = !isPortrait
        }

        binding.emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("bank@example.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry")
            intent.putExtra(Intent.EXTRA_TEXT, "Hello, I have an inquiry about my account.")

            try {
                startActivity(Intent.createChooser(intent, "Send Email"))
            } catch (ex: ActivityNotFoundException) {
                // Handle the case where no email app is installed
                Toast.makeText(this, "No email app installed.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.contactButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+1234567890")

            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                // Handle the case where no dialer app is installed
                Toast.makeText(this, "No dialer app installed.", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun calculateLoan(){
        var first_buyer = binding.checkBox.isChecked
        var selling_price = binding.textPrice.text.toString().toDoubleOrNull() ?: 0.0
        var down_payment =  binding.textDownPayment.text.toString().toDoubleOrNull() ?: 0.0
        var loan = 0.0

        if(first_buyer){
            loan = selling_price * 0.9
        }else{
            loan = selling_price-down_payment
        }

        var legal_fees =calculateLegalFees(loan)
        val STAMP_DUTY =calculateStampDuty(loan)

        val houseLoan = HouseLoan(selling_price,down_payment,first_buyer)
        binding.houseLoan =houseLoan

        loan = legal_fees + STAMP_DUTY
        binding.output.text = loan.toString()
    }

    private fun calculateLegalFees(loanAmount : Double) : Double{
        return when {
            loanAmount <= 500000 -> loanAmount * 0.01
            loanAmount <= 1000000 -> (500000 * 0.01) + ((loanAmount - 500000) * 0.008)
            else -> (500000 * 0.01) + (500000 * 0.008) + ((loanAmount - 1000000) * 0.005)
        }
    }

    private fun calculateStampDuty(loanAmount: Double):Double{
        return loanAmount * 0.05
    }

}