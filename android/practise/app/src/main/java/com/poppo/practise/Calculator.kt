package com.poppo.practise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class Calculator : AppCompatActivity() {

    var before = "0";
    var after = "0";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val resultView : TextView = findViewById(R.id.result)

        val clearView : TextView = findViewById(R.id.clear)

        clearView.setOnClickListener {
            Log.d("clear", "clear")
            clearAll()
            resultView.text = after
        }

        val oneButton : TextView = findViewById(R.id.one)
        oneButton.setOnClickListener {
            Log.d("one", "one")
            inputNumber("1")
            resultView.text = after
        }

        val twoButton : TextView = findViewById(R.id.two)
        twoButton.setOnClickListener {
            Log.d("two", "two")
            inputNumber("2")
            resultView.text = after
        }

        val threeButton : TextView = findViewById(R.id.three)
        threeButton.setOnClickListener {
            Log.d("three", "three")
            inputNumber("3")
            resultView.text = after
        }

        val fourButton : TextView = findViewById(R.id.four)
        fourButton.setOnClickListener {
            Log.d("four", "four")
            inputNumber("4")
            resultView.text = after
        }

        val fiveButton : TextView = findViewById(R.id.five)
        fiveButton.setOnClickListener {
            Log.d("five", "five")
            inputNumber("5")
            resultView.text = after
        }

        val sixButton : TextView = findViewById(R.id.six)
        sixButton.setOnClickListener {
            Log.d("six", "six")
            inputNumber("6")
            resultView.text = after
        }

        val sevenButton : TextView = findViewById(R.id.seven)
        sevenButton.setOnClickListener {
            Log.d("seven", "seven")
            inputNumber("7")
            resultView.text = after
        }

        val eightButton : TextView = findViewById(R.id.eight)
        eightButton.setOnClickListener {
            Log.d("eight", "eight")
            inputNumber("8")
            resultView.text = after
        }

        val nineButton : TextView = findViewById(R.id.nine)
        nineButton.setOnClickListener {
            Log.d("nine", "nine")
            inputNumber("9")
            resultView.text = after
        }

        val zeroButton : TextView = findViewById(R.id.zero)
        zeroButton.setOnClickListener {
            Log.d("zero", "zero")
            inputNumber("0")
            resultView.text = after
        }

        val plusButton : TextView = findViewById(R.id.plus)
        plusButton.setOnClickListener {
            before = (before.toInt() + after.toInt()).toString()
            after = "0"
            resultView.text = before
        }
    }

    fun inputNumber(number: String) {
        after += number
    }

    fun clearAll() {
        before = "0"
        after = "0"
    }
}
