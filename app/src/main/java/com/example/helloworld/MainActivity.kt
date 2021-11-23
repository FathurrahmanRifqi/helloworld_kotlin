package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.material.utils.ViewAnimation


class MainActivity : AppCompatActivity() {

    private val loadingDuration = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // declare variable
        val editText = findViewById<EditText>(R.id.editText_id)
        val button = findViewById<Button>(R.id.button)


        // when text typing
        editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                button.isEnabled = s.toString().trim { it <= ' ' }.isNotEmpty()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })

        // when button on click
        button.setOnClickListener {
            val inputValue: String = editText.text.toString()
            when {
                inputValue.trim()=="Fathurrahman Rifqi Azzami" -> {
                    Toast.makeText(this,"Welcome Back !",Toast.LENGTH_LONG).show()
                    hideKeybaord(it)
                    loadingAndIntent()
                }
                inputValue.trim() == "" -> {
                    Toast.makeText(this,"please input data, edit text cannot be blank",Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this,"Not Access !",Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    // Loading and Intent
    private fun loadingAndIntent() {
        val lytWrap = findViewById<View>(R.id.lyt_wrap) as RelativeLayout
        val loading = findViewById<View>(R.id.loading) as ProgressBar

        lytWrap.alpha = 1.0f
        lytWrap.visibility = View.VISIBLE

        Handler().postDelayed({
                ViewAnimation.fadeOut(lytWrap)
                loading.visibility = View.VISIBLE },
            loadingDuration.toLong()-1500)

        Handler().postDelayed({
            loading.visibility = View.GONE
            restartActivity()
            moveActivity()
            }, (loadingDuration + 300).toLong())
    }

    // Hide Keyboard
    private fun hideKeybaord(v: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

    // Move Activity
    private fun moveActivity() {
        intent = Intent(applicationContext, DashboardActivity::class.java)
        startActivity(intent)
    }


    // restart Activity
    private fun restartActivity() {
        val mIntent = intent
        finish()
        startActivity(mIntent)
    }



}