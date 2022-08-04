package com.example.ndkexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ndkexample.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var result: Int? = null
    private var a: Int = 0
    private var b: Int = 0
    private var random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        // binding.sampleText.text = stringFromJNI()

        setRandomNumbers()

        binding.apply {

            bNext.setOnClickListener {
                if (etResult.text.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Please, enter your answer!", Toast.LENGTH_SHORT).show()
                }else if (etResult.text.isNotEmpty() && etResult.text.toString().toInt() == result) {
                    Toast.makeText(this@MainActivity, "Correct answer✅", Toast.LENGTH_SHORT).show()
                    setRandomNumbers()

                    etResult.text.clear()
                }else if (etResult.text.toString().toInt() != result) {
                    Toast.makeText(this@MainActivity, "Incorrect answer.Please, try again!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun setRandomNumbers() {
        a = random.nextInt(100)
        b = random.nextInt(100)
        val c = random.nextInt(4)

        binding.apply {
            tvNumber1.text = a.toString()
            tvNumber2.text = b.toString()

            when(c) {
                0 -> {
                    tvOperation.text = "+"
                    result = add(a,b)

                }
                1 -> {
                    tvOperation.text = "-"
                    result = sub(a,b)
                }
                2 -> {
                    tvOperation.text = "*"
                    result = multiply(a,b)

                }
                3 -> {
                    tvOperation.text = "/"
                    result = divide(a,b)

                }
            }
        }

    }

    /**
     * A native method that is implemented by the 'ndkexample' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun add(a: Int, b: Int): Int
    external fun sub(a: Int, b: Int): Int
    external fun multiply(a: Int, b: Int): Int
    external fun divide(a: Int, b: Int): Int

    companion object {
        // Used to load the 'ndkexample' library on application startup.
        init {
            System.loadLibrary("ndkexample")
        }
    }
}