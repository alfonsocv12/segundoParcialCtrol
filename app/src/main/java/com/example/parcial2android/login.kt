package com.example.parcial2android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.error_login.view.*

class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnLogin.setOnClickListener {
            if (txtName.text.toString() == "segundoparcial" && txtPassword.text.toString() == "1"){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                val myDialogView = layoutInflater.inflate(R.layout.error_login, null)
                val mBuilder = AlertDialog.Builder(this).setView(myDialogView).setTitle("Error login").show()
                myDialogView.btnCloceEL.setOnClickListener {
                    mBuilder.dismiss()
                }
            }
        }
    }

}
