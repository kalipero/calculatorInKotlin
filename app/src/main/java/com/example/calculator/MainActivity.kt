package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var numInput: TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numInput = findViewById(R.id.numInput)
    }

        fun onDigit(view: View){        // zachycení stisknutého tlačítka
            numInput?.append((view as Button).text)  // přídání textu tlačítka do textového pole
            lastNumeric = true          // pracujeme s číslicemi, takže to bude true
            lastDot = false             // platí pro číslice, takže na konci nebude tečka, takže false
        }
    fun onClear(view: View){           // funkce pro vyčištění vstupu
        numInput?.text=""              // nastavení textu ve vstupu na prázdnou hodnotu
    }
    fun onDecimalPoint(view: View){    // funkce pro přidání tečky
        if (lastNumeric && !lastDot){  // pokud je na konci číslo a není tam tečka
            numInput?.append(".")      // přidáme tečku
            lastDot = true             // přidali jsme na konec tečku, takže prom bude true
            lastNumeric = false        // na konci není číslo, takže false
        }
    }
    fun onOperator(view: View){
        numInput?.text?.let {           // provede se pokud numInput není null a pokud má nějaký text
            if (lastNumeric && !isOperatorAdded(it.toString())){  // kontrola jestli je na poslední pozici nula a jestli nebyl přidán operátor
                numInput?.append((view as Button).text) // přidá se operátor
                lastNumeric = false  // na konci není číslo
                lastDot = false     // na konci není tečka
            }
        }
    }
    fun onEqual(view: View){
        if(lastNumeric){
            var numValue = numInput?.text.toString()
            var prefix = " "

            try {
                if (numValue.startsWith("-")){
                    prefix = "-"
                    numValue = numValue.substring(1)
                }
                if (numValue.contains("-")){
                    val splitValue = numValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    numInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }
                else if (numValue.contains("+")) {
                    val splitValue = numValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    numInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                else if (numValue.contains("/")){
                    val splitValue = numValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    numInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
                else if (numValue.contains("*")){
                    val splitValue = numValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }


                    numInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }


            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfterDot(result: String) : String{
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length-2)
        return value
    }

    private fun isOperatorAdded(value : String) : Boolean{      // kontrolujeme zda je v řetězci nějaký operátor
        return if (value.startsWith("-")){
            false
        } else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

}