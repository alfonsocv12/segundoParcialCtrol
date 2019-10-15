package com.example.parcial2android

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.acerca_de_mi.view.*
import kotlinx.android.synthetic.main.acerca_de_mi.view.btnClose
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.car_dialog.view.*
import kotlinx.android.synthetic.main.error_login.view.*
import kotlinx.android.synthetic.main.share_cars.view.*

class MainActivity : AppCompatActivity() {
    internal var qrScanIntegrator: IntentIntegrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        qrScanIntegrator = IntentIntegrator(this)
        btnReadPlate.setOnClickListener {
            qrScanIntegrator?.initiateScan()
            //startActivity(Intent(this, readPlate::class.java))
        }
        btnAcercaDe.setOnClickListener {
            val myDialogView = layoutInflater.inflate(R.layout.acerca_de_mi, null)
            val mBuilder = AlertDialog.Builder(this).setView(myDialogView).setTitle("Acerca de mi").show()
            myDialogView.btnGit.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://github.com/alfonsocv12")
                startActivity(openURL)
            }
            myDialogView.btnClose.setOnClickListener {
                mBuilder.dismiss()
            }
        }
        btnVA.setOnClickListener {
            val myDialogView = layoutInflater.inflate(R.layout.share_cars, null)
            val mBuilder = AlertDialog.Builder(this).setView(myDialogView).setTitle("Carros afiliados").show()
            val sharedPreferences = getSharedPreferences("cars", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val cars = sharedPreferences.getString("cars", "")
            myDialogView.txtSharePraferences.setText(cars)
            myDialogView.btnSave.setOnClickListener {
                editor.putString("cars", myDialogView.txtSharePraferences.text.toString())
                editor.apply()
                mBuilder.dismiss()
                Toast.makeText(this,"Your cars have been saved!", Toast.LENGTH_LONG).show()
            }
        }
        btnCerrarSesion.setOnClickListener {
            startActivity(Intent(this,login::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
            } else {
                val myDialogView2 = layoutInflater.inflate(R.layout.car_dialog, null)
                val arrayValores = result.contents.toString().split(",")
                val mBuilder2 = AlertDialog.Builder(this).setView(myDialogView2).setTitle(arrayValores[0]).show()
                myDialogView2.txtModelo.text = arrayValores[1]
                myDialogView2.txtAno.text = arrayValores[2]
                myDialogView2.txtDeuda.text = arrayValores[3]
                myDialogView2.btnClose.setOnClickListener {
                    mBuilder2.dismiss()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
