package com.ssu.bilda

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.ssu.bilda.databinding.ActivityEmailInputBinding

class EmailInputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSignupError.visibility = View.INVISIBLE
        binding.btnSignupSendauth.isEnabled = false

        binding.etSignupEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                val isEmailValid = text.endsWith("@soongsil.ac.kr")
                binding.btnSignupSendauth.isEnabled = isEmailValid
                if (!isEmailValid && text.contains("@")) {
                    val newText = "$text" + "soongsil.ac.kr"
                    binding.etSignupEmail.setText(newText)
                    binding.etSignupEmail.setSelection(newText.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSignupSendauth.setOnClickListener {
            showEmailDialog()
        }
    }

    private fun showEmailDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_email, null)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnDialog = dialogView.findViewById<AppCompatButton>(R.id.btn_signup_dialog)
        btnDialog.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()

        val window: Window? = alertDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.setGravity(Gravity.CENTER)
    }
}
