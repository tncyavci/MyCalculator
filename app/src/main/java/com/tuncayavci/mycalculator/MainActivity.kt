package com.tuncayavci.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.tuncayavci.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    // Represent whether the lastly pressed key is numeric or not
    private var lastNumeric : Boolean = false

    // If true, don't allow to add another dot
    private var lastDot : Boolean = false

    private var inputText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        inputText = binding.inputText
    }

    fun onDigit(view: View) {
        inputText?.append((view as Button).text)
            lastNumeric=true
    }

    fun onClear(view: View){
        inputText?.text = ""
            lastNumeric = false
            lastDot = false
    }
    /**
     * Append to the TextView
     */

    fun onDecimalPoint(view: View){

        // If the last appended values is numeric then append (".") or don't
        if(lastNumeric && !lastDot) {
            inputText?.append(".")
                lastNumeric = false //Update the flag
                lastDot = false // update the flag
        }
    }

    /**
     * Append +,-,*,/ operators to the TextView s per Button.Text
     */
    fun onOperator(view: View) {
        inputText?.text.let {
            if(lastNumeric && !isOperatorAdded(it.toString())) {
                inputText?.append((view as Button).text)
                lastNumeric = false // update the flag
                lastDot = false // update the DOT flag
            }
        }
    }

    /**
     * Calculate the output
     */
    fun onEqual(view: View) {
        // If the last input is a number only, solution can be found
        if(lastNumeric) {
            // Read the textView value
            var textValue = inputText?.text.toString()
            var prefix = ""

            try {

                // Here if the value starts with '-' then we will separate it and perform the calculation with value.
                if (textValue.startsWith("-")){
                    prefix="-"
                    textValue= textValue.substring(1)
                }
                // if the inputValue contains the Division operator
                when {
                    textValue.contains("/")-> {
                        // will split the value using Division operator
                        val splitValue = textValue.split("/")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) { // if the prefix is not empty then we will append it with first value i.e one .
                            one = prefix + one
                        }

                        /**
                         * Here as the value one and two will be calculated based on the operator and
                         * if the result contains the zero after decimal point will remove it.
                         * and display the result TextView
                         */
                        inputText?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                    textValue.contains("*") -> {
                        // will split the value using multiplication operator
                        val splitValue = textValue.split("*")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                         }

                        /** Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        inputText?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    textValue.contains("-") -> {

                        // If the inputValue contains the Subtraction operator
                        // Will split the inputValue using Subtraction operator
                        val splitedValue = textValue.split("-")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /** Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        inputText?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    textValue.contains("+") -> {
                        // If the inputValue contains the Addition operator
                        // Will split the inputValue using Addition operator
                        val splitedValue = textValue.split("+")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /**Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        inputText?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                }
            } catch (e: java.lang.ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    /**
     *  It is used to check whether any of the operator is used or not
     */

    private fun isOperatorAdded(value: String) : Boolean {

        /**
         * Here first we will check that if the value starts with "-" then will ignore it.
         * As it is the result value and perform further calculation.
         */

        return if(value.startsWith("-")) {
            false
        } else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+"))
        }
    }

    /**
     *  Remove the zero after decimal point
     */
    private fun removeZeroAfterDot(result: String): String{
        var value = result

        if (result.contains(".0")){
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }

}
